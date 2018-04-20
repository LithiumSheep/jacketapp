package com.lithiumsheep.jacketapp.models.weather;

import com.squareup.moshi.Json;

import java.util.List;

public class CurrentWeather {

    private Coord coord;
    private List<Weather> weather;
    @Json(name = "main")
    private Temperature temperature;
    private Wind wind;
    private Clouds clouds;

    private String id;
    private String name;
    private String dt;  // epoch time of weather

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
