package com.lithiumsheep.jacketapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lithiumsheep.jacketapp.api.WeatherApi;
import com.lithiumsheep.jacketapp.api.WeatherHttpClient;
import com.lithiumsheep.jacketapp.util.StorageUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.loc_text)
    TextView locText;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadInitialSet();
        setupGoogleApis();
    }

    private void loadInitialSet() {
        String lastLocation = StorageUtil.getLastLocation(this);
        if (!lastLocation.isEmpty()) {
            locText.setText("Last Known Location: \n" + lastLocation);
        }
    }

    private void setupGoogleApis() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.d("GoogleApi connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("GoogleAPi connection failed");
    }


    @OnClick(R.id.button)
    void openSearchActivity() {
        //startActivity(new Intent(this, SplashActivity.class));
        StorageUtil.clearLocations(this);
    }

    @OnClick(R.id.button2)
    void clicked() {
        getLocation();
    }

    private void getLocation() {
        Timber.d("GET THE DAM LOCATION");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Timber.d("Coarse Location is not granted so request it");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1337);
        } else {
            Timber.d("Permission is granted");
            Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (loc != null) {
                Timber.d("Lat %s ; Lon %s", loc.getLatitude(), loc.getLongitude());
                StorageUtil.storeLastLocation(this, loc);
                locText.setText(locText.getText() + "\nLat " + loc.getLatitude() + " ; Lon " + loc.getLongitude());

                //geoCodeToAddress(loc);
            }
        }
    }

    private void geoCodeToAddress(Location loc) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            Address address = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1).get(0);
            Timber.d("Location corresponds to zip code " + address.getPostalCode());
            Toast.makeText(this, address.getPostalCode(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1337) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Timber.d("Location requested and user denied");
            }
        }
    }

}
