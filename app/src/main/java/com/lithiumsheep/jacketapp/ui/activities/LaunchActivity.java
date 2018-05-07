package com.lithiumsheep.jacketapp.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.models.LocationCache;
import com.lithiumsheep.jacketapp.util.PermissionUtil;
import com.lithiumsheep.jacketapp.viewmodel.LocationViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LaunchActivity extends AppCompatActivity {

    static final String BACKDROP_URL_RAIN = "https://images.pexels.com/photos/243971/pexels-photo-243971.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
    static final String BACKDROP_URL_DARKER_RAIN = "https://images.pexels.com/photos/110874/pexels-photo-110874.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260";
    static final String BACKDROP_URL_BACKUP = "https://images.pexels.com/photos/797853/pexels-photo-797853.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260";

    @BindView(R.id.backdrop)
    ImageView backdrop;

    LocationViewModel locationViewModel;
    LocationCache locationCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        Picasso.get()
                .load(BACKDROP_URL_DARKER_RAIN)
                .into(backdrop);

        locationCache = new LocationCache(this);
        if (locationCache.load() != null) {
            cacheLocationAndProceed(locationCache.load());
        } // else proceed

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.getlastKnownLocation(this).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                updateUi(location);
            }
        });
    }

    @OnClick(R.id.location_button)
    void onUseCurrentLocationClicked() {
        if (!PermissionUtil.hasLocationPermission(this)) {
            PermissionUtil.requestLocationPermission(this, 1337);
        } else {
            locationViewModel.fetchLocation();
        }
    }

    @OnClick(R.id.search_button)
    void onSearchManuallyClicked() {
        //startActivity(new Intent(this, MainActivity.class));
        AutocompleteFilter filter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setCountry("USA")
                .build();
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(filter)
                    .build(this);
            startActivityForResult(intent, 7331);
        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e) {
            Timber.e(e);
        }
    }

    void updateUi(Location location) {
        if (location == null) {
            // provide reasons it could be null
        } else {
            cacheLocationAndProceed(location);
        }
    }

    void cacheLocationAndProceed(Location location) {
        // cache it
        Timber.d("Lat %s", location.getLatitude());
        Timber.d("Lon %s", location.getLongitude());

        new LocationCache(this).save(location);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1337) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 7331) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Timber.i("Place: %s", place.getName());
                Timber.i("Place: %s", place.getAddress());

                Location location = new Location("GooglePlacesAutocomplete");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);

                updateUi(location);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Timber.w(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
