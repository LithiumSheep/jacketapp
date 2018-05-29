package com.lithiumsheep.jacketapp.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.Nullable;

import com.lithiumsheep.jacketapp.models.weather.Coord;

public class LastLocationCache {

    private static final String LOCATION_CACHE = "location_cache";

    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String NAME = "name";

    private final SharedPreferences prefs;

    private LastLocation lastLocation = null;

    public LastLocationCache(Context context) {
        prefs = context.getSharedPreferences(LOCATION_CACHE, Context.MODE_PRIVATE);
    }

    public LastLocation load() {
        if (lastLocation == null) {
            String lat = prefs.getString(LAT, null);
            String lon = prefs.getString(LON, null);
            String name = prefs.getString(NAME, null);
            if (lat != null && lon != null) {
                Location location = new Location(LOCATION_CACHE);
                location.setLatitude(Double.valueOf(lat));
                location.setLongitude(Double.valueOf(lon));
                lastLocation = new LastLocation(name, location);
            }
            // could be null still
        }
        return lastLocation;
    }

    public void save(String name, Coord coordinates) {
        Location loc = new Location("JacketApp_Backend");
        loc.setLatitude(coordinates.getLat());
        loc.setLongitude(coordinates.getLon());
        save(new LastLocation(name, loc));
    }

    public void save(LastLocation lastLocation) {
        this.lastLocation = lastLocation;
        prefs.edit()
                .putString(LAT, String.valueOf(lastLocation.getLocation().getLatitude()))
                .putString(LON, String.valueOf(lastLocation.getLocation().getLongitude()))
                .putString(NAME, lastLocation.getName())
                .apply();
    }

    public void clear() {
        lastLocation = null;
        prefs.edit()
                .clear()
                .apply();
    }
}
