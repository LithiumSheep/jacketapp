package com.lithiumsheep.jacketapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.lithiumsheep.jacketapp.util.PermissionUtil;
import com.lithiumsheep.jacketapp.viewmodel.LocationViewModel;

import timber.log.Timber;

public abstract class LocationActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 1337;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 7331;

    LocationViewModel locationViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.getlastKnownLocation(this).observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                onLocationFetched(location);
            }
        });
    }

    public void getLastLocation() {
        if (PermissionUtil.hasLocationPermission(this)) {
            locationViewModel.fetchLocation();
        } else {
            PermissionUtil.requestLocationPermission(this, LOCATION_REQUEST_CODE);
        }
    }

    public void setLocationObserver(Observer<Location> observer) {
        locationViewModel.getlastKnownLocation(this).observe(this, observer);
    }

    public void displayPlacesAutocomplete() {
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

    public abstract void onLocationFetched(Location location);

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
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
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                Location location = new Location("GooglePlacesAutocomplete");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);

                onLocationFetched(location);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Timber.w(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
