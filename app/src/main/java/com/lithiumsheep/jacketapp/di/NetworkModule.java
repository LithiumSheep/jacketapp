package com.lithiumsheep.jacketapp.di;

import android.app.Application;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;

@Module
class NetworkModule {

    @Singleton
    @Provides
    Cache provideOkhttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;   // 10 mb
        File file = application.getCacheDir();
        return new Cache(file, cacheSize);
    }
}
