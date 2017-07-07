package com.lithiumsheep.jacketapp;


import android.app.Application;

import com.lithiumsheep.weatherwrapperwhuut.WeatherWrapper;
import com.lithiumsheep.weatherwrapperwhuut.util.LocationCache;

import timber.log.Timber;

public class JacketApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        WeatherWrapper.initialize(this, LocationCache.CachePolicy.AGGRESSIVE);
    }
}
