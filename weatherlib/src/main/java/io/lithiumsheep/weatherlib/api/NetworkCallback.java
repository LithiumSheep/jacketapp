package io.lithiumsheep.weatherlib.api;

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

    protected abstract void onError(Error error);

    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if (!response.isSuccessful()) {
            handleHttpError(response);
        } else {
            onSuccess(response.body());
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if (t instanceof UnknownHostException) {    // since we control the Service, this usually means no network
            onError(new Error(new Throwable("No network available, please check your WiFi or Data connection")));
        } else {    // it's something else, just let it go through with t.message
            onError(new Error(t));
        }
    }

    private void handleHttpError(Response response) {
        try {
            onError(new Moshi.Builder().build().adapter(Error.class).fromJson(response.errorBody().string()));
        } catch (IOException e) {
            onError(new Error(e));  // shouldn't hit unless standard error body from server is inconsistent
        }
    }

    public static class Error {
        transient Throwable throwable;

        private String message;

        Error(Throwable throwable) {
            this(throwable, null);
        }

        Error(Throwable throwable, @Nullable Response response) {
            this.throwable = throwable;
            this.message = parseErrorMessage(throwable, response);
        }

        public String getMessage() {
            return this.message;
        }

        private String parseErrorMessage(Throwable throwable, @Nullable Response response) {
            if (response != null && response.errorBody() != null) {
                try {
                    return response.errorBody().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return throwable.getMessage();
        }
    }
}
