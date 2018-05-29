package com.lithiumsheep.jacketapp.ui.activities;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.models.LastLocation;
import com.lithiumsheep.jacketapp.models.LastLocationCache;
import com.lithiumsheep.jacketapp.ui.LocationActivity;
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

    LastLocationCache lastLocationCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        Picasso.get()
                .load(BACKDROP_URL_DARKER_RAIN)
                .into(backdrop);

        lastLocationCache = new LastLocationCache(this);
        if (lastLocationCache.load() != null) {
            proceedToMainActivity(false, null);
        } else {

        }

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
            proceedToMainActivity(true, location);
        }
    }


    /**
     * Some weird shit going on here.  This whole screen needs to be redone as MainActivity gains most of the caching location fetching behavior
     * Weirdness starts in {@link #onCreate(Bundle)} above, where the lastLocationCache is checked)}
     */
    @Deprecated
    void proceedToMainActivity(boolean shouldCacheLocation, @Nullable Location location) {
        // cache it
        Timber.d("Lat %s", location.getLatitude());
        Timber.d("Lon %s", location.getLongitude());

        if (shouldCacheLocation) {
            LastLocation loc = new LastLocation("", location);
            lastLocationCache.save(loc);
        }

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
