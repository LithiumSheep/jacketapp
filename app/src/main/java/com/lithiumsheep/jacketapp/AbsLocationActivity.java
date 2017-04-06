package com.lithiumsheep.jacketapp;


import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;

public abstract class AbsLocationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;


}
