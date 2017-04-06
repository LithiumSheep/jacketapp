package com.lithiumsheep.jacketapp.api;


import android.util.Log;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.lithiumsheep.jacketapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;


public class WeatherHttpClient {

    private static LoggingInterceptor prettyLoggger() {
        return new LoggingInterceptor.Builder()
                .loggable(BuildConfig.DEBUG)
                .setLevel(Level.BASIC)
                .log(Log.INFO)
                .request("Request")
                .response("Response")
                .addHeader("version", BuildConfig.VERSION_NAME)
                .build();
    }

    private static HttpLoggingInterceptor JacketWharton() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        //logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logger;
    }

    private static OkHttpClient client;
    private static synchronized OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(prettyLoggger())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    public static void get(Request request, Callback callback) {
        getClient().newCall(request).enqueue(callback);
    }


    private String baseUrl() {
        return "http://api.openweathermap.org/data/2.5/weather";
    }
}
