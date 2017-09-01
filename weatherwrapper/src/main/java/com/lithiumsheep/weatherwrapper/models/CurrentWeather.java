package com.lithiumsheep.weatherwrapper.models;

import com.squareup.moshi.Json;

import java.util.List;

public class CurrentWeather {

    @Json(name = "coord")
    private Coord coord;

    @Json(name = "weather")
    private List<Weather> weatherList;

    @Json(name = "main")
    private Temperature temperature;

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

    public Temperature getTemperature() {
        return this.temperature;
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

    @Override
    public String toString() {
        return "Location: " + getLocationName()
                + "\nTemperature: " + getTemperature().current()
                + "\nClouds: " + getClouds().getAll()
                + "\nWind: " + getWind().getSpeed()
                + "\nWeather: " + getWeatherList().get(0).getDescription();
    }
}
