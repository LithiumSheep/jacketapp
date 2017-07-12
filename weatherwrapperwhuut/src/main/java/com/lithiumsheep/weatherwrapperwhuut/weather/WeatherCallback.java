package com.lithiumsheep.weatherwrapperwhuut.weather;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.lithiumsheep.weatherwrapperwhuut.models.WeatherPointModel;
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
        if (response.isSuccessful()) {
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<WeatherPointModel> jsonAdapter = moshi.adapter(WeatherPointModel.class);
            try {
                final WeatherPointModel weatherPoint = jsonAdapter.fromJson(response.body().string());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onSuccess(weatherPoint);
                    }
                });
            } catch (IOException e) {
                onFailure(e);
            }
        } else {
            onFailure(new Exception());
        }
    }

    public abstract void onFailure(Exception exception);
    public abstract void onSuccess(WeatherPointModel weatherPoint);
}
