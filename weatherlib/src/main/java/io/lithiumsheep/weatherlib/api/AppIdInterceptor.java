package io.lithiumsheep.weatherlib.api;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AppIdInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        HttpUrl url = chain.request().url()
                .newBuilder()
                .addQueryParameter("appId", "2f3e06e1d1a7d3ac7eb733be32aef344")
                .build();

        Request request = chain.request().newBuilder().url(url).build();

        return chain.proceed(request);
    }
}
