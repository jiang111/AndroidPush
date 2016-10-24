package com.jiang.android.pushdemo;

import android.app.Application;

import com.jiang.android.push.Const;
import com.jiang.android.push.application.MyApplication;

/**
 * Created by jiang on 2016/10/8.
 */

public class App extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Const.setMiUI_APP("2882303761517516835", "5911751629835");
        Const.setFlyme_APP("110029", "9efb4973a717495bbae156ee5b66978b");

    }
}
