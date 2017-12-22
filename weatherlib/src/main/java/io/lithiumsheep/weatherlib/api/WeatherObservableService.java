package io.lithiumsheep.weatherlib.api;

import io.lithiumsheep.weatherlib.models.CurrentWeather;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherObservableService {

    @GET("data/2.5/weather")
    Observable<CurrentWeather> getCurrentWeather(@Query("zip") String cityOrZip);

    @GET("data/2.5/weather")
    Observable<CurrentWeather> getCurrentWeather(@Query("lat") Double lat, @Query("lon") Double lon);
}
