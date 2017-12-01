package io.lithiumsheep.weatherlib.api;

import android.support.annotation.RestrictTo;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HttpClient {

    private static WeatherService service;

    private static String baseUrl() {
        return "https://api.openweathermap.org";
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static WeatherService getService() {
        if (service == null) {
            service = new Retrofit.Builder()
                    .baseUrl(baseUrl())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new AppIdInterceptor())
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).build())
                    .build()
                    .create(WeatherService.class);
        }
        return service;
    }
}
