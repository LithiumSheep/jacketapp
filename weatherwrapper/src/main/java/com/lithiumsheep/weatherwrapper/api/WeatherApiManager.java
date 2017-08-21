package com.lithiumsheep.weatherwrapper.api;

import android.location.Location;
import android.support.annotation.RestrictTo;

public class WeatherApiManager {

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static void getWeatherForLocation(Location location, WeatherCallback weatherCallback) {
        WeatherHttpClient.get(
                WeatherApi.weatherRequestByCoord(location),
                weatherCallback);
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static void getWeatherByZip(String zipCode, WeatherCallback weatherCallback) {
        WeatherHttpClient.get(
                WeatherApi.weatherRequestByZip(zipCode),
                weatherCallback);
    }
}
