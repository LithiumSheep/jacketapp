package io.lithiumsheep.weatherlib.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class HttpClient {

    private static String baseUrl() {
        return "https://api.openweathermap.org";
    }

    private static WeatherService service;
    private static WeatherService getService() {
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
