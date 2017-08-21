package com.lithiumsheep.weatherwrapper.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.annotation.NonNull;

/**
 * Created by JesseXLi on 7/7/17.
 */

/**
 * Cache freshness is based on Time
 *
 * Cache policy aggressive invalidates after 15 minutes
 * Cache policy relaxed invalidates after 24 hours
 * Cache policy devil-may-car invalidates after 7 days
 */

public class LocationCache {

    public enum CachePolicy {
        AGGRESSIVE,
        RELAXED,
        DEVIL_MAY_CARE
    }

    private CachePolicy policy;

    //private final String WEATHER_WRAPPER_CACHE_FILE = "weather_wrapper_cache_file";
    private static final String LOCATION_LAST_LAT = "location_last_lat";
    private static final String LOCATION_LAST_LON = "location_last_lon";
    private static final String LOCATION_CACHE_TIMESTAMP = "location_last_timestamp";

    private static SharedPreferences sharedPrefs;

    public LocationCache(@NonNull Context context, CachePolicy cachePolicy) {
        String WEATHER_WRAPPER_CACHE_FILE = "weather_wrapper_cache_file";
        sharedPrefs = context.getSharedPreferences(WEATHER_WRAPPER_CACHE_FILE, Context.MODE_PRIVATE);
        policy = cachePolicy;
    }

    private boolean hasCachedLocation() {
        return sharedPrefs.contains(LOCATION_LAST_LAT) && sharedPrefs.contains(LOCATION_LAST_LON);
    }

    public boolean cachedLocationIsFresh() {
        switch(policy) {
            case AGGRESSIVE:
                return false;
            case RELAXED:
                return true;
            case DEVIL_MAY_CARE:
                return true;
            default:
                return false;
        }
    }

    public void storeLastLocation(@NonNull Location loc) {
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString(LOCATION_LAST_LAT, String.valueOf(loc.getLatitude())).apply();
        editor.putString(LOCATION_LAST_LON, String.valueOf(loc.getLongitude())).apply();
        editor.putLong(LOCATION_CACHE_TIMESTAMP, System.currentTimeMillis());
    }

    public Location getLastLocation() {
        if (hasCachedLocation()) {

            Location lastLoc = new Location("JacketApp");
            lastLoc.setLatitude(Double.parseDouble(sharedPrefs.getString(LOCATION_LAST_LAT, null)));
            lastLoc.setLongitude(Double.parseDouble(sharedPrefs.getString(LOCATION_LAST_LON, null)));

            return lastLoc;
        } else {
            return null;
        }
    }

    private long getLastLocationCacheTimestamp() {
        return sharedPrefs.getLong(LOCATION_CACHE_TIMESTAMP, 0);
    }

    private long millisSinceCacheTimestamp(long cacheTimeStamp) {
        return System.currentTimeMillis() - cacheTimeStamp;
    }

    private boolean passesAggressivePolicy(long cacheTimeStamp) {
        return millisSinceCacheTimestamp(cacheTimeStamp) < (15 * 60 * 1000);
    }

    private boolean passesRelaxedPolicy(long cacheTimeStamp) {
        return millisSinceCacheTimestamp(cacheTimeStamp) < (24 * 60 * 60 * 1000);
    }

    private boolean passesDevilPolicy(long cacheTimeStamp) {
        return millisSinceCacheTimestamp(cacheTimeStamp) < (7 * 24 * 60 * 60 * 1000);
    }
}
