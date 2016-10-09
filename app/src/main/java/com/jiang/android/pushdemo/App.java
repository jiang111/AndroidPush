package com.jiang.android.pushdemo;

import android.app.Application;

import com.jiang.android.push.Const;

/**
 * Created by jiang on 2016/10/8.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Const.setMiui_app_id("123456");
        Const.setFlyme_app_id("123456");
        Const.setMiui_app_key("123456");
        Const.setFlyme_app_key("123456");
    }
}
