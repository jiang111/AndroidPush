package com.jiang.android.push.utils;

import android.util.Log;

import com.jiang.android.push.BuildConfig;

/**
 * Created by jiang on 2016/10/11.
 */

public class L {
    private static final String TAG = "AndroidPush";

    public static final boolean debug = BuildConfig.DEBUG;

    public static void i(String msg) {
        //if (debug) {
        Log.i(TAG, msg);
        //}
    }
}
