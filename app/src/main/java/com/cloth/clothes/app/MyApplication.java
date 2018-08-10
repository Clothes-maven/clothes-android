package com.cloth.clothes.app;

import android.app.Application;

import com.cloth.kernel.service.LcAppWrapper;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        LcAppWrapper.createAppWrapper(this,true);
    }
}
