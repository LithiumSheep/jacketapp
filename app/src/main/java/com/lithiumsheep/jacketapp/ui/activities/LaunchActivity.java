package com.lithiumsheep.jacketapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.lithiumsheep.jacketapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LaunchActivity extends AppCompatActivity {

    static final String BACKDROP_URL_RAIN = "https://images.pexels.com/photos/243971/pexels-photo-243971.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260";
    static final String BACKDROP_URL_DARKER_RAIN = "https://images.pexels.com/photos/110874/pexels-photo-110874.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260";
    static final String BACKDROP_URL_BACKUP = "https://images.pexels.com/photos/797853/pexels-photo-797853.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260";

    @BindView(R.id.backdrop)
    ImageView backdrop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);

        Picasso.get()
                .load(BACKDROP_URL_DARKER_RAIN)
                .into(backdrop);
    }

    @OnClick(R.id.location_button)
    void onUseCurrentLocationClicked() {

    }

    @OnClick(R.id.search_button)
    void onSearchManuallyClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
