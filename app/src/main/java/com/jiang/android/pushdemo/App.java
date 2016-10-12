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
        Const.setMiUI_APP("123345", "9efbdsfsdfdsfa717495bbae156ee5b66978b");
        Const.setFlyme_APP("110029", "9efb4973a717495bbae156ee5b66978b");
    }
}
