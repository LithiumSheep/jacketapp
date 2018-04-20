package com.lithiumsheep.jacketapp.ui;

import android.app.Activity;
import android.widget.TextView;

import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;
import com.lithiumsheep.jacketapp.util.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherViewHolder {

    @BindView(R.id.weather_time)
    TextView time;
    @BindView(R.id.weather_location)
    TextView location;
    @BindView(R.id.temperature_main)
    TextView mainTemp;
    @BindView(R.id.temperature_low)
    TextView minTemp;
    @BindView(R.id.temperature_high)
    TextView maxTemp;
    @BindView(R.id.weather_text)
    TextView name;

    public WeatherViewHolder(Activity target) {
        ButterKnife.bind(this, target);
    }

    public void bind(CurrentWeather weather) {
        time.setText(TimeUtil.getTimeForNow());
        location.setText(weather.getName());
        mainTemp.setText(String.valueOf(weather.getMetrics().getTemp()));
        minTemp.setText(String.valueOf(weather.getMetrics().getTempMin()));
        maxTemp.setText(String.valueOf(weather.getMetrics().getTempMax()));
        name.setText(weather.getWeather().get(0).getMain());
    }
}
