package io.lithiumsheep.weatherlib;

import io.lithiumsheep.weatherlib.api.HttpClient;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import retrofit2.Call;

public class WeatherLib {

    public static Call<CurrentWeather> getCurrentWeather(String cityOrZip) {
        return HttpClient.getService().getCurrentWeather(cityOrZip);
    }

    public static Call<Void> getWeatherZip(String zip) {
        return HttpClient.getService().getWeatherByZip(zip);
    }
}
