package io.lithiumsheep.reactiveapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.lithiumsheep.weatherlib.WeatherLib;
import io.lithiumsheep.weatherlib.api.WeatherCallback;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherLib.getCurrentWeather("Seattle")
                .subscribeWith(new WeatherCallback<CurrentWeather>() {
                    @Override
                    public void onError(Error error) {
                        Timber.w(error.getMessage());
                    }

                    @Override
                    public void onSuccess(CurrentWeather response) {
                        Timber.d("Got a response");
                    }
                });
    }
}
