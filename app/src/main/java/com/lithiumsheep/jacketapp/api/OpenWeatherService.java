package com.lithiumsheep.jacketapp.api;

import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    String URL = "https://api.openweathermap.org";

    @GET("data/2.5/weather")
    Call<CurrentWeather> weatherByCity(@Query("q") String city);

    @GET("data/2.5/weather")
    Call<CurrentWeather> weatherByZip(@Query("zip") String zip);

    @GET("data/2.5/weather")
    Call<CurrentWeather> weatherByLatLon(@Query("lat") Double lat, @Query("lon") Double lon);
}
