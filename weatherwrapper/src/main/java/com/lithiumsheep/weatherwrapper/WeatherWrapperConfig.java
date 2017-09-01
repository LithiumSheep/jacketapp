package com.lithiumsheep.weatherwrapper;

import android.util.Log;

import com.lithiumsheep.weatherwrapper.models.Temperature;

public class WeatherWrapperConfig {

    private String appID;
    private boolean basicLogging;
    private boolean prettyLogging;
    private Temperature.Unit defaultUnit = Temperature.Unit.KELVIN;

    public String getAppID() {
        return appID;
    }

    public boolean basicLoggingEnabled() {
        return basicLogging;
    }

    public boolean prettyLoggingEnabled() {
        return prettyLogging;
    }

    public Temperature.Unit getTemperatureUnit() {
        return defaultUnit;
    }

    public static class Builder {
        private WeatherWrapperConfig config;

        public Builder() {
            this.config = new WeatherWrapperConfig();

            WeatherWrapperConfig currentConfig = WeatherWrapper.getConfig();

            this.config.appID = currentConfig.appID;
            this.config.basicLogging = currentConfig.basicLogging;
            this.config.prettyLogging = currentConfig.prettyLogging;
            this.config.defaultUnit = currentConfig.defaultUnit;
        }

        public Builder setAppId(String appId) {
            config.appID = appId;
            return this;
        }

        @SuppressWarnings("SameParameterValue")
        public Builder setBasicLoggingEnabled(boolean basicLoggingEnabled) {
            config.basicLogging = basicLoggingEnabled;
            return this;
        }

        @SuppressWarnings("SameParameterValue")
        public Builder setPrettyLoggingEnabled(boolean prettyLoggingEnabled) {
            config.prettyLogging = prettyLoggingEnabled;
            return this;
        }

        public Builder withTemperatureUnit(Temperature.Unit targetUnit) {
            config.defaultUnit = targetUnit;
            return this;
        }

        public WeatherWrapperConfig build() {
            return this.config;
        }
    }

    public void apply() {
        if (this.appID.isEmpty()) {
            Log.e("WeatherWrapperConfig", "Your WeatherWrapper configuration is missing or has an empty APP_ID.  WeatherWrapper requires an APP_ID to access OpenWeatherMap API.");
        }
        WeatherWrapper.setConfig(this);
    }
}
