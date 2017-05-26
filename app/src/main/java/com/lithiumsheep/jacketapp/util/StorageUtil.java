package com.lithiumsheep.jacketapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import timber.log.Timber;

public class StorageUtil {

    private static final String LOCATIONS_FILE = "LOCATIONS_FILE";
    private static final String LOCATION_SET = "LOCATION_SET";
    private static final String LOCATION_LAST = "LOCATION_LAST";

    /*public static void storeLastLocation(Context context, Location lastLoc) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        Set<String> locationsSet = getLastLocationSet(context);
        if (locationsSet == null) {
            locationsSet = new HashSet<>();
        }
        String lls = "Lat " + lastLoc.getLatitude() + " ; Lon " + lastLoc.getLongitude();
        locationsSet.add(lls);

        editor.putStringSet(LOCATION_SET, locationsSet).apply();
    }*/

    /*public static Set<String> getLastLocationSet(Context context) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);
        return pref.getStringSet(LOCATION_SET, null);
    }*/

    public static void storeLastLocation(Context context, Location lastLoc) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String lls = "Lat " + lastLoc.getLatitude() + " ; Lon " + lastLoc.getLongitude();
        Timber.d("Storing location %s", lls);
        editor.putString(LOCATION_LAST, lls).apply();
    }

    public static String getLastLocation(Context context) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);
        return pref.getString(LOCATION_LAST, "");
    }

    public static void clearLocations(Context context) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.clear().apply();
    }
}
