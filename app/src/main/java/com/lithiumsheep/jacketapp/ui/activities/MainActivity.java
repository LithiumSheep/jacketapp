package com.lithiumsheep.jacketapp.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.api.WeatherApi;
import com.lithiumsheep.jacketapp.api.WeatherHttpClient;
import com.lithiumsheep.weatherwrapperwhuut.WeatherWrapper;
import com.lithiumsheep.weatherwrapperwhuut.models.WeatherPointModel;
import com.lithiumsheep.weatherwrapperwhuut.weather.WeatherCallback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
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

    private void getLastLocation() {
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            performLocationRequest();
        }
    }

    @SuppressLint("MissingPermission")
    private void performLocationRequest() {
        locText.setText("Loading...");
        WeatherWrapper.getWeatherForCurrentLocation(this, new WeatherCallback() {
            @Override
            public void onFailure(Exception exception) {
                Timber.e(exception);
                locText.setText("Hit onFailure of Weathercallback \n" + exception.getMessage());
            }

            @Override
            public void onSuccess(WeatherPointModel weatherPoint) {
                locText.setText("Location: " + weatherPoint.getLocationName() + "\n" + "Temperature: " + weatherPoint.getCurrentTemp());
            }
        });
    }

    private void makeWeatherRequest(Location location) {
        Request weatherRequest = WeatherApi.weatherRequestByCoord(location);

        WeatherHttpClient.get(weatherRequest, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Timber.e(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            locText.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
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
