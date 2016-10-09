package com.jiang.android.pushdemo;

import android.app.Application;

import com.jiang.android.push.Push;

/**
 * Created by jiang on 2016/10/8.
 */

public class App extends Application {

    PushService mPushService = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mPushService = new PushService();
        Push.register(this, true, mPushService);
    }
}
