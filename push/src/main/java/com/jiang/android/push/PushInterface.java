package com.jiang.android.push;

import android.content.Context;

/**
 * warning:
 * 在flyme的推送中,不会回调onMessage()和onMessageClicked()方法
 * 在华为推送中，不会回调onMessage()和onAlias()方法
 * Created by jiang on 2016/10/8.
 */

public interface PushInterface {

    /**
     * 注册成功之后回调
     *
     * @param context
     * @param registerID
     */
    void onRegister(Context context, String registerID);

    /**
     * 取消注册成功
     *
     * @param context
     */
    void onUnRegister(Context context);

    /**
     * 暂停推送
     *
     * @param context
     */
    void onPaused(Context context);

    /**
     * 开启推送
     *
     * @param context
     */
    void onResume(Context context);

    /**
     * 通知下来之后
     *
     * @param context
     * @param message
     */
    void onMessage(Context context, Message message);

    /**
     * 通知栏被点击之后
     *
     * @param context
     * @param message
     */
    void onMessageClicked(Context context, Message message);

    /**
     * 透传消息
     *
     * @param context
     * @param message
     */
    void onCustomMessage(Context context, Message message);


    /**
     * 别名设置成功的回调
     *
     * @param context
     * @param alias
     */
    void onAlias(Context context, String alias);
}
