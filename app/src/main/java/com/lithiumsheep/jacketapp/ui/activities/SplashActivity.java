package com.lithiumsheep.jacketapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // theme set through SplashTheme in AndroidManifest

        // just forward to MainActivity
        startActivity(new Intent(this, LaunchActivity.class));
    }
}
