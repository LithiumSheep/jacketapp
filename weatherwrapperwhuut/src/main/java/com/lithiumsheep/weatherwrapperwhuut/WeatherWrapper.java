package com.lithiumsheep.weatherwrapperwhuut;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import com.lithiumsheep.weatherwrapperwhuut.location.GeocodeCallback;
import com.lithiumsheep.weatherwrapperwhuut.location.LocationCallback;
import com.lithiumsheep.weatherwrapperwhuut.location.LocationManager;
import com.lithiumsheep.weatherwrapperwhuut.weather.WeatherCallback;
import com.lithiumsheep.weatherwrapperwhuut.weather.WeatherHttpClient;
import com.lithiumsheep.weatherwrapperwhuut.weather.WeatherManager;


public class WeatherWrapper {

    public static void enableBasicLogging(boolean enableBasicLogging) {
        WeatherHttpClient.enableLogging(enableBasicLogging);
    }

    public static void enablePrettyLogging(boolean enablePrettyLogging) {
        WeatherHttpClient.enablePrettyLogging(enablePrettyLogging);
    }

    public static void initialize(String weatherApiAppId) {
        // set the weather api APP_ID?
    }

    public static void geocodeAddress(Context context, Location loc, GeocodeCallback geocodeCallback) {
        new LocationManager(context).geocodeAddressFromLocation(loc, geocodeCallback);
    }

    @SuppressLint("MissingPermission")  // TODO: Also wrap the location permission
    public static void getLastLocation(Context context, final LocationCallback locationCallback) {
        // check cache and freshness
        // if fresh, return that
        new LocationManager(context).getLastLocation(locationCallback);
    }

    public static void getWeatherForCurrentLocation(Context context, final WeatherCallback weatherCallback) {
        new LocationManager(context).getLastLocation(new LocationCallback() {
            @Override
            public void onError(Exception exception) {
                weatherCallback.onFailure(exception);
            }

            @Override
            public void onLocationSuccess(Location location) {
                WeatherManager.getWeatherForLocation(location, weatherCallback);
            }
        });
    }

    public static void getWeatherWithLocation(Location location, WeatherCallback weatherCallback) {
        WeatherManager.getWeatherForLocation(location, weatherCallback);
    }

    public static void getWeatherByZip(String zipCode, WeatherCallback weatherCallback) {
        WeatherManager.getWeatherByZip(zipCode, weatherCallback);
    }
}
