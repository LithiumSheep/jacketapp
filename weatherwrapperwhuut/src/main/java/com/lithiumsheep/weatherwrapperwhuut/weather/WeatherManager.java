package com.lithiumsheep.weatherwrapperwhuut.weather;

import android.location.Location;

public class WeatherManager {

    public static void getWeatherForLocation(Location location, WeatherCallback weatherCallback) {
        WeatherHttpClient.get(
                WeatherApi.weatherRequestByCoord(location),
                weatherCallback);
    }

    public static void getWeatherByZip(String zipCode, WeatherCallback weatherCallback) {
        WeatherHttpClient.get(
                WeatherApi.weatherRequestByZip(zipCode),
                weatherCallback);
    }
}
