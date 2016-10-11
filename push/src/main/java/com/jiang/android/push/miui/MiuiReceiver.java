package com.jiang.android.push.miui;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jiang.android.push.Message;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.utils.JHandler;
import com.jiang.android.push.utils.JsonUtils;
import com.jiang.android.push.utils.L;
import com.jiang.android.push.utils.Target;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 小米推送下来的消息运行在子线程
 * Created by jiang on 2016/10/8.
 */

public class MiuiReceiver extends PushMessageReceiver {

    private static final String TAG = "MiuiReceiver";
    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;


    private static PushInterface mPushInterface;

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
    public void onReceivePassThroughMessage(final Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
        if (mPushInterface != null) {
            final Message result = new Message();
            result.setMessageID(message.getMessageId());
            result.setTitle(mTopic);
            result.setExtra(mMessage);
            result.setTarget(Target.MIUI);
            try {
                result.setExtra(JsonUtils.setJson(message.getExtra()).toString());
            } catch (Exception e) {
                L.i("onReceivePassThroughMessage: " + e.toString());
                result.setExtra("{}");
            }
            JHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushInterface.onCustomMessage(context, result);
                }
            });
        }


    }

    @Override
    public void onNotificationMessageClicked(final Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
        if (mPushInterface != null) {
            final Message result = new Message();
            result.setNotifyID(message.getNotifyId());
            result.setMessageID(message.getMessageId());
            result.setTitle(mTopic);
            result.setMessage(mMessage);
            result.setTarget(Target.MIUI);
            try {
                result.setExtra(JsonUtils.setJson(message.getExtra()).toString());
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
    public void onNotificationMessageArrived(final Context context, MiPushMessage message) {
        mMessage = message.getContent();
        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        } else if (!TextUtils.isEmpty(message.getUserAccount())) {
            mUserAccount = message.getUserAccount();
        }
        if (mPushInterface != null) {
            final Message result = new Message();
            result.setTitle(mTopic);
            result.setMessage(mMessage);
            result.setTarget(Target.MIUI);
            try {
                result.setExtra(JsonUtils.setJson(message.getExtra()).toString());
            } catch (Exception e) {
                L.i("onReceivePassThroughMessage: " + e.toString());
                result.setExtra("{}");
            }
            JHandler.handler().post(new Runnable() {
                @Override
                public void run() {
                    mPushInterface.onMessage(context, result);
                }
            });
        }
    }

    @Override
    public void onCommandResult(final Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                if (mPushInterface != null) {
                    JHandler.handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mPushInterface.onRegister(context, mRegId);
                        }
                    });
                }
            } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mAlias = cmdArg1;
                    if (mPushInterface != null) {
                        JHandler.handler().post(new Runnable() {
                            @Override
                            public void run() {
                                mPushInterface.onAlias(context, mAlias);
                            }
                        });
                    }
                }
            } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mAlias = cmdArg1;
                }
            } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mTopic = cmdArg1;
                }
            } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mTopic = cmdArg1;
                }
            } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
                if (message.getResultCode() == ErrorCode.SUCCESS) {
                    mStartTime = cmdArg1;
                    mEndTime = cmdArg2;
                }
            }
        }



    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        super.onReceiveRegisterResult(context, miPushCommandMessage);
    }

    public static void clearPushInterface() {
        mPushInterface = null;
    }

}
