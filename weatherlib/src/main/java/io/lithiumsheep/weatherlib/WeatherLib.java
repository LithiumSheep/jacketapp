package io.lithiumsheep.weatherlib;

import io.lithiumsheep.weatherlib.api.HttpClient;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class WeatherLib {

    public static Observable<CurrentWeather> getObservableCurrentWeather(String cityOrZip) {
        return HttpClient.getObservableService().getCurrentWeather(cityOrZip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Call<CurrentWeather> getCurrentWeather(String cityOrZip) {
        return HttpClient.getService().getCurrentWeather(cityOrZip);
    }

    public static Call<Void> getWeatherZip(String zip) {
        return HttpClient.getService().getWeatherByZip(zip);
    }
}
