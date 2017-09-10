package com.lithiumsheep.weatherwrapper.models;

import com.squareup.moshi.Json;

public class Weather {

    @Json(name = "id")
    private int id;

    @Json(name = "main")
    private String main;

    @Json(name="description")
    private String description;

    @Json(name="icon")
    private String icon;

    public int getId() {
        return id;
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
}
