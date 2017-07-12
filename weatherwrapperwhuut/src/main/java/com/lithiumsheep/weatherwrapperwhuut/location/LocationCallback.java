package com.lithiumsheep.weatherwrapperwhuut.location;

import android.location.Location;

public abstract class LocationCallback {
    public abstract void onError(Exception exception);
    public abstract void onLocationSuccess(Location location);
}
