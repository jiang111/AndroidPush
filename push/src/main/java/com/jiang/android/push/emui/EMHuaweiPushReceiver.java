package com.jiang.android.push.emui;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import com.huawei.android.pushagent.PushReceiver;
import com.jiang.android.push.Message;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.utils.JHandler;
import com.jiang.android.push.utils.L;
import com.jiang.android.push.utils.Target;

/**
 * Created by jiang on 2016/10/8.
 */

public class EMHuaweiPushReceiver extends PushReceiver {

    private static String mToken = null;


    private static PushInterface mPushInterface;

    public static void registerInterface(PushInterface pushInterface) {
        mPushInterface = pushInterface;
    }

    public static PushInterface getPushInterface() {
        return mPushInterface;
    }

    public static String getmToken() {
        return mToken;
    }


    @Override
    public void onToken(final Context context, final String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        L.i(content);
        mToken = token;
        if (mPushInterface != null) {
            JHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushInterface.onRegister(context, token);
                }
            });
        }
    }

    @Override
    public void onPushMsg(Context context, byte[] bytes, String s) {
        super.onPushMsg(context, bytes, s);
        L.i("onPushMsg...");
    }


    @Override
    public boolean onPushMsg(final Context context, byte[] msg, Bundle bundle) {
        //这里是透传消息， msg是透传消息的字节数组 bundle字段没用
        L.i("onPushMsg: " + new String(msg));
        try {
            String content = new String(msg, "UTF-8");
            if (mPushInterface != null) {
                final Message message = new Message();
                message.setMessage(content);
                //华为的sdk在透传的时候无法实现extra字段，这里要注意
                message.setExtra("{}");
                message.setTarget(Target.EMUI);
                JHandler.handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mPushInterface.onCustomMessage(context, message);
                    }
                });
            }
            L.i(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 在华为的sdk中，通知栏的事件只有三种:
     * <p>
     * NOTIFICATION_OPENED, //通知栏中的通知被点击打开
     * NOTIFICATION_CLICK_BTN, //通知栏中通知上的按钮被点击
     * PLUGINRSP, //标签上报回应
     *
     * @param context
     * @param event
     * @param extras
     */
    public void onEvent(final Context context, Event event, Bundle extras) {
        L.i("onEvent: ");
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = "收到通知附加消息： " + extras.getString(BOUND_KEY.pushMsgKey);
            L.i(content);
//            try {
//                if (mPushInterface != null) {
//                    final Message message = new Message();
//                    message.setTitle("暂无");
//                    message.setNotifyID(notifyId);
//                    message.setMessage(content);
//                    message.setExtra(JsonUtils.getJson(extras));
//                    message.setTarget(Target.EMUI);
//                    JHandler.handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            mPushInterface.onMessageClicked(context, message);
//                        }
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } else if (Event.PLUGINRSP.equals(event)) {
            final int TYPE_LBS = 1;
            final int TYPE_TAG = 2;
            int reportType = extras.getInt(BOUND_KEY.PLUGINREPORTTYPE, -1);
            boolean isSuccess = extras.getBoolean(BOUND_KEY.PLUGINREPORTRESULT, false);
            String message = "";
            if (TYPE_LBS == reportType) {
                message = "LBS report result :";
            } else if (TYPE_TAG == reportType) {
                message = "TAG report result :";
            }
            L.i(message + isSuccess);
            //   showPushMessage(PustDemoActivity.RECEIVE_TAG_LBS_MSG, message + isSuccess);
            super.onEvent(context, event, extras);
        }
    }


    public static void clearPushInterface() {
        mPushInterface = null;
    }
}
