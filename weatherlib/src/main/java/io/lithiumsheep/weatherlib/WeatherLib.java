package io.lithiumsheep.weatherlib;

import io.lithiumsheep.weatherlib.api.HttpClient;
import io.lithiumsheep.weatherlib.api.NetworkCallback;
import io.lithiumsheep.weatherlib.models.CurrentWeather;

public class WeatherLib {

    public static void getWeatherByZip(String zip, final Callback<CurrentWeather> callback) {
        HttpClient.getService().weatherByZip(zip).enqueue(new NetworkCallback<CurrentWeather>() {
            @Override
            protected void onSuccess(CurrentWeather response) {
                callback.onSuccess(response);
            }

            @Override
            protected void onError(Error error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    public static void getWeatherByQuery(String query, final Callback<CurrentWeather> callback) {
        HttpClient.getService().weatherByCity(query).enqueue(new NetworkCallback<CurrentWeather>() {
            @Override
            protected void onSuccess(CurrentWeather response) {
                callback.onSuccess(response);
            }

            @Override
            protected void onError(Error error) {
                callback.onFailure(error.getMessage());
            }
        });
    }

    public interface Callback<T> {
        void onSuccess(T response);
        void onFailure(String reason);
    }

    public class Config {
        private String appId;
        private boolean loggingEnabled;

        private Config() {

        }

        private Config(String appId, boolean loggingEnabled) {
            this.appId = appId;
            this.loggingEnabled = loggingEnabled;
        }

        public String getAppId() {
            return appId;
        }

        public boolean isLoggingEnabled() {
            return loggingEnabled;
        }

        public class Builder {
            private String appId;
            private boolean loggingEnabled;

            public Builder() {
                // pull from currentConfig?
                appId = "";
                loggingEnabled = false;
            }

            public Builder withAppId(String appId) {
                this.appId = appId;
                return this;
            }

            public Builder withLoggingEnabled(boolean loggingEnabled) {
                this.loggingEnabled = loggingEnabled;
                return this;
            }

            public Config build() {
                return new Config(appId, loggingEnabled);
            }
        }
    }
}
