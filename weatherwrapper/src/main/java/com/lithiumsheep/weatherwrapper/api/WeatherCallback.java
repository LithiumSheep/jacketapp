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
                onFailure(new Error(e.getMessage()));
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
            JsonAdapter<Error> jsonAdapter = moshi.adapter(Error.class);
            try {
                final Error error = jsonAdapter.fromJson(response.body().string());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onFailure(error);
                    }
                });
            } catch (IOException e) {
                onFailure(call, e);
            }
        }
    }

    public abstract void onFailure(Error error);    // used to be Exception exception
    public abstract void onSuccess(CurrentWeather currentWeather);

    // TODO: Use Error class instead of WeatherError
    public static class Error {
        private String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
