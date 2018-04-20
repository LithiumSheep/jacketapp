package com.lithiumsheep.jacketapp.models.weather;

import com.squareup.moshi.Json;

public class Wind {

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
