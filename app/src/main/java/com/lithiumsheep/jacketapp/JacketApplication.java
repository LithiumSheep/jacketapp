package com.lithiumsheep.jacketapp;


import android.app.Application;

import com.lithiumsheep.weatherwrapperwhuut.WeatherWrapperConfig;

import timber.log.Timber;

public class JacketApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        new WeatherWrapperConfig.Builder()
                .setAppId(getString(R.string.openweathermap_appid))   // required
                .setBasicLoggingEnabled(false)  // default: false
                .setPrettyLoggingEnabled(true) // default: false
                .build().apply();
    }
}
