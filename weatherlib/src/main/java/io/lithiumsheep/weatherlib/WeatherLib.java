package io.lithiumsheep.weatherlib;

import io.lithiumsheep.weatherlib.api.HttpClient;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import retrofit2.Call;

public class WeatherLib {

    public static Call<CurrentWeather> getCurrentWeatherByCity(String city) {
        return HttpClient.getService().weatherByCity(city);
    }

    public static Call<CurrentWeather> getWeatherZip(String zip) {
        return HttpClient.getService().weatherByZip(zip);
    }
}
