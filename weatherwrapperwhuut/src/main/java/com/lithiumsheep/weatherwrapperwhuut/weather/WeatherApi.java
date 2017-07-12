package com.lithiumsheep.weatherwrapperwhuut.weather;

import android.location.Location;

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
        return "2f3e06e1d1a7d3ac7eb733be32aef344";
    }

    static Request weatherRequestByCoord(Location loc) {
        return new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme("http")
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
}
