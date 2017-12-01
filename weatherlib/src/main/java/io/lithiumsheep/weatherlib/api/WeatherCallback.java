package io.lithiumsheep.weatherlib.api;

import com.squareup.moshi.Moshi;

import java.io.IOException;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public abstract class WeatherCallback<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody body = ((HttpException) e).response().errorBody();
            try {
                Error error = new Moshi.Builder().build().adapter(Error.class).fromJson(body.string());
                onError(error);
            } catch (IOException e1) {
                onError(new Error(e1.getMessage()));
            }
        } else {
            onError(new Error(e.getMessage()));
        }
    }

    @Override
    public void onComplete() {
        // nothing?
    }

    public abstract void onError(Error error);
    public abstract void onSuccess(T response);

    public static class Error {
        private String message;

        Error() {
            this.message = "A default error";
        }

        Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
