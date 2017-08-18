package com.lithiumsheep.weatherwrapperwhuut.models;

import com.squareup.moshi.Json;

public class Clouds {

    @Json(name = "all")
    private int all;

    public int getAll() {
        return all;
    }
}
