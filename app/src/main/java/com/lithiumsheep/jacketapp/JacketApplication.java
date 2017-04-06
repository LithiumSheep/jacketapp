package com.lithiumsheep.jacketapp;


import android.app.Application;

import timber.log.Timber;

public class JacketApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
