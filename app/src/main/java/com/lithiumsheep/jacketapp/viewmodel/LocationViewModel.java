package com.lithiumsheep.jacketapp.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import timber.log.Timber;

public class LocationViewModel extends ViewModel {

    private FusedLocationProviderClient locationClient;

    private SettingsClient settingsClient;

    private boolean isStreamingLocation;
    private LocationCallback locationCallback;

    private MutableLiveData<Location> lastLocation;
    public LiveData<Location> getlastKnownLocation(Context context) {
        if (locationClient == null) {
            locationClient = LocationServices.getFusedLocationProviderClient(context);
        }
        if (settingsClient == null) {
            settingsClient = LocationServices.getSettingsClient(context);
        }
        if (lastLocation == null) {
            lastLocation = new MutableLiveData<>();
        }
        return lastLocation;
    }

    @SuppressLint("MissingPermission")
    public void fetchLocation() {
        locationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lastLocation.setValue(location);
                } else {
                    Timber.d("ViewModel location is null, trying to stream location instead");
                    if (isStreamingLocation) {
                        Timber.w("ViewModel Location Updates are already queued...no-op");
                    } else {
                        streamLocation();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Timber.e(e);
            }
        }).addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // log statements
                if (task.getException() != null) {
                    Timber.w(task.getException());
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void streamLocation() {
        LocationRequest request = new LocationRequest();
        request.setInterval(10_000);
        request.setFastestInterval(2_000);
        request.setNumUpdates(2);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // TODO: does this live here?
        checkLocationSettings(request);

        isStreamingLocation = true;
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                Timber.d("Streaming location available %s", locationAvailability.isLocationAvailable());
            }

            @Override
            public void onLocationResult(LocationResult locationResult) {
                Timber.d("ViewModel LocationRequest result came back");
                for (Location location : locationResult.getLocations()) {
                    isStreamingLocation = false;
                    Timber.d("ViewModel LocationRequest lat %s lon %s", location.getLatitude(), location.getLongitude());
                }
            }
        };
        locationClient.requestLocationUpdates(request, locationCallback, null);
        Timber.d("ViewModel Location Updates queued");
    }

    private void checkLocationSettings(LocationRequest request) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        settingsClient.checkLocationSettings(builder.build()).addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                if (task.isSuccessful()) {
                    Timber.d("LocationSettings location usable %s", task.getResult().getLocationSettingsStates().isLocationUsable());
                    Timber.d("LocationSettings location present %s", task.getResult().getLocationSettingsStates().isLocationPresent());
                }
            }
        });
    }


    // TODO: Should live on the LifecycleObserver object
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPauseCalled() {
        if (isStreamingLocation) {
            Timber.d("Current lifecycleOwner is streaming location, stopping stream in ON_PAUSE");
            locationClient.removeLocationUpdates(locationCallback);
        }
    }
}
