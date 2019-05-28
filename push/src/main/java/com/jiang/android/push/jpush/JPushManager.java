package com.jiang.android.push.jpush;

import android.content.Context;

import com.jiang.android.push.IPushManager;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.L;
import com.jiang.android.push.utils.RomUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class JPushManager implements IPushManager {
    @Override
    public void register(Context context, boolean debug, PushInterface pushInterface) {
        if (pushInterface != null) {
            JPushReceiver.registerInterface(pushInterface);
        }
        JPushInterface.init(context);
        JPushInterface.setDebugMode(debug);
    }

    @Override
    public void unregister(Context context) {
        JPushReceiver.clearPushInterface();
        JPushInterface.stopPush(context);
    }

    @Override
    public void setPushInterface(PushInterface pushInterface) {
        if (pushInterface != null) {
            JPushReceiver.registerInterface(pushInterface);
        }
    }

    @Override
    public void setAlias(final Context context, String alias) {
        JPushInterface.setAlias(context, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) { // 这里极光规定0代表成功
                    if (JPushReceiver.getPushInterface() != null) {
                        L.i("JPushInterface.setAlias");
                        JPushReceiver.getPushInterface().onAlias(context, s);

                    }
                }
            }
        });
    }

    @Override
    public TokenModel getToken(Context context) {
        if (context == null)
            return null;
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        result.setToken(JPushInterface.getRegistrationID(context));
        return result;
    }

    @Override
    public void pause(Context context) {
        if (!JPushInterface.isPushStopped(context)) {
            JPushInterface.stopPush(context);
            if (JPushReceiver.getPushInterface() != null) {
                JPushReceiver.getPushInterface().onPaused(context);
            }
        }
    }

    @Override
    public void resume(Context context) {
        if (JPushInterface.isPushStopped(context)) {
            JPushInterface.resumePush(context);
            if (JPushReceiver.getPushInterface() != null) {
                JPushReceiver.getPushInterface().onResume(context);
            }
        }
    }
}
