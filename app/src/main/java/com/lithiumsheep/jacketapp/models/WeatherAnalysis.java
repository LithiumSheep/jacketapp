package com.lithiumsheep.jacketapp.models;

import io.lithiumsheep.weatherlib.models.CurrentWeather;

public class WeatherAnalysis {

    private boolean shouldDonJacket;
    private String jacketText;
    private CurrentWeather currentWeather;

    private WeatherAnalysis() {
        new WeatherAnalysis(null);
    }

    private WeatherAnalysis(CurrentWeather weather) {
        currentWeather = weather;
    }

    private void setShouldDonJacket(boolean should) {
        shouldDonJacket = should;
    }

    private void setJacketText(String text) {
        jacketText = text;
    }

    public static WeatherAnalysis digest(CurrentWeather weather) {
        // check weather group?
        // check temp
        // calculate

        WeatherAnalysis analysis = new WeatherAnalysis(weather);

        if (weather.getTemperature().getTemp() < 40) {
            analysis.setShouldDonJacket(true);
            analysis.setJacketText("");
        }



        return analysis;
    }
}
