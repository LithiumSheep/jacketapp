package io.lithiumsheep.reactiveapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.lithiumsheep.weatherlib.WeatherLib;
import io.lithiumsheep.weatherlib.api.NetworkCallback;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*WeatherLib.getCurrentWeather("Seattle")
                .enqueue(new NetworkCallback<CurrentWeather>() {
                    @Override
                    protected void onSuccess(CurrentWeather response) {
                        //Timber.d("Success");
                    }

                    @Override
                    protected void onError(Error error) {
                        Timber.w(error.getMessage());
                    }
                });*/

        /*WeatherLib.getWeatherZip("22202")
                .enqueue(new NetworkCallback<Void>() {
                    @Override
                    protected void onSuccess(Void response) {
                        Timber.d("What is response: %s", response);
                    }

                    @Override
                    protected void onError(Error error) {
                        Timber.w(error.getMessage());
                    }
                });*/

        WeatherLib.getObservableCurrentWeather("98006")
                .subscribe(new Observer<CurrentWeather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CurrentWeather currentWeather) {
                        Timber.d(currentWeather.getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Complete");
                    }
                });
    }
}
