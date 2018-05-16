package com.lithiumsheep.jacketapp.models;


import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;

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

        analysis.setShouldDonJacket(Helper.shouldDonJacket(weather));


        return analysis;
    }

    public static class Helper {
        private static final String TEMPERATURE = "Temperature";
        private static final String WIND = "Wind";
        private static final String CLOUDS = "Clouds";
        private static final String WEATHER = "Weather";

        // Base values and weights
        private static float baseScore = 100f;

        private static float jacketThreshold = 60f;

        private static float tempWeight = 0.4f;
        private static float windWeight = 0.2f;
        private static float weatherWeight = 0.2f;
        private static float cloudsWeight = 0.2f;

        // thresholds (range, threshold, or filter)

        // temperature (Fahrenheit)
        private static float tempHigh = 60f;
        private static float tempLow = 32f;

        // wind (m/s)
        private static float windThreshhold = 3.576f;

        // weather (filter total score)
        private static int code = 800; // clear weatherGroup

        // clouds (1-100 range) (inverse)
        private static int cloudsThreshold = 70;

        private static float calcTemp(float inputTemp) {
            float weightedFullScore = baseScore * tempWeight;
            if (inputTemp >= tempHigh) {
                return weightedFullScore;
            } else if (inputTemp <= tempLow) {
                return 0f;
            } else {
                return (inputTemp / tempHigh) * weightedFullScore;
            }
        }

        private static float calcWind(float inputWind) {
            float weightedFullScore = baseScore * windWeight;
            if (inputWind >= windThreshhold) {
                return 0f;
            } else if (inputWind <= 0) {
                return weightedFullScore;
            } else {
                return (inputWind / windThreshhold) * weightedFullScore;
            }
        }

        private static float calcWeather(int code) {    // TODO: Add support for list of codes
            float weightedFullScore = baseScore * weatherWeight;
            if (code == 800) {
                return weightedFullScore;
            } else {
                return 0;
            }
        }

        private static float calcClouds(int visibility) {
            // clouds range from 0 - 100
            float weightedFullScore = baseScore * cloudsWeight;
            return (visibility / 100f) * weightedFullScore;
        }

        public static float calcCompositeScore(CurrentWeather weather) {
            return calcTemp(weather.getMetrics().getTemp()) +
                    calcWind(weather.getWind().getSpeed()) +
                    calcWeather(weather.getWeather().get(0).getId()) +
                    calcClouds(weather.getClouds().getAll());
        }

        public static void scoreBreakdown(CurrentWeather weather) {

        }

        public static boolean shouldDonJacket(CurrentWeather weather) {
            return calcCompositeScore(weather) < jacketThreshold;
        }
    }
}
