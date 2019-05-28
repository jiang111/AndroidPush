package com.jiang.android.push.flyme;

import android.content.Context;

import com.jiang.android.push.Const;
import com.jiang.android.push.IPushManager;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.RomUtil;
import com.meizu.cloud.pushsdk.PushManager;

public class FlymePushManager implements IPushManager {
    @Override
    public void register(Context context, boolean debug, PushInterface pushInterface) {
        if (pushInterface != null) {
            FlymeReceiver.registerInterface(pushInterface);
        }
        PushManager.register(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
    }

    @Override
    public void unregister(Context context) {
        FlymeReceiver.clearPushInterface();
        PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
    }

    @Override
    public void setPushInterface(PushInterface pushInterface) {
        if (pushInterface != null) {
            FlymeReceiver.registerInterface(pushInterface);
        }
    }

    @Override
    public void setAlias(Context context, String alias) {
        PushManager.subScribeAlias(context, Const.getFlyme_app_id(), Const.getFlyme_app_key(), getToken(context).getToken(), alias);

    }

    @Override
    public TokenModel getToken(Context context) {
        if (context == null)
            return null;
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        result.setToken(PushManager.getPushId(context));
        return result;
    }

    @Override
    public void pause(Context context) {
        PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
        if (FlymeReceiver.getPushInterface() != null) {
            FlymeReceiver.getPushInterface().onPaused(context);
        }
    }

    @Override
    public void resume(Context context) {
        PushManager.register(context, Const.getMiui_app_id(), Const.getFlyme_app_key());
        if (FlymeReceiver.getPushInterface() != null) {
            FlymeReceiver.getPushInterface().onResume(context);
        }
    }
}
