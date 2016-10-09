package com.jiang.android.pushdemo;

import android.content.Context;
import android.util.Log;

import com.jiang.android.push.Message;
import com.jiang.android.push.PushInterface;

/**
 * Created by jiang on 2016/10/9.
 */

public class PushService implements PushInterface {

    private static final String TAG = "PushServiceforMyOwn";

    @Override
    public void onRegister(Context context, String registerID) {
        Log.i(TAG, "onRegister: " + " id: " + registerID);

    }

    @Override
    public void onUnRegister(Context context) {
        Log.i(TAG, "onUnRegister: ");

    }

    @Override
    public void onPaused(Context context) {
        Log.i(TAG, "onPaused: ");

    }

    @Override
    public void onResume(Context context) {
        Log.i(TAG, "onResume: ");

    }

    @Override
    public void onMessage(Context context, Message message) {
        Log.i(TAG, "onMessage: " + message.toString());

    }

    @Override
    public void onMessageClicked(Context context, Message message) {
        Log.i(TAG, "onMessageClicked: " + message.toString());

    }

    @Override
    public void onCustomMessage(Context context, Message message) {
        Log.i(TAG, "onCustomMessage: " + message.toString());

    }
}
