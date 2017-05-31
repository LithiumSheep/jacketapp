package com.lithiumsheep.jacketapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.lithiumsheep.jacketapp.api.WeatherApi;
import com.lithiumsheep.jacketapp.api.WeatherHttpClient;
import com.lithiumsheep.jacketapp.util.StorageUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;
    @BindView(R.id.loc_text)
    TextView locText;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alternate);
        ButterKnife.bind(this);

        loadInitialSet();
        setupGoogleApis();

        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_location) {
                    // check if last location exists on disk, if yes ask to continue
                    getLocation();
                }
            }
        });

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Timber.d("%s changed to %s", oldQuery, newQuery);
                Suggestion s = new Suggestion(newQuery);
                List<Suggestion> list = new ArrayList<>();
                list.add(s);
                searchView.swapSuggestions(list);
            }
        });
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
        Timber.d("GoogleApi connection OKAY");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Timber.d("GoogleApi connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("GoogleAPi connection failed");
    }

    @OnClick(R.id.button2)
    void clicked() {
        getLocation();
    }

    @OnClick(R.id.button)
    void openSearchActivity() {
        //StorageUtil.clearAll(this);
        confirmZipCode("22102");
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

                geoCodeToAddress(loc);
            } else {
                Timber.e("Location was null, wonder why");
            }
        }
    }

    private void geoCodeToAddress(Location loc) {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 5);
            for (Address address : addresses) {
                if (address.getPostalCode() != null) {
                    Timber.d("First valid zip is " + address.getPostalCode());
                    StorageUtil.storeLastZip(this, address.getPostalCode());
                    break;
                }
            }

            if (StorageUtil.getLastLocation(this) == null) {
                Timber.d("SOMETHING IS HORRIBLY WRONG");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void confirmZipCode(String zip) {
        new MaterialDialog.Builder(this)
                .title("Is this your zip code?")
                .content(zip)
                .positiveText("Yeah")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {

                    }
                })
                .negativeText("Nope")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                        Timber.d("FUUUUUUU");
                    }
                })
                .build().show();
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
