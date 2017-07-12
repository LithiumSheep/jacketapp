package com.lithiumsheep.weatherwrapperwhuut.weather;

import android.util.Log;

import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class WeatherHttpClient {

    private static boolean loggable = false;
    public static void enableLogging(boolean enableLogging) {
        loggable = enableLogging;
    }

    private static LoggingInterceptor prettyLoggger() {
        return new LoggingInterceptor.Builder()
                .loggable(loggable)
                .setLevel(Level.BASIC)
                .log(Log.INFO)
                .request("Request")
                .response("Response")
                //.addHeader("version", BuildConfig.VERSION_NAME)
                .build();
    }

    private static HttpLoggingInterceptor JacketWharton() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC);
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

    static void get(Request request, Callback callback) {
        getClient().newCall(request).enqueue(callback);
    }
}
