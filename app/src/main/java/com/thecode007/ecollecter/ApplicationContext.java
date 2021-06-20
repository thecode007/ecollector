package com.thecode007.ecollecter;

import android.app.Application;

import androidx.lifecycle.LifecycleObserver;

import com.androidnetworking.AndroidNetworking;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class ApplicationContext extends Application  implements LifecycleObserver {

    static ApplicationContext applicationContext;


    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(1200, TimeUnit.SECONDS)
                .build();

        AndroidNetworking.initialize(getApplicationContext(),okHttpClient);
    }

    public static ApplicationContext getInstance() {
        return applicationContext;
    }


}
