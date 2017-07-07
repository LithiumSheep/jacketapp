package com.lithiumsheep.weatherwrapperwhuut;

import android.location.Location;

/**
 * Created by JesseXLi on 7/7/17.
 */

public abstract class LocationCallback {
    public abstract void onError(Exception exception);
    public abstract void onLocationSuccess(Location location);
}
