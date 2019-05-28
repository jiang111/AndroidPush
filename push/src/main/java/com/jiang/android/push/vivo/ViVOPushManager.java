package com.jiang.android.push.vivo;

import android.content.Context;

import com.jiang.android.push.IPushManager;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.RomUtil;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

public class ViVOPushManager implements IPushManager {
    @Override
    public void register(Context context, boolean debug, final PushInterface pushInterface) {
        // 在当前工程入口函数，建议在 Application 的 onCreate 函数中，添加以下代码：
        PushClient.getInstance(context).initialize();
        // 当需要打开推送服务时，调用以下代码：
        PushClient.getInstance(context).turnOnPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
                if (i == 0 && pushInterface != null) {
                    PushMessageReceiverImpl.registerInterface(pushInterface);
                }

            }
        });
    }

    @Override
    public void unregister(Context context) {
        PushClient.getInstance(context).turnOffPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
                if (i == 0) {
                    PushMessageReceiverImpl.registerInterface(null);
                }

            }
        });

    }

    @Override
    public void setPushInterface(PushInterface pushInterface) {
        PushMessageReceiverImpl.registerInterface(pushInterface);

    }

    @Override
    public void setAlias(Context context, String alias) {
        PushClient.getInstance(context).bindAlias(alias, new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {

            }
        });
    }

    @Override
    public TokenModel getToken(Context context) {
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        result.setToken(PushClient.getInstance(context).getRegId());

        return result;
    }

    @Override
    public void pause(Context context) {

        PushClient.getInstance(context).turnOffPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {

            }
        });
        if (PushMessageReceiverImpl.getPushInterface() != null) {
            PushMessageReceiverImpl.getPushInterface().onPaused(context);
        }
    }

    @Override
    public void resume(Context context) {
        register(context, false, null);
        if (PushMessageReceiverImpl.getPushInterface() != null) {
            PushMessageReceiverImpl.getPushInterface().onResume(context);
        }
    }
}
