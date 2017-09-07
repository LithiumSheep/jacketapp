package com.lithiumsheep.weatherwrapper.models;

public class WeatherError {

    private String message;

    public WeatherError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
