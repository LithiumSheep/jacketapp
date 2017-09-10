package com.lithiumsheep.jacketapp.util;

// TODO: Rename?
public class JacketUtil {

    // Based on Groups defined at https://openweathermap.org/weather-conditions
    public enum WeatherGroup {
        THUNDERSTORM,
        DRIZZLE,
        RAIN,
        SNOW,
        ATMOSPHERE,
        CLEAR,
        CLOUDY,
        EXTREME,
        OTHER
    }


    public boolean shouldDonJacket() {
        return true;
    }


}
