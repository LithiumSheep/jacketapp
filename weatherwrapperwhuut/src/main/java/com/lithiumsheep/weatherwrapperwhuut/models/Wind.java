package com.lithiumsheep.weatherwrapperwhuut.models;

import com.squareup.moshi.Json;

public class Wind {

    @Json(name = "speed")
    private float speed;

    @Json(name = "deg")
    private int degrees;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }
}
