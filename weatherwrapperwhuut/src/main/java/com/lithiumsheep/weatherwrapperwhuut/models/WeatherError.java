package com.lithiumsheep.weatherwrapperwhuut.models;

import com.squareup.moshi.Json;

public class WeatherError {

    @Json(name = "message")
    private String message;

    public String getMessage() {
        return message;
    }
}
