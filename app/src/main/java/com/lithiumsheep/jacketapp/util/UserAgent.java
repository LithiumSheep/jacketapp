package com.lithiumsheep.jacketapp.util;

import android.os.Build;

import com.lithiumsheep.jacketapp.BuildConfig;

import java.util.Locale;

public class UserAgent {

    public static String getDefault() {
        return "JacketApp/" + BuildConfig.VERSION_NAME +
                " (" +
                "Android " + Build.VERSION.RELEASE + "; " +
                Locale.getDefault() + "; " +
                Build.MODEL + "; Build/" + Build.ID +
                ")";
    }
}
