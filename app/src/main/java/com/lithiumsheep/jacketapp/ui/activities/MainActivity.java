package com.lithiumsheep.jacketapp.ui.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.models.search.PlaceSuggestion;
import com.lithiumsheep.jacketapp.util.Converter;
import com.lithiumsheep.jacketapp.util.DrawerHelper;
import com.lithiumsheep.jacketapp.util.PermissionUtil;
import com.lithiumsheep.jacketapp.util.TimeUtil;
import com.lithiumsheep.jacketapp.viewmodel.WeatherViewModel;
import com.mikepenz.materialdrawer.Drawer;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int LEET_LOCATION_REQUEST_CODE = 1337;

    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;
    @BindView(R.id.view_group)
    ViewGroup viewGroup;

    // using activity_main_alternate
    /*@BindView(R.id.text)
    TextView weatherText;
    @BindView(R.id.main_progress)
    ProgressBar progressBar;*/

    // using activity_main
    @BindView(R.id.weather_time)
    TextView weatherTime;
    @BindView(R.id.weather_location)
    TextView weatherLocation;
    @BindView(R.id.temperature_high)
    TextView tempHigh;
    @BindView(R.id.temperature_low)
    TextView tempLow;
    @BindView(R.id.temperature_main)
    TextView tempMain;
    @BindView(R.id.weather_text)
    TextView weatherText;
    @BindView(R.id.jacket_text)
    TextView jacketText;

    // view and model
    private Drawer drawer;
    private WeatherViewModel weatherViewModel;

    // autocomplete
    private GeoDataClient client;
    private AutocompleteFilter defaultFilter;

    // location
    private FusedLocationProviderClient locationClient;

    // debug
    boolean disableAutocomplete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        drawer = DrawerHelper.attach(this);
        searchView.attachNavigationDrawerToMenuButton(drawer.getDrawerLayout());

        client = Places.getGeoDataClient(this, null);
        defaultFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setCountry("USA")
                .build();
        locationClient = LocationServices.getFusedLocationProviderClient(this);

        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.getData().observe(this, new Observer<CurrentWeather>() {
            @Override
            public void onChanged(@Nullable CurrentWeather currentWeather) {
                if (currentWeather != null) {
                    updateUi(currentWeather);
                } else {
                    Toast.makeText(MainActivity.this, "There was a problem fetching your result", Toast.LENGTH_SHORT).show();
                    Timber.w("currentWeather came back null");
                }
            }
        });

        // menu clicked
        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_search) {
                    // check if last location exists on disk, if yes ask to continue
                    weatherViewModel.fetchWeather(searchView.getQuery());
                } else if (item.getItemId() == R.id.action_location) {
                    getWeatherByLocation();
                }
            }
        });

        // keyboard search tapped
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Timber.d("Search suggestion clicked %s", searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String currentQuery) {
                weatherViewModel.fetchWeather(currentQuery);
            }
        });

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
    }

    private void getWeatherByLocation() {
        if (!PermissionUtil.hasLocationPermission(this)) {
            Timber.d("Requesting location permissions...");
            PermissionUtil.requestLocationPermission(this, LEET_LOCATION_REQUEST_CODE);
        } else {
            getLastKnownLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastKnownLocation() {
        locationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location == null) {
                    Timber.w("Location was null");
                    performLocationRequest();
                } else {
                    weatherViewModel.fetchWeather(location);
                    Timber.d("lastKnownLocation lat %s lon %s", location.getLatitude(), location.getLongitude());
                }
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Timber.e(e);
            }
        }).addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.getException() != null) {
                    Timber.w(task.getException());
                }
                Timber.d("lastKnownLocation Complete %s", task.isComplete());
                Timber.d("lastKnowLocation Succcessful %s", task.isSuccessful());
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void performLocationRequest() {
        Timber.d("Creating location updates request");
        LocationRequest request = new LocationRequest();
        request.setInterval(10_000);
        request.setFastestInterval(5_000);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationClient.requestLocationUpdates(request, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Timber.d("LocationRequest result came back");
                for (Location location : locationResult.getLocations()) {
                    Timber.d("LocationRequest lat %s lon %s", location.getLatitude(), location.getLongitude());
                }
            }
        }, null);
    }

    private void updateUi(CurrentWeather weather) {
        weatherTime.setText(TimeUtil.getTimeForNow());
        weatherLocation.setText(weather.getName());
        tempHigh.setText("High " + Converter.tempForDisplay(weather.getTemperature().getTempMax()));
        tempLow.setText(("Low " + Converter.tempForDisplay(weather.getTemperature().getTempMin())));
        tempMain.setText(Converter.tempForDisplay(weather.getTemperature().getTemp()));
        weatherText.setText(weather.getWeather().get(0).getDescription());

        // jacketText.setText("It's cold, put a Jacket on!");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LEET_LOCATION_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Timber.w("Location request was interrupted (by user?)");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // granted
                getLastKnownLocation();
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
            super.onBackPressed();
        }
    }
}
