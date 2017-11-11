package com.unicorn.bcykt.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        JodaTimeAndroid.init(this);
    }

}
