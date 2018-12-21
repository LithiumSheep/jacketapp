package com.lithiumsheep.jacketapp.arch.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.lithiumsheep.jacketapp.api.OpenWeatherService;

public class AppViewModelFactory implements ViewModelProvider.Factory {

    private OpenWeatherService service;

    public AppViewModelFactory(OpenWeatherService service) {
        this.service = service;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            return (T) new WeatherViewModel(service);
        }
        return null;
    }
}
