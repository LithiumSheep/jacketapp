package com.lithiumsheep.jacketapp;


import android.app.Activity;
import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.lithiumsheep.jacketapp.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class JacketApplication extends Application implements HasActivityInjector {

    @Inject DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .create(this)
                .inject(this);

        Timber.plant(new Timber.DebugTree());

        AndroidThreeTen.init(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
