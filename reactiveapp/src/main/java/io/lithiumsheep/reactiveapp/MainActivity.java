package io.lithiumsheep.reactiveapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.lithiumsheep.weatherlib.WeatherLib;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherLib.getCurrentWeather("22202");
    }
}
