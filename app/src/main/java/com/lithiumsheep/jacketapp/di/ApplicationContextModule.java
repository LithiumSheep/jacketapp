package com.lithiumsheep.jacketapp.di;

import android.app.Application;

import com.lithiumsheep.jacketapp.JacketApplication;

import dagger.Binds;
import dagger.Module;

@Module
abstract class ApplicationContextModule {
    @Binds
    abstract Application application(JacketApplication application);
}
