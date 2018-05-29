package com.lithiumsheep.jacketapp.models;

import android.location.Location;

public class LastLocation {

    String name;
    Location location;  // LatLng holder;

    public LastLocation(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "LastLocation{" +
                "name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
