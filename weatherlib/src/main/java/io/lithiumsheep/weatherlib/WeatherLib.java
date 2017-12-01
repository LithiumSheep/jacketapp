package io.lithiumsheep.weatherlib;

import io.lithiumsheep.weatherlib.api.HttpClient;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherLib {

    public static Observable<CurrentWeather> getCurrentWeather(String cityOrZip) {
        return HttpClient.getService().getCurrentWeather(cityOrZip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
