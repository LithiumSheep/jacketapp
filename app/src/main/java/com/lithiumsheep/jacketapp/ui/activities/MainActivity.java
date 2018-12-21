package com.lithiumsheep.jacketapp.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.arch.Resource;
import com.lithiumsheep.jacketapp.arch.viewmodel.AppViewModelFactory;
import com.lithiumsheep.jacketapp.models.LastLocation;
import com.lithiumsheep.jacketapp.models.LastLocationCache;
import com.lithiumsheep.jacketapp.models.search.PlaceSuggestion;
import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;
import com.lithiumsheep.jacketapp.ui.WeatherViewHolder;
import com.lithiumsheep.jacketapp.util.DrawerHelper;
import com.lithiumsheep.jacketapp.util.PermissionUtil;
import com.lithiumsheep.jacketapp.arch.viewmodel.LocationViewModel;
import com.lithiumsheep.jacketapp.arch.viewmodel.WeatherViewModel;
import com.mikepenz.materialdrawer.Drawer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int LEET_LOCATION_REQUEST_CODE = 1337;

    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    AppViewModelFactory viewModelFactory;

    // view and model
    Drawer drawer;
    WeatherViewModel weatherViewModel;
    LocationViewModel locationViewModel;

    // autocomplete
    GeoDataClient client;
    AutocompleteFilter defaultFilter;

    // debug
    boolean disableAutocomplete = false;

    WeatherViewHolder holder;

    LastLocationCache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        drawer = DrawerHelper.attach(this);
        searchView.attachNavigationDrawerToMenuButton(drawer.getDrawerLayout());

        holder = new WeatherViewHolder(this);

        locationViewModel = ViewModelProviders
                .of(this)
                .get(LocationViewModel.class);

        weatherViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(WeatherViewModel.class);

        locationViewModel.getlastKnownLocation(this).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                if (location != null) {
                    Timber.d("Last Known Location is %s", location.toString());
                    weatherViewModel.fetchWeather(location);
                } else {
                    Timber.w("Observed ViewModel location is null");
                }
            }
        });


        weatherViewModel.getWeather().observe(this, new Observer<Resource<CurrentWeather>>() {
            @Override
            public void onChanged(@Nullable Resource<CurrentWeather> weather) {
                if (weather != null) {
                    swipeRefreshLayout.setRefreshing(weather.isLoading());
                    if (weather.isLoading()) {
                        holder.clear();
                    }
                    if (weather.isSuccessful()) {
                        updateUi(weather.getData());
                    } else {
                        Toast.makeText(MainActivity.this, weather.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // menu clicked
        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_search) {
                    // check if last location exists on disk, if yes ask to continue
                    if (!searchView.getQuery().isEmpty()) {
                        weatherViewModel.fetchWeather(searchView.getQuery());
                    }
                } else if (item.getItemId() == R.id.action_location) {
                    getWeatherByLocation();
                }
            }
        });


        client = Places.getGeoDataClient(this);
        defaultFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setCountry("USA")
                .build();
        // queryChange - only for Google Places Autocomplete API
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                //Timber.d("%s changed to %s", oldQuery, newQuery);

                if (disableAutocomplete) {
                    return;
                }

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    searchView.clearSuggestions();
                } else {
                    searchView.showProgress();
                    client.getAutocompletePredictions(newQuery, null, defaultFilter)
                            .addOnSuccessListener(new OnSuccessListener<AutocompletePredictionBufferResponse>() {
                                @Override
                                public void onSuccess(AutocompletePredictionBufferResponse autocompletePredictions) {
                                    //Timber.d("Predictions count: %s", autocompletePredictions.getCount());
                                    //Timber.d("Suggestion 1 %s", PlaceSuggestion.of(autocompletePredictions).get(0).getBody());
                                    searchView.swapSuggestions(PlaceSuggestion.of(autocompletePredictions));
                                }
                            }).addOnCompleteListener(new OnCompleteListener<AutocompletePredictionBufferResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<AutocompletePredictionBufferResponse> task) {
                            searchView.hideProgress();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Timber.e(e);
                        }
                    });
                }
            }
        });

        // keyboard search tapped
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                if (!searchSuggestion.getBody().isEmpty()) {
                    Timber.d("Search suggestion clicked %s", searchSuggestion.getBody());
                    searchView.clearSearchFocus();
                    searchView.setSearchText(searchSuggestion.getBody());
                    weatherViewModel.fetchWeather(searchSuggestion.getBody());
                }
            }

            @Override
            public void onSearchAction(String currentQuery) {
                if (! currentQuery.isEmpty()) {
                    weatherViewModel.fetchWeather(currentQuery);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "SwipeRefresh is a no-op", Toast.LENGTH_SHORT).show();
                Timber.d("SwipeRefresh is a no-op");
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        cache = new LastLocationCache(this);
        if (cache.load() != null) {
            LastLocation lastLocation = cache.load();

            Timber.d(lastLocation.toString());
            searchView.setSearchText(lastLocation.getName());
            weatherViewModel.fetchWeather(lastLocation.getLocation());
        }
    }

    void updateUi(CurrentWeather weather) {
        searchView.setSearchText(weather.getName());

        Timber.d(weather.toString());
        holder.bind(weather);

        cache.save(weather.getName(), weather.getCoord());
    }

    void getWeatherByLocation() {
        if (!PermissionUtil.hasLocationPermission(this)) {
            Timber.d("Requesting location permissions...");
            PermissionUtil.requestLocationPermission(this, LEET_LOCATION_REQUEST_CODE);
        } else {
            //getLastKnownLocation();
            locationViewModel.fetchLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LEET_LOCATION_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Timber.w("Location request was interrupted (by user?)");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // granted
                locationViewModel.fetchLocation();
            } else {
                Timber.d("Location requested denied by user");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else {
            moveTaskToBack(true);
        }
    }
}
