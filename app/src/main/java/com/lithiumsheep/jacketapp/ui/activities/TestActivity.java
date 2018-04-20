package com.lithiumsheep.jacketapp.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lithiumsheep.jacketapp.R;
import com.lithiumsheep.jacketapp.api.HttpClient;
import com.lithiumsheep.jacketapp.api.NetworkCallback;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpClient.get().weatherByCity("Arlington")
                .enqueue(new NetworkCallback<Void>() {
                    @Override
                    protected void onSuccess(Void response) {

                    }

                    @Override
                    protected void onError(Error error) {

                    }
                });
    }
}
