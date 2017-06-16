package com.lithiumsheep.jacketapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.lithiumsheep.jacketapp.api.WeatherApi;
import com.lithiumsheep.jacketapp.api.WeatherHttpClient;
import com.lithiumsheep.jacketapp.util.StorageUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int LEET_LOCATION_REQUEST_CODE = 1337;

    @BindView(R.id.floating_search_view)
    FloatingSearchView searchView;
    @BindView(R.id.loc_text)
    TextView locText;

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alternate);
        ButterKnife.bind(this);

        loadInitialSet();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.action_location) {
                    // check if last location exists on disk, if yes ask to continue
                    getLastLocation();
                }
            }
        });

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Timber.d("%s changed to %s", oldQuery, newQuery);
                /*Suggestion s = new Suggestion(newQuery);
                List<Suggestion> list = new ArrayList<>();
                list.add(s);
                searchView.swapSuggestions(list);*/
            }
        });
    }

    private void loadInitialSet() {
        Location lastLocation = StorageUtil.getLastLocation(this);
        if (lastLocation != null) {
            locText.setText("Last Known Location: \nLat " + lastLocation.getLatitude() + " ; Lon " + lastLocation.getLongitude());
        }
    }

    @OnClick(R.id.button2)
    void clicked() {
        getLastLocation();
    }

    @OnClick(R.id.button)
    void openSearchActivity() {
        //StorageUtil.clearAll(this);
        //confirmZipCode("22102");
    }

    private void getLastLocation() {
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            performLocationRequest();
        }
    }

    @SuppressLint("MissingPermission")
    private void performLocationRequest() {
        Timber.d("Requesting Last Location...");

        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location loc = task.getResult();

                            Timber.d("Location request came back with lat %s lon %s", loc.getLatitude(), loc.getLongitude());
                            StorageUtil.storeLastLocation(MainActivity.this, loc);

                            locText.setText(locText.getText() + "\nLat " + loc.getLatitude() + " ; Lon " + loc.getLongitude());

                            //geoCodeToAddress(loc);
                        } else {
                            Timber.w("getLastLocation Exception", task.getException());
                        }
                    }
                });
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
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        Timber.d("Requesting location permissions...");
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LEET_LOCATION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LEET_LOCATION_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Timber.w("Location request was interrupted (by user?)");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Timber.d("Location requested denied by user");
            }
        }
    }
}
