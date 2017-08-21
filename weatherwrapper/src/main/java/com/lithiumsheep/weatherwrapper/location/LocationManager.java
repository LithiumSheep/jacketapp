package com.lithiumsheep.weatherwrapper.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationManager {

    private FusedLocationProviderClient fusedLocationClient;
    //private Geocoder geocoder;
    //private int defaultGeocoderMaxResults = 5;

    public LocationManager(Context context) {
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context);
        //geocoder = new Geocoder(context);
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation(final LocationCallback callback) {
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            callback.onLocationSuccess(task.getResult());
                        } else {
                            callback.onError(task.getException());
                        }
                    }
                });
    }

    /*public void setGeocoderMaxResults(int maxNumberOfResults) {
        defaultGeocoderMaxResults = maxNumberOfResults;
    }

    public void geocodeAddressFromLocation(Location location, GeocodeCallback geocodeCallback) {
        if (geocoder == null) {
            Log.e("LocationManager", "Geocoder was null.  Make sure you initialize WeatherWrapper before calling getAddressForLoc");
            return;
        }

        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), defaultGeocoderMaxResults);
            geocodeCallback.onSuccess(addresses);
        } catch (IOException e) {
            geocodeCallback.onError(e);
        }
    }*/
}
