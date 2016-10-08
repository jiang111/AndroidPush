package com.jiang.android.pushdemo;

import android.app.Application;

import com.jiang.android.push.Push;

/**
 * Created by jiang on 2016/10/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Push.register(this, true);
    }
}
