package com.lithiumsheep.jacketapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

public class StorageUtil {

    private static final String LOCATIONS_FILE = "LOCATIONS_FILE";
    //private static final String LOCATION_SET = "LOCATION_SET";
    private static final String LOCATION_LAST = "LOCATION_LAST";
    private static final String LOCATION_LAST_LAT = "LOCATION_LAST_LAT";
    private static final String LOCATION_LAST_LON = "LOCATION_LAST_LON";
    private static final String ZIP_LAST = "ZIP_LAST";

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

        editor.putString(LOCATION_LAST_LAT, String.valueOf(lastLoc.getLatitude())).apply();
        editor.putString(LOCATION_LAST_LON, String.valueOf(lastLoc.getLongitude())).apply();
    }

    public static Location getLastLocation(Context context) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);

        if (pref.getString(LOCATION_LAST_LAT, "").isEmpty() || pref.getString(LOCATION_LAST_LON, "").isEmpty()) {
            return null;
        }

        Location lastLoc = new Location("JacketApp");
        lastLoc.setLatitude(Double.parseDouble(pref.getString(LOCATION_LAST_LAT, null)));
        lastLoc.setLongitude(Double.parseDouble(pref.getString(LOCATION_LAST_LON, null)));

        return lastLoc;
    }

    public static void storeLastZip(Context context, String zipCode) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(ZIP_LAST, zipCode).apply();
    }

    public static String getLastZip(Context context) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);
        return pref.getString(ZIP_LAST, "");
    }

    public static void clearAll(Context context) {
        SharedPreferences pref = context.getSharedPreferences(LOCATIONS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.clear().apply();
    }
}
