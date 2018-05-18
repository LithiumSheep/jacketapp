package com.lithiumsheep.jacketapp.api;

import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    //String URL = "https://api.openweathermap.org";
    String URL = "https://us-central1-jacketapp-1513807035703.cloudfunctions.net/";

    @GET("weather")
    Call<CurrentWeather> getWeather(@Query("lat") Double lat, @Query("lon") Double lon);

    @GET("weather")
    Call<CurrentWeather> getWeather(@Query("city") String city);

    @GET("weather")
    Call<CurrentWeather> getWeatherByZip(@Query("zipcode") String zip);
}
