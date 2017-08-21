package com.lithiumsheep.jacketapp.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.util.PermissionUtil;
import com.lithiumsheep.weatherwrapper.WeatherWrapper;
import com.lithiumsheep.weatherwrapper.models.CurrentWeather;
import com.lithiumsheep.weatherwrapper.api.WeatherCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
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
                    getWeather();
                } else if (item.getItemId() == R.id.action_voice) {
                    Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                    startActivity(intent);
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

    private void getWeather() {
        if (!PermissionUtil.hasLocationPermission(this)) {
            Timber.d("Requesting location permissions...");
            PermissionUtil.requestLocationPermission(this, LEET_LOCATION_REQUEST_CODE);
        } else {
            //performLocationRequestForWeather();
            performWeatherRequestByZip(searchView.getQuery());
        }
    }

    @SuppressLint("MissingPermission")
    private void performLocationRequestForWeather() {
        locText.setText(R.string.loading);
        WeatherWrapper.getWeatherForCurrentLocation(this, new WeatherCallback() {
            @Override
            public void onFailure(Exception exception) {
                Timber.e(exception.getMessage());
                Toast.makeText(MainActivity.this,
                        exception.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Toast.makeText(MainActivity.this,
                        "Success!", Toast.LENGTH_SHORT).show();
                setText(currentWeather);
            }
        });
    }

    private void performWeatherRequestByZip(String zipCode) {
        WeatherWrapper.getWeatherByZip(zipCode, new WeatherCallback() {
            @Override
            public void onFailure(Exception exception) {
                Timber.e(exception.getMessage());
                Toast.makeText(MainActivity.this,
                        exception.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Toast.makeText(MainActivity.this,
                        "Success!", Toast.LENGTH_SHORT).show();
                setText(currentWeather);
            }
        });
    }

    private void setText(CurrentWeather currentWeather) {
        String weatherText = "Location: " + currentWeather.getLocationName()
                + "\nTemperature: " + currentWeather.getCurrentTemp()
                + "\nClouds: " + currentWeather.getClouds().getAll()
                + "\nWind: " + currentWeather.getWind().getSpeed()
                + "\nWeather: " + currentWeather.getWeatherList().get(0).getDescription();
        locText.setText(weatherText);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LEET_LOCATION_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Timber.w("Location request was interrupted (by user?)");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeather();
            } else {
                Timber.d("Location requested denied by user");
            }
        }
    }
}
