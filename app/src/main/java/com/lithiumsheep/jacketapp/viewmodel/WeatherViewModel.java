package com.lithiumsheep.jacketapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
import android.support.annotation.NonNull;

import com.lithiumsheep.jacketapp.api.HttpClient;
import com.lithiumsheep.jacketapp.api.NetworkCallback;
import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;

public class WeatherViewModel extends ViewModel {

    private String lastQuery;

    private MutableLiveData<CurrentWeather> data;
    private MutableLiveData<Boolean> loading;

    public LiveData<CurrentWeather> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    public LiveData<Boolean> getLoadingState() {
        if (loading == null) {
            loading = new MutableLiveData<>();
        }
        return loading;
    }

    public void getWeather(final String query) {
        lastQuery = query;

        loading.setValue(true);

        HttpClient.get()
                .getWeather(query)
                .enqueue(new NetworkCallback<CurrentWeather>() {
                    @Override
                    protected void onSuccess(CurrentWeather response) {
                        if (!query.equals(lastQuery)) {
                            return;
                        }
                        loading.setValue(false);
                        data.setValue(response);
                    }

                    @Override
                    protected void onError(Error error) {
                        loading.setValue(false);
                    }
                });
    }

    public void getWeather(@NonNull Location location) {
        loading.setValue(true);

        HttpClient.get()
                .getWeather(location.getLatitude(), location.getLongitude())
                .enqueue(new NetworkCallback<CurrentWeather>() {
                    @Override
                    protected void onSuccess(CurrentWeather response) {
                        loading.setValue(false);
                        data.setValue(response);
                    }

                    @Override
                    protected void onError(Error error) {
                        loading.setValue(false);
                    }
                });
    }
}
