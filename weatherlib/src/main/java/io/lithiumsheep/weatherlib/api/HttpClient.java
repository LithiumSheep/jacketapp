package io.lithiumsheep.weatherlib.api;

import android.support.annotation.RestrictTo;

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
                    .build()
                    .create(WeatherService.class);
        }
        return service;
    }
}
