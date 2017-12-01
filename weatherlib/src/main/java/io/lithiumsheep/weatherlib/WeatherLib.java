package io.lithiumsheep.weatherlib;

import io.lithiumsheep.weatherlib.api.HttpClient;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherLib {

    public static void getCurrentWeather(String cityOrZip) {
        HttpClient.getService().getCurrentWeather(cityOrZip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    interface WeatherCallback {
        void onSuccess(CurrentWeather weather);
        void onError();
    }
}
