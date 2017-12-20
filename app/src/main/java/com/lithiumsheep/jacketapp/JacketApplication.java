package com.lithiumsheep.jacketapp;


import android.app.Application;

import com.lithiumsheep.jacketapp.models.Defaults;

import timber.log.Timber;

public class JacketApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        // get Application defaults from preferences
        Defaults userDefaults = new Defaults(this);

        /*new WeatherWrapperConfig.Builder()
                .setAppId(getString(R.string.openweathermap_appid))   // required
                .setBasicLoggingEnabled(false)  // default: false
                .setPrettyLoggingEnabled(true) // default: false
                .withTemperatureUnit(userDefaults.getDefaultUnit())
                .build().apply();*/
    }
}
