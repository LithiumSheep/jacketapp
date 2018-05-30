package com.lithiumsheep.jacketapp.ui;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.models.weather.CurrentWeather;
import com.lithiumsheep.jacketapp.util.Converter;
import com.lithiumsheep.jacketapp.util.TimeUtil;
import com.squareup.picasso.Picasso;

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
    @BindView(R.id.weather_icon)
    ImageView icon;
    @BindView(R.id.jacket_text)
    TextView jacketText;

    public WeatherViewHolder(Activity target) {
        ButterKnife.bind(this, target);
    }

    public void bind(CurrentWeather weather) {
        time.setText(TimeUtil.getTimeForNow());
        location.setText(weather.getName());
        mainTemp.setText(Converter.tempForDisplay(weather.getMetrics().getTemp()));
        minTemp.setText(String.valueOf(weather.getMetrics().getTempMin()));
        maxTemp.setText(String.valueOf(weather.getMetrics().getTempMax()));
        name.setText(weather.getWeather().get(0).getMain());

        if (weather.getMetrics().getTemp() < 294.261) { // magic number for 70 F
            // TODO: later pull this from analysis or Converter + preferences
            Picasso.get()
                    .load(R.drawable.jacket_red)
                    .into(icon);

            jacketText.setText("Put on a jacket.  It may be chilly out.");
        } else {
            Picasso.get()
                    .load(R.drawable.weather_sun)
                    .into(icon);
            jacketText.setText("You probably won't need a jacket today");
        }
    }

    public void clear() {
        time.setText(null);
        location.setText(null);
        mainTemp.setText(null);
        minTemp.setText(null);
        maxTemp.setText(null);
        name.setText(null);
        icon.setImageBitmap(null);
        jacketText.setText(null);
    }
}
