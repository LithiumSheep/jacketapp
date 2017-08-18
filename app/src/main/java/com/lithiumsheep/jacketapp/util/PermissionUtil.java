package com.lithiumsheep.jacketapp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionUtil {

    public static boolean hasLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestLocationPermission(Activity callbackActivity, int requestCode) {
        ActivityCompat.requestPermissions(callbackActivity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, requestCode);
    }
}
