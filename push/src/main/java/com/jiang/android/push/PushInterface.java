package com.jiang.android.push;

import android.content.Context;

/**
 * PushInterface 在所有的receiver中只能注册一次
 * Created by jiang on 2016/10/8.
 */

public interface PushInterface {

    void onRegister(Context context);

    void onUnRegister(Context context, boolean b);

    void onPaused(Context context);

    void onResume(Context context);

    void onToken(Context context, String token);

    void onMessage(Context context, Message message);

    /**
     * 极光叫自定义消息
     * flyme叫透传消息
     * @param context
     * @param message
     */
    void onCustomMessage(Context context, Message message);


}
