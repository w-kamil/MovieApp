package com.github.w_kamil.movieapp;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Frod_ on 11.03.2017.
 */

public class MovieApplication extends Application implements RetrfofitProvider{
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl("http://www.omdbapi.com").build();
    }

    @Override
    public Retrofit provideRetrofit() {
        return retrofit;
    }
}
