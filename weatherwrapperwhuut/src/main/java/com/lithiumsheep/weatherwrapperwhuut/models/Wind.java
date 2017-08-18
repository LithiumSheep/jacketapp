package com.lithiumsheep.weatherwrapperwhuut.models;

import com.squareup.moshi.Json;

public class Wind {

    @Json(name = "speed")
    private float speed;

    @Json(name = "deg")
    private float degrees;

    public float getSpeed() {
        return speed;
    }

    public float getDegrees() {
        return degrees;
    }
}
