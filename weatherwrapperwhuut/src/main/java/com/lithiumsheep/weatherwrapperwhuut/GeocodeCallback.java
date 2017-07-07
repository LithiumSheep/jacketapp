package com.lithiumsheep.weatherwrapperwhuut;

import android.location.Address;

import java.io.IOException;
import java.util.List;

/**
 * Created by JesseXLi on 7/7/17.
 */

public abstract class GeocodeCallback {
    public abstract void onError(IOException exception);
    public abstract void onSuccess(List<Address> addresses);
}
