package com.lithiumsheep.jacketapp.arch.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
import android.support.annotation.NonNull;

import com.lithiumsheep.jacketapp.api.NetworkCallback;
import com.lithiumsheep.jacketapp.api.OpenWeatherService;
import com.lithiumsheep.jacketapp.arch.Resource;
import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;

public class WeatherViewModel extends ViewModel {

    private OpenWeatherService service;

    private MutableLiveData<Resource<CurrentWeather>> currentWeather;

    WeatherViewModel(OpenWeatherService service) {
        this.service = service;
    }

    public LiveData<Resource<CurrentWeather>> getWeather() {
        if (currentWeather == null) {
            currentWeather = new MutableLiveData<>();
        }
        return currentWeather;
    }

    public void fetchWeather(final String query) {
        currentWeather.setValue(Resource.<CurrentWeather>loading());

        service.getWeather(query)
                .enqueue(new NetworkCallback<CurrentWeather>() {
                    @Override
                    protected void onSuccess(CurrentWeather response) {
                        currentWeather.setValue(Resource.success(response));
                    }

                    @Override
                    protected void onError(Throwable error) {
                        currentWeather.setValue(Resource.<CurrentWeather>error(error));
                    }
                });
    }

    public void fetchWeather(@NonNull Location location) {
        currentWeather.setValue(Resource.<CurrentWeather>loading());

        service.getWeather(location.getLatitude(), location.getLongitude())
                .enqueue(new NetworkCallback<CurrentWeather>() {
                    @Override
                    protected void onSuccess(CurrentWeather response) {
                        currentWeather.setValue(Resource.success(response));
                    }

                    @Override
                    protected void onError(Throwable error) {
                        currentWeather.setValue(Resource.<CurrentWeather>error(error));
                    }
                });
    }
}
