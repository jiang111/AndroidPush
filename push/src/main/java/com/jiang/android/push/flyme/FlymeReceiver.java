package com.jiang.android.push.flyme;

import android.content.Context;
import android.util.Log;

import com.jiang.android.push.Message;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.utils.JHandler;
import com.jiang.android.push.utils.Target;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * flyme push
 * 如果发现onregister回调了两次，那就得删除相关的代码，主要是因为flyme的sdk分老版本和新版本
 * Created by jiang on 2016/10/8.
 */

public class FlymeReceiver extends MzPushMessageReceiver {

    private static final String TAG = "FlymeReceiver";

    private static PushInterface mPushInterface;

    public static void registerInterface(PushInterface pushInterface) {

        mPushInterface = pushInterface;
    }

    public static PushInterface getPushInterface() {
        return mPushInterface;
    }

    @Override
    public void onRegister(final Context context, final String pushid) {
        //应用在接受返回的pushid
        if (mPushInterface != null) {
            JHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushInterface.onRegister(context, pushid);

                }
            });
        }
    }

    /**
     * 若该回调有数据，就证明是透传消息，否则全部都是通知消息
     *
     * @param context
     * @param s
     */
    @Override
    public void onMessage(final Context context, String s) {
        if (mPushInterface != null) {
            final Message message = new Message();
            message.setMessageID("");
            message.setTarget(Target.FLYME);
            message.setExtra(s);
            JHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushInterface.onCustomMessage(context, message);

                }
            });
        }

    }


    @Override
    public void onUnRegister(final Context context, boolean b) {
        //调用PushManager.unRegister(context）方法后，会在此回调反注册状态
        if (b == true) {
            if (mPushInterface != null) {
                JHandler.handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mPushInterface.onUnRegister(context);

                    }
                });
            }
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
    public void onRegisterStatus(final Context context, final RegisterStatus registerStatus) {
        Log.i(TAG, "onRegisterStatus " + registerStatus);
        //新版订阅回调
        if (mPushInterface != null) {
            JHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushInterface.onRegister(context, registerStatus.getPushId());
                }
            });
        }
    }

    @Override
    public void onUnRegisterStatus(final Context context, UnRegisterStatus unRegisterStatus) {
        Log.i(TAG, "onUnRegisterStatus " + unRegisterStatus);
        //新版反订阅回调
        if (mPushInterface != null) {
            JHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushInterface.onUnRegister(context);
                }
            });
        }
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

    public static void clearPushInterface() {
        mPushInterface = null;
    }
}
