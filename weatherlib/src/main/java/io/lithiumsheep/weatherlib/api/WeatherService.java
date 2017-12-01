package io.lithiumsheep.weatherlib.api;

import io.lithiumsheep.weatherlib.models.CurrentWeather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("data/2.5/weather")
    Call<CurrentWeather> getCurrentWeather(@Query("q") String cityOrZip);

    @GET("data/2.5/weather")
    Call<CurrentWeather> getCurrentWeather(@Query("lat") Double lat, @Query("lon") Double lon);
}
