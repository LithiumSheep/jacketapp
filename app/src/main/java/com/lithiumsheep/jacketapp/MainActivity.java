package com.lithiumsheep.jacketapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
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
import com.lithiumsheep.jacketapp.api.WeatherApi;
import com.lithiumsheep.jacketapp.api.WeatherHttpClient;
import com.lithiumsheep.jacketapp.util.StorageUtil;
import com.lithiumsheep.weatherwrapperwhuut.GeocodeCallback;
import com.lithiumsheep.weatherwrapperwhuut.LocationCallback;
import com.lithiumsheep.weatherwrapperwhuut.WeatherWrapper;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alternate);
        ButterKnife.bind(this);

        loadInitialSet();

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

        WeatherWrapper.getLastLocation(this, new LocationCallback() {
            @Override
            public void onError(Exception exception) {
                Timber.e(exception);
            }

            @Override
            public void onLocationSuccess(Location location) {
                //Timber.d("Location request came back with lat %s lon %s", location.getLatitude(), location.getLongitude());
                locText.setText(locText.getText() + "\nLat " + location.getLatitude() + " ; Lon " + location.getLongitude());
                geoCodeToAddress(location);
            }
        });
    }

    private void geoCodeToAddress(Location loc) {
        if (StorageUtil.getLastLocation(MainActivity.this) == null) {
            Timber.w("SOMETHING IS HORRIBLY WRONG");
        }
        WeatherWrapper.getAddressForLoc(loc, new GeocodeCallback() {
            @Override
            public void onError(IOException exception) {
                Timber.e(exception);
            }

            @Override
            public void onSuccess(List<Address> addresses) {
                for (Address address : addresses) {
                    if (address.getPostalCode() != null) {
                        Timber.d("First valid zip is " + address.getPostalCode());
                        StorageUtil.storeLastZip(MainActivity.this, address.getPostalCode());
                        break;
                    }
                }
            }
        });
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
