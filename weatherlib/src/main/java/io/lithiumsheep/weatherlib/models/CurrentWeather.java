package io.lithiumsheep.weatherlib.models;

public class CurrentWeather {

    private Coord coord;
    //List<Weather>
    private String base;
    // temperatures
    private Wind wind;
    private Clouds clouds;

    private String id;
    private String name;

    public Coord getCoord() {
        return coord;
    }

    public String getBase() {
        return base;
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
