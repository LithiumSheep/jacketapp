package io.lithiumsheep.weatherlib;

import io.lithiumsheep.weatherlib.api.HttpClient;
import io.lithiumsheep.weatherlib.api.NetworkCallback;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherLib {

    public static Call<CurrentWeather> weatherByCity(String city) {
        return HttpClient.getService().weatherByCity(city);
    }

    public static Call<CurrentWeather> weatherByZip(String zip) {
        return HttpClient.getService().weatherByZip(zip);
    }

    public static void getWeatherByZip(String zip, final Callback<CurrentWeather> callback) {
        HttpClient.getService().weatherByZip(zip).enqueue(new NetworkCallback<CurrentWeather>() {
            @Override
            protected void onSuccess(CurrentWeather response) {
                callback.onSuccess(response);
            }

            @Override
            protected void onError(Error error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    public static void getWeatherByQuery(String query, final Callback<CurrentWeather> callback) {
        HttpClient.getService().weatherByCity(query).enqueue(new NetworkCallback<CurrentWeather>() {
            @Override
            protected void onSuccess(CurrentWeather response) {
                callback.onSuccess(response);
            }

            @Override
            protected void onError(Error error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    public interface Callback<T> {
        void onSuccess(T response);
        void onFailure(String reason);
    }
}
