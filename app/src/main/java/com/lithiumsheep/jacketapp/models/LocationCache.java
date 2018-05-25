package com.lithiumsheep.jacketapp.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

public class LocationCache {

    private static final String LOCATION_CACHE = "location_cache";

    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String NAME = "name";

    private final SharedPreferences prefs;

    private Location lastLocation = null;

    public LocationCache(Context context) {
        prefs = context.getSharedPreferences(LOCATION_CACHE, Context.MODE_PRIVATE);
    }

    public Location load() {
        if (lastLocation == null) {
            String lat = prefs.getString(LAT, null);
            String lon = prefs.getString(LON, null);
            if (lat != null && lon != null) {
                lastLocation = new Location(LOCATION_CACHE);
                lastLocation.setLatitude(Double.valueOf(lat));
                lastLocation.setLongitude(Double.valueOf(lon));
            }
            // could be null still
        }
        return lastLocation;
    }

    public void save(Location location) {
        lastLocation = location;
        prefs.edit()
                .putString(LAT, String.valueOf(location.getLatitude()))
                .putString(LON, String.valueOf(location.getLongitude()))
                .apply();
    }

    public void clear() {
        lastLocation = null;
        prefs.edit()
                .clear()
                .apply();
    }
}
