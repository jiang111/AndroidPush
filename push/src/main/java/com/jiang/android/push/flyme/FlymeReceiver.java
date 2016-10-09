package com.jiang.android.push.flyme;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jiang.android.push.PushInterface;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * flyme push
 * Created by jiang on 2016/10/8.
 */

public class FlymeReceiver extends MzPushMessageReceiver {

    private static final String TAG = "FlymeReceiver";

    private static PushInterface mPushInterface;

    public static void registerInterface(PushInterface pushInterface) {
        if (mPushInterface == null)
            mPushInterface = pushInterface;
    }

    public static PushInterface getPushInterface() {
        return mPushInterface;
    }

    @Override
    public void onRegister(Context context, String pushid) {
        //应用在接受返回的pushid
        if (mPushInterface != null) {
            mPushInterface.onRegister(context,pushid);
        }
    }

    @Override
    public void onMessage(Context context, String s) {
        //接收服务器推送的消息,这里要手动判断传递下来的是什么消息类型
        // 200 正常 520 多个消息,其他都算失败
    }

    /**
     * 3.0 down
     *
     * @param context
     * @param intent
     */
    @Override
    public void onMessage(Context context, Intent intent) {
        super.onMessage(context, intent);
    }

    @Override
    public void onUnRegister(Context context, boolean b) {
        //调用PushManager.unRegister(context）方法后，会在此回调反注册状态
        if (mPushInterface != null) {
            mPushInterface.onUnRegister(context, b);
        }
    }

    //设置通知栏小图标
    @Override
    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        // pushNotificationBuilder.setmStatusbarIcon(R.drawable.mz_stat_share_weibo);
    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {
        //检查通知栏和透传消息开关状态回调
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        Log.i(TAG, "onRegisterStatus " + registerStatus);
        //新版订阅回调
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {
        Log.i(TAG, "onUnRegisterStatus " + unRegisterStatus);
        //新版反订阅回调
    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {
        Log.i(TAG, "onSubTagsStatus " + subTagsStatus);
        //标签回调
    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {
        Log.i(TAG, "onSubAliasStatus " + subAliasStatus);
        //别名回调
    }
}
