package com.jiang.android.push.vivo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jiang.android.push.Message;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.utils.JHandler;
import com.jiang.android.push.utils.JsonUtils;
import com.jiang.android.push.utils.Target;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

public class PushMessageReceiverImpl extends OpenClientPushMessageReceiver {


    private static PushInterface mPushInterface;
    private String mMessage;

    /**
     * @param pushInterface
     */
    public static void registerInterface(PushInterface pushInterface) {

        mPushInterface = pushInterface;
    }

    public static PushInterface getPushInterface() {
        return mPushInterface;
    }


    @Override
    public void onNotificationMessageClicked(final Context context, UPSNotificationMessage message) {
        mMessage = message.getContent();

        if (mPushInterface != null) {
            final Message result = new Message();
            result.setNotifyID(1);
            result.setMessageID(String.valueOf(message.getMsgId()));
            result.setTitle(message.getTitle());
            result.setMessage(mMessage);
            result.setTarget(Target.MIUI);
            try {
                result.setExtra(JsonUtils.setJson(message.getParams()).toString());
            } catch (Exception e) {
                Log.e(TAG, "onNotificationMessageClicked: " + e.toString());
                result.setExtra("{}");
            }
            JHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushInterface.onMessageClicked(context, result);
                }
            });
        }
    }

    @Override
    public void onReceiveRegId(Context context, String s) {
        if (mPushInterface != null) {
            Message message = new Message();
            message.setMessage(s);
            mPushInterface.onMessage(context, message);
        }
    }
}
