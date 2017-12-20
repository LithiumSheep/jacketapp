package io.lithiumsheep.reactiveapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import io.lithiumsheep.weatherlib.api.HttpClient;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<CurrentWeather> data;
    public LiveData<CurrentWeather> getWeather(String zip) {
        if (data == null) {
            data = new MutableLiveData<>();
            getData(zip);
        }
        return data;
    }

    private void getData(String zip) {
        HttpClient.getService().getCurrentWeather(zip)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        data.setValue(null);
                    }
                });
    }
}
