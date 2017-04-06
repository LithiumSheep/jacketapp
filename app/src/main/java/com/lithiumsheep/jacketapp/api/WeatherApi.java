package com.lithiumsheep.jacketapp.api;


import android.location.Location;


import okhttp3.HttpUrl;
import okhttp3.Request;

public class WeatherApi {

    private static String baseUrl() {
        return "http://api.openweathermap.org/data/2.5/weather";
    }

    private static String getAppId() {
        return "2f3e06e1d1a7d3ac7eb733be32aef344";
    }

    public static Request weatherCoord(Location loc) {
        return new Request.Builder()
                .url(new HttpUrl.Builder()
                        .scheme("http")
                        .host("api.openweathermap.org")
                        .addPathSegments("data/2.5/weather")
                        .addQueryParameter("lon",
                                Double.valueOf(loc.getLongitude()).toString())
                        .addQueryParameter("lat",
                                Double.valueOf(loc.getLatitude()).toString())
                        .addQueryParameter("appid", getAppId())
                        .build())
                .build();
    }

}
