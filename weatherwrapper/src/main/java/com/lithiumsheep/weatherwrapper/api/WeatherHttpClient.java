package com.lithiumsheep.weatherwrapper.api;

import android.util.Log;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.lithiumsheep.weatherwrapper.BuildConfig;
import com.lithiumsheep.weatherwrapper.WeatherWrapper;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

class WeatherHttpClient {

    private static LoggingInterceptor prettyLoggger() {
        return new LoggingInterceptor.Builder()
                .loggable(WeatherWrapper.getConfig().prettyLoggingEnabled())
                .setLevel(Level.BASIC)
                .log(Log.INFO)
                .request("WeatherWrapper")
                .response("WeatherWrapper")
                .addHeader(BuildConfig.APPLICATION_ID.concat("version"), BuildConfig.VERSION_NAME)
                .build();
    }

    private static HttpLoggingInterceptor JacketWharton() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(WeatherWrapper.getConfig().basicLoggingEnabled() ?
                HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        return logger;
    }

    private static OkHttpClient client;
    private static synchronized OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(prettyLoggger())
                    .addInterceptor(JacketWharton())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    static void get(Request request, Callback callback) {
        getClient().newCall(request).enqueue(callback);
    }
}
