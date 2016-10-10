package com.jiang.android.push.utils;

import android.os.Looper;

/**
 * Created by jiang on 2016/10/10.
 */

public class JHandler {
    private static android.os.Handler mHandler = null;

    public static android.os.Handler handler() {
        if (mHandler == null) {
            synchronized (JHandler.class) {
                mHandler = new android.os.Handler(Looper.getMainLooper());
            }
        }
        return mHandler;
    }

}
