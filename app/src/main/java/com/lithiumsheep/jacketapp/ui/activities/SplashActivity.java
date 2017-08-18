package com.lithiumsheep.jacketapp.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lithiumsheep.jacketapp.R;

import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

    }
}
