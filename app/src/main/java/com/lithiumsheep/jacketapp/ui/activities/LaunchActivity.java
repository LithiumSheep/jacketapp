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
import com.lithiumsheep.jacketapp.ui.LocationActivity;
import com.lithiumsheep.jacketapp.util.PermissionUtil;
import com.lithiumsheep.jacketapp.viewmodel.LocationViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class LaunchActivity extends LocationActivity {

    static final String BACKDROP_URL_RAIN = "https://images.pexels.com/photos/243971/pexels-photo-243971.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
    static final String BACKDROP_URL_DARKER_RAIN = "https://images.pexels.com/photos/110874/pexels-photo-110874.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260";
    static final String BACKDROP_URL_BACKUP = "https://images.pexels.com/photos/797853/pexels-photo-797853.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260";

    @BindView(R.id.backdrop)
    ImageView backdrop;

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

    }

    @OnClick(R.id.location_button)
    void onUseCurrentLocationClicked() {
        getLastLocation();
    }

    @OnClick(R.id.search_button)
    void onSearchManuallyClicked() {
        displayPlacesAutocomplete();
    }

    @Override
    public void onLocationFetched(Location location) {
        updateUi(location);
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
}
