package com.lithiumsheep.jacketapp.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
class PersistenceModule {

    @Provides
    @Named("Default")
    SharedPreferences provideDefaultSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
