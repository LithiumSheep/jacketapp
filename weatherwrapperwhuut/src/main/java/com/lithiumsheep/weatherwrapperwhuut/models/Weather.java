package com.lithiumsheep.weatherwrapperwhuut.models;

import com.squareup.moshi.Json;

public class Weather {

    @Json(name = "id")
    private int id;

    @Json(name = "main")
    private String main;

    @Json(name="description")
    private String description;

    @Json(name="icon")
    private String name;

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
