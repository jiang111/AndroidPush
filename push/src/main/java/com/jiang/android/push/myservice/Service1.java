package com.jiang.android.push.myservice;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This Service is Persistent Service. Do some what you want to do here.<br/>
 * <p/>
 * Created by Mars on 12/24/15.
 */
public class Service1 extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO do some thing what you want..


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
