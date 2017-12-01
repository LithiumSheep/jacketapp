package io.lithiumsheep.reactiveapp;

import android.app.Application;

import timber.log.Timber;

public class ReactiveApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
