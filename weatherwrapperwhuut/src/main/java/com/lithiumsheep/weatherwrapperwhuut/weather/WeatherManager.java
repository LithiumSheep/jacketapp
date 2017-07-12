package com.lithiumsheep.weatherwrapperwhuut.weather;

import android.location.Location;

public class WeatherManager {

    public static void getWeatherForLocation(Location location, WeatherCallback weatherCallback) {
        WeatherApi.weatherRequestByCoord(location);

        WeatherHttpClient.get(
                WeatherApi.weatherRequestByCoord(location),
                weatherCallback);
    }
}
