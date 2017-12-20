package io.lithiumsheep.reactiveapp.util;

import java.util.regex.Pattern;

public class Validator {

    private static final String zipRegEx = "^[0-9]{5}(?:-[0-9]{4})?$";
    private static final Pattern zipPattern = Pattern.compile(zipRegEx);

    public static boolean matchesZip(String value) {
        return zipPattern.matcher(value).matches();
    }
}
