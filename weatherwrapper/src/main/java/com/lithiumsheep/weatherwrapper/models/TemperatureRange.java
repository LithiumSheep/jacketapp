package com.lithiumsheep.weatherwrapper.models;

import com.squareup.moshi.Json;

public class TemperatureRange {

    public enum Unit {
        KELVIN,
        CELCIUS,
        FAHRENHEIT
    }

    @Json(name = "temp")
    float currentTemperature;

    @Json(name = "temp_min")
    float minTemperature;

    @Json(name = "temp_max")
    float maxTemperature;

    Unit temperatureUnit;

    TemperatureRange(float current_temp, float min_temp, float max_temp) {
        this.currentTemperature = current_temp;
        this.minTemperature = min_temp;
        this.maxTemperature = max_temp;
        this.temperatureUnit = Unit.KELVIN;
    }

    public float currentTemperature(Unit unit) {
        return this.currentTemperature;
    }

    public float maxTemperature(Unit unit) {
        return this.maxTemperature;
    }

    public float minTemperature(Unit unit) {
        return this.minTemperature;
    }


}
