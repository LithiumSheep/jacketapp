package com.lithiumsheep.weatherwrapperwhuut.models;

import com.squareup.moshi.Json;

import java.util.List;

public class CurrentWeather {

    @Json(name = "coord")
    private Coord coord;

    @Json(name = "weather")
    private List<Weather> weatherList;

    @Json(name = "main")
    private TemperatureRange temperatureRange;

    // visibility?

    @Json(name = "wind")
    private Wind wind;

    @Json(name = "clouds")
    private Clouds clouds;

    // what's dt?

    // sys

    @Json(name = "name")
    private String locationName;

    public Coord getCoords() {
        return coord;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
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

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public String getLocationName() {
        return locationName;
    }
}
