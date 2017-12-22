package com.lithiumsheep.jacketapp.util;

public class Converter {

    private static final float RATIO = (9f / 5f);
    private static final String DEGREE_SYMBOL = "\u00B0";

    private static float kelvinToCelcius(float kelvin) {
        return kelvin - 273.15f;
    }

    public static float toCelcius(float input) {
        return kelvinToCelcius(input);
    }

    public static float toFahrenheit(float input) {
        return kelvinToCelcius(input) * RATIO + 32;
    }

    /**
     * Convert kevlin value to String with degree symbol
     * @param kelvin float representing temperature in Kelvin
     * @return  rounded String value of temperature
     */
    public static String tempForDisplay(float kelvin) {
        return String.valueOf(
               Math.round(toFahrenheit(kelvin)))
                .concat(DEGREE_SYMBOL).concat("F");
    }
}
