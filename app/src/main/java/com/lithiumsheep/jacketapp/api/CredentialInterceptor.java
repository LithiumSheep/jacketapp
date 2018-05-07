package com.lithiumsheep.jacketapp.api;

import android.support.annotation.NonNull;

import com.lithiumsheep.jacketapp.util.UserAgent;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CredentialInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl url = request.url();

        if (request.url().host().contains("https://api.openweathermap.org")) {
            // only apply credentials if using api.openweathermap directly
            url = chain.request().url()
                    .newBuilder()
                    .addQueryParameter("appId", "2f3e06e1d1a7d3ac7eb733be32aef344")
                    .addQueryParameter("units", "imperial")
                    .build();
        }

        request = request.newBuilder().url(url)
                .addHeader("User-Agent", UserAgent.getDefault())
                .build();

        return chain.proceed(request);
    }
}
