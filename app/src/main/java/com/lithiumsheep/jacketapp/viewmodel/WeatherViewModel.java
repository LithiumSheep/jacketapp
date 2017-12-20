package com.lithiumsheep.jacketapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.lithiumsheep.jacketapp.util.Validator;

import io.lithiumsheep.weatherlib.WeatherLib;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import timber.log.Timber;

public class WeatherViewModel extends ViewModel {

    private String lastQuery;

    MutableLiveData<CurrentWeather> data;
    public LiveData<CurrentWeather> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    // called after getData() for observing
    public void fetchWeather(final String query) {
        lastQuery = query;
        if (Validator.matchesZip(query)) {
            WeatherLib.getWeatherByZip(query, new WeatherLib.Callback<CurrentWeather>() {
                @Override
                public void onSuccess(CurrentWeather response) {
                    if (!query.equals(lastQuery)) {
                        return;
                    }
                    data.setValue(response);
                }

                @Override
                public void onFailure(String reason) {
                    Timber.w(reason);
                    data.setValue(null);
                }
            });
        } else {
            WeatherLib.getWeatherByQuery(query, new WeatherLib.Callback<CurrentWeather>() {
                @Override
                public void onSuccess(CurrentWeather response) {
                    if (!query.equals(lastQuery)) {
                        return;
                    }
                    data.setValue(response);
                }

                @Override
                public void onFailure(String reason) {
                    Timber.w(reason);
                    data.setValue(null);
                }
            });
        }
    }
}
