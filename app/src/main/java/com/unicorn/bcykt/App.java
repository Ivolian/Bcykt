package com.unicorn.bcykt;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by thinkpad on 2017/11/7.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
