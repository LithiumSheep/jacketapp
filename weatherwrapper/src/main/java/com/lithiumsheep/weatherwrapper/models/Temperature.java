package com.lithiumsheep.weatherwrapper.models;

import com.lithiumsheep.weatherwrapper.WeatherWrapper;
import com.squareup.moshi.Json;

public class Temperature {

    public enum Unit {
        KELVIN (0, "Kelvin"),
        CELSIUS (1, "Celsius"),
        FAHRENHEIT (2, "Fahrenheit");

        private int value;
        private String simpleName;

        Unit(int value, String simpleName) {
            this.value = value;
            this.simpleName = simpleName;
        }

        public static Unit valueOf(int value) {
            for (Unit unit : Unit.values()) {
                if (unit.value == value) {
                    return unit;
                }
            }
            return null;
        }
    }

    private static Unit targetUnit = WeatherWrapper.getConfig().getTemperatureUnit();

    /**
     * By Default current (temp) is stored as Kelvin, and conversions take from KELVIN -> targetUnit
     */
    @Json(name = "temp")
    private float current;

    /*@Json(name = "temp_min")
    float minTemperature;

    @Json(name = "temp_max")
    float maxTemperature;*/

    public static void setDefaultUnit(Unit target) {
        targetUnit = target;
    }

    public float current() {
        return Util.convertTemp(this.current, targetUnit);
    }

    /*public float current(Unit targetUnit) {
        return Util.convertTemp(this.current, targetUnit);
    }*/

    public static class Util {
        private static final float RATIO = (9f / 5f);

        private static float kelvinToCelsius(float kel) {
            return kel - 273.15f;
        }

        static float toFahrenheit(float temp, boolean fromKelvin) {
            if (fromKelvin) {
                temp = kelvinToCelsius(temp);
            }
            return temp * RATIO + 32;
        }

        static float toCelsius(float temp, boolean fromKelvin) {
            return fromKelvin ? kelvinToCelsius(temp) : temp;
        }

        static float convertTemp(float val, Unit targetUnit) {
            if (targetUnit.equals(Unit.FAHRENHEIT)) {
                val = toFahrenheit(val, true);
            } else if (targetUnit.equals(Unit.CELSIUS)) {
                val = toCelsius(val, true);
            }
            return val;
        }
    }

}
