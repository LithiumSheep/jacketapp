package com.lithiumsheep.weatherwrapper.api;

import android.location.Location;

import com.lithiumsheep.weatherwrapper.WeatherWrapper;

import okhttp3.HttpUrl;
import okhttp3.Request;

class WeatherApi {
    private static String baseUrl() {
        return "api.openweathermap.org";
    }

    private static String pathSegments() {
        return "data/2.5/weather";
    }

    private static String getAppId() {
        return WeatherWrapper.getConfig().getAppID();
    }

    static Request weatherRequestByCoord(Location loc) {
        return new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme("https")
                        .host(baseUrl())
                        .addPathSegments(pathSegments())
                        .addQueryParameter("lat",
                                Double.valueOf(loc.getLatitude()).toString())
                        .addQueryParameter("lon",
                                Double.valueOf(loc.getLongitude()).toString())
                        .addQueryParameter("appid", getAppId())
                        .build())
                .build();
    }

    static Request weatherRequestByZip(String zipCode) {
        return new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme("https").host(baseUrl())
                        .addPathSegments(pathSegments())
                        .addQueryParameter("zip", zipCode)
                        .addQueryParameter("appid", getAppId())
                        .build())
                .build();
    }
}
