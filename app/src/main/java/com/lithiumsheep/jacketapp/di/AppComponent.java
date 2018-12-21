package com.lithiumsheep.jacketapp.di;

import com.lithiumsheep.jacketapp.JacketApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationContextModule.class,
        ActivityModule.class,
        PersistenceModule.class,
        NetworkModule.class
})
public interface AppComponent extends AndroidInjector<JacketApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<JacketApplication> {
    }
}
