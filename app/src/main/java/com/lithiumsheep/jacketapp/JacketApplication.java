package com.lithiumsheep.jacketapp;


import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.File;

import timber.log.Timber;

public class JacketApplication extends Application {

    static File cacheDir;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        AndroidThreeTen.init(this);

        cacheDir = getCacheDir();
    }

    public static File getDefaultCacheDir() {
        return cacheDir;
    }

    public static int getDefaultCacheSize() {
        return 10 * 2014 * 2014; // 10 MiB
    }
}
