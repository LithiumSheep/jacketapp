package com.lithiumsheep.weatherwrapperwhuut.location;

import android.location.Address;

import java.io.IOException;
import java.util.List;

public abstract class GeocodeCallback {
    public abstract void onError(IOException exception);
    public abstract void onSuccess(List<Address> addresses);
}
