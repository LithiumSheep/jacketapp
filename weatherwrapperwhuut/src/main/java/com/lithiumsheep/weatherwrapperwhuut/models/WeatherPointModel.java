package com.lithiumsheep.weatherwrapperwhuut.models;

import com.squareup.moshi.Json;

public class WeatherPointModel {

    @Json(name = "main")
    private TemperatureRange temperatureRange;

    @Json(name = "name")
    private String locationName;

    public String getLocationName() {
        return locationName;
    }

    public float getMinTemp() {
        return temperatureRange.minTemperature;
    }

    public float getMaxTemp() {
        return temperatureRange.maxTemperature;
    }

    public float getCurrentTemp() {
        return temperatureRange.currentTemperature;
    }
}
