package com.lithiumsheep.jacketapp.models.weather;

public class Wind {

    private float speed;
    private float deg;

    public float getSpeed() {
        return speed;
    }

    public float getDegrees() {
        return deg;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", deg=" + deg +
                '}';
    }
}
