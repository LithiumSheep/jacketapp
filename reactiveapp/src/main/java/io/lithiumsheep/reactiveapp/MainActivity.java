package io.lithiumsheep.reactiveapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.lithiumsheep.reactiveapp.viewmodel.WeatherViewModel;
import io.lithiumsheep.weatherlib.models.CurrentWeather;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherViewModel vm = ViewModelProviders.of(this).get(WeatherViewModel.class);
        vm.getWeather("98006")
                .observe(this, new android.arch.lifecycle.Observer<CurrentWeather>() {
                    @Override
                    public void onChanged(@Nullable CurrentWeather currentWeather) {
                        Timber.d(currentWeather.getName());
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
