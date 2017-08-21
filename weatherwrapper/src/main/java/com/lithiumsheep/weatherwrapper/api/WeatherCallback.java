package com.lithiumsheep.weatherwrapper.api;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.lithiumsheep.weatherwrapper.models.CurrentWeather;
import com.lithiumsheep.weatherwrapper.models.WeatherError;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * An OpenWeatherApi response callback.  onFailure and onSuccess will execute on on the main thread
 */
public abstract class WeatherCallback implements Callback {

    @Override
    public void onFailure(@NonNull Call call, @NonNull final IOException e) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                onFailure(e);
            }
        });
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) {
        Moshi moshi = new Moshi.Builder().build();
        if (response.isSuccessful()) {
            JsonAdapter<CurrentWeather> jsonAdapter = moshi.adapter(CurrentWeather.class);
            try {
                final CurrentWeather weatherPoint = jsonAdapter.fromJson(response.body().string());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(weatherPoint);
                    }
                });
            } catch (IOException e) {
                onFailure(call, e);
            }
        } else {
            JsonAdapter<WeatherError> jsonAdapter = moshi.adapter(WeatherError.class);
            try {
                final WeatherError error = jsonAdapter.fromJson(response.body().string());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(new RuntimeException(error.getMessage()));
                    }
                });
            } catch (IOException e) {
                onFailure(call, e);
            }
        }
    }

    public abstract void onFailure(Exception exception);
    public abstract void onSuccess(CurrentWeather currentWeather);
}
