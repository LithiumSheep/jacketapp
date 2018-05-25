package com.lithiumsheep.jacketapp.api;

import com.lithiumsheep.jacketapp.BuildConfig;
import com.lithiumsheep.jacketapp.JacketApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HttpClient {

    private static HttpLoggingInterceptor basicLogger() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);
        return logger;
    }

    private static OkHttpClient client;
    private static synchronized OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .cache(new Cache(JacketApplication.getDefaultCacheDir(), JacketApplication.getDefaultCacheSize()))
                    .addInterceptor(new CredentialInterceptor())
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
