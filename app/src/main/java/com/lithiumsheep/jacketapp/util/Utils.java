package com.lithiumsheep.jacketapp.util;

import java.util.regex.Pattern;

public class Utils {

    private static final String zipRegEx = "^[0-9]{5}(?:-[0-9]{4})?$";
    private static final Pattern zipPattern = Pattern.compile(zipRegEx);

    public static boolean emptyOrNull(String... args) {
        for (String arg : args) {
            if (arg == null || arg.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean matchesZip(String value) {
        return zipPattern.matcher(value).matches();
    }

}
