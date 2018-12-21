package com.lithiumsheep.jacketapp.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
class PersistenceModule {

    @Provides
    @Named("default")
    SharedPreferences provideDefaultSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Named("location")
    SharedPreferences provideLocationCachePreferences(Application application) {
        return application.getSharedPreferences("location_cache", Context.MODE_PRIVATE);
    }
}
