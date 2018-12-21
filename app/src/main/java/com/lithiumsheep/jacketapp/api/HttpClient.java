package com.lithiumsheep.jacketapp.api;

import com.lithiumsheep.jacketapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class HttpClient {

    private static HttpLoggingInterceptor basicLogger() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        return logger;
    }

    private static OkHttpClient client;
    private static synchronized OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .callTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(basicLogger())
                    .build();
        }
        return client;
    }

    private static OpenWeatherService service;
    public static OpenWeatherService get() {
        if (service == null) {
            service = new Retrofit.Builder()
                    .baseUrl(OpenWeatherService.URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(getClient())
                    .build()
                    .create(OpenWeatherService.class);
        }
        return service;
    }

}
