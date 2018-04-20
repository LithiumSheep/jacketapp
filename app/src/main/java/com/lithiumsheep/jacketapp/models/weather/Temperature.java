package com.lithiumsheep.jacketapp.models.weather;

import com.squareup.moshi.Json;

/**
 * Default unit is Kevlin, use the Converter util to convert to other units
 */
public class Temperature {

    private float temp;
    @Json(name = "temp_min")
    private float tempMin;
    @Json(name = "temp_max")
    private float tempMax;

    private int pressure;
    private int humidity;

    public float getTemp() {
        return temp;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }
}
