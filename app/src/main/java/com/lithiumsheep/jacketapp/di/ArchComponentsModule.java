package com.lithiumsheep.jacketapp.di;

import com.lithiumsheep.jacketapp.api.HttpClient;
import com.lithiumsheep.jacketapp.arch.viewmodel.AppViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class ArchComponentsModule {

    @Singleton
    @Provides
    AppViewModelFactory provideViewModelFactor() {
        // TODO: service interface can be provided by dagger
        return new AppViewModelFactory(HttpClient.get());
    }
}
