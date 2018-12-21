package com.lithiumsheep.jacketapp.di;

import com.lithiumsheep.jacketapp.ui.activities.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}
