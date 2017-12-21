package com.lithiumsheep.weatherwrapper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.support.annotation.RestrictTo;

import com.lithiumsheep.weatherwrapper.api.WeatherApiManager;
import com.lithiumsheep.weatherwrapper.api.WeatherCallback;
import com.lithiumsheep.weatherwrapper.location.LocationCallback;
import com.lithiumsheep.weatherwrapper.location.LocationManager;


public class WeatherWrapper {

    private static WeatherWrapperConfig config = new WeatherWrapperConfig();

    @RestrictTo(RestrictTo.Scope.LIBRARY)   // does this do anything?
    public static WeatherWrapperConfig getConfig() {
        return config;
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    static void setConfig(WeatherWrapperConfig config) {
        WeatherWrapper.config = config;
    }

    @SuppressLint("MissingPermission")  // TODO: Also wrap the location permission
    public static void getLastLocation(Context context, final LocationCallback locationCallback) {
        // TODO: Implement Location cache, if cache is fresh return cached location
        new LocationManager(context).getLastLocation(locationCallback);
    }

    public static void getWeatherForCurrentLocation(Context context, final WeatherCallback weatherCallback) {
        new LocationManager(context).getLastLocation(new LocationCallback() {
            @Override
            public void onError(Exception exception) {
                weatherCallback.onFailure(new WeatherCallback.Error(exception.getMessage()));
            }

            @Override
            public void onLocationSuccess(Location location) {
                WeatherApiManager.getWeatherForLocation(location, weatherCallback);
            }
        });
    }

    public static void getWeatherWithLocation(Location location, WeatherCallback weatherCallback) {
        WeatherApiManager.getWeatherForLocation(location, weatherCallback);
    }

    public static void getWeatherByZip(String zipCode, WeatherCallback weatherCallback) {
        WeatherApiManager.getWeatherByZip(zipCode, weatherCallback);
    }
}
