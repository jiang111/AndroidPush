package com.jiang.android.push.emui;

import android.content.Context;

import com.huawei.android.pushagent.api.PushManager;
import com.jiang.android.push.IPushManager;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.RomUtil;
import com.jiang.android.push.utils.Target;

import java.util.HashMap;
import java.util.Map;

public class EMPushManager implements IPushManager {
    @Override
    public void register(Context context, boolean debug, PushInterface pushInterface) {
        if (pushInterface != null) {
            EMHuaweiPushReceiver.registerInterface(pushInterface);
        }
        PushManager.requestToken(context);

    }

    @Override
    public void unregister(Context context) {
        EMHuaweiPushReceiver.clearPushInterface();
        PushManager.deregisterToken(context, getToken(context).getToken());

    }

    @Override
    public void setPushInterface(PushInterface pushInterface) {
        if (pushInterface != null) {
            EMHuaweiPushReceiver.registerInterface(pushInterface);
        }
    }

    @Override
    public void setAlias(Context context, String alias) {
        Map<String, String> tag = new HashMap<>();
        tag.put("name", alias);
        PushManager.setTags(context, tag);
    }

    @Override
    public TokenModel getToken(Context context) {
        if (context == null)
            return null;
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        result.setToken(EMHuaweiPushReceiver.getmToken());
        return result;
    }

    @Override
    public void pause(Context context) {
        PushManager.enableReceiveNormalMsg(context, false);
        PushManager.enableReceiveNotifyMsg(context, false);
        if (EMHuaweiPushReceiver.getPushInterface() != null) {
            EMHuaweiPushReceiver.getPushInterface().onPaused(context);
        }

    }

    @Override
    public void resume(Context context) {
        PushManager.enableReceiveNormalMsg(context, true);
        PushManager.enableReceiveNotifyMsg(context, true);
        if (EMHuaweiPushReceiver.getPushInterface() != null) {
            EMHuaweiPushReceiver.getPushInterface().onResume(context);
        }
    }
}
