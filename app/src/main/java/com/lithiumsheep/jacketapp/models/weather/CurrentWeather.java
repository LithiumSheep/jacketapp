package com.lithiumsheep.jacketapp.models.weather;

import java.util.List;

public class CurrentWeather {

    private Coord coord;
    private List<Weather> weather;
    private Metrics main;
    private Wind wind;
    private Clouds clouds;

    private String id;  // ID of city
    private String name;    // name of city
    private String dt;  // epoch time of weather

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Metrics getMetrics() {
        return main;
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

    @Override
    public String toString() {
        return "CurrentWeather{" +
                "coord=" + coord +
                ", weather=" + weather +
                ", main=" + main +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dt='" + dt + '\'' +
                '}';
    }
}
