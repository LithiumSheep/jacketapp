package com.lithiumsheep.jacketapp.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lithiumsheep.jacketapp.R;

public class Defaults {

    //private Temperature.Unit defaultUnit;
    private boolean nsfwMode;

    public Defaults(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // default temp unit

        /*defaultUnit = Temperature.Unit.valueOf(
                Integer.parseInt(prefs.getString(context.getString(R.string.pref_temp_key), "0"))
        );*/
        nsfwMode = prefs.getBoolean(context.getString(R.string.pref_nsfw_key), false);
    }

    /*public Temperature.Unit getDefaultUnit() {
        return this.defaultUnit;
    }*/

    public boolean isNsfwMode() {
        return this.nsfwMode;
    }
}
