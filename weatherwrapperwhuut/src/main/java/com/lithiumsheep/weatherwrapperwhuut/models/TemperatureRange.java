package com.lithiumsheep.weatherwrapperwhuut.models;

import com.squareup.moshi.Json;

public class TemperatureRange {

    public enum TempScale {
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

    TempScale temperatureScale;

    TemperatureRange(float current_temp, float min_temp, float max_temp) {
        this.currentTemperature = current_temp;
        this.minTemperature = min_temp;
        this.maxTemperature = max_temp;
        this.temperatureScale = TempScale.KELVIN;
    }

    public float currentTemperature(TempScale scale) {
        return this.currentTemperature;
    }

    public float maxTemperature(TempScale scale) {
        return this.maxTemperature;
    }

    public float minTemperature(TempScale scale) {
        return this.minTemperature;
    }


}
