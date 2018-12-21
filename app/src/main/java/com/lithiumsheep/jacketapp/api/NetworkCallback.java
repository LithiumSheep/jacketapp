package com.lithiumsheep.jacketapp.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NetworkCallback<T> implements Callback<T> {

    protected abstract void onSuccess(T response);

    protected abstract void onError(Throwable throwable);

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (response.isSuccessful()) {
            this.onSuccess(response.body());
        } else {
            try {
                this.onError(new Throwable(response.errorBody().string()));
            } catch (IOException e) {
                this.onError(e);
            }
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (t instanceof UnknownHostException) {    // since we control the Service, this exception usually means no network
            onError(new Throwable("No network available, please check your WiFi or Data connection."));
        } else {
            onError(t);
        }
    }
}
