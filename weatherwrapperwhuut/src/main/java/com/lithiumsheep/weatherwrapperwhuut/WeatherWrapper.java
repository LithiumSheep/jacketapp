package com.lithiumsheep.weatherwrapperwhuut;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import timber.log.Timber;

/**
 * Created by JesseXLi on 7/7/17.
 */

public class WeatherWrapper {

    private static Geocoder geocoder;
    private static int defaultGeocoderMaxResults = 5;

    public static void initialize(Context context) {
        Timber.plant(new Timber.DebugTree());

        geocoder = new Geocoder(context);
    }

    public static void setGeocoderMaxResults(int maxNumberOfResults) {
        defaultGeocoderMaxResults = maxNumberOfResults;
    }

    public static void getAddressForLoc(Location loc, GeocodeCallback geocodeCallback) {
        if (geocoder == null) {
            Timber.e("Geocoder was null.  Make sure you initialize WeatherWrapper before calling getAddressForLoc");
            return;
        }

        try {
            List<Address> addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), defaultGeocoderMaxResults);
            geocodeCallback.onSuccess(addresses);
        } catch (IOException e) {
            geocodeCallback.onError(e);
        }
    }

    @SuppressLint("MissingPermission")  //TODO: Also wrap the location permission
    public static void getLastLocation(Context context, final LocationCallback locationCallback) {
        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context);

        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {

                            // TODO: Cache last location (cache expiry?)

                            locationCallback.onLocationSuccess(task.getResult());
                        } else {
                            locationCallback.onError(task.getException());
                        }
                    }
                });
    }

    private static boolean hasCachedLastLocation() {
        return false;
    }

    public static Location getCachedLastLocation() {
        if (hasCachedLastLocation()) {
            // attempt to grab from disk cache
        }
        return null;
    }
}
