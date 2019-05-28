package com.jiang.android.push.miui;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import com.jiang.android.push.Const;
import com.jiang.android.push.IPushManager;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.L;
import com.jiang.android.push.utils.RomUtil;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class MiuiPushManager implements IPushManager {
    @Override
    public void register(Context context, boolean debug, PushInterface pushInterface) {
        if (pushInterface != null) {
            MiuiReceiver.registerInterface(pushInterface);
        }
        if (shouldInit(context)) {
            MiPushClient.registerPush(context, Const.getMiui_app_id(), Const.getMiui_app_key());
        }
        if (debug) {
            LoggerInterface newLogger = new LoggerInterface() {
                @Override
                public void setTag(String tag) {
                    // ignore
                }

                @Override
                public void log(String content, Throwable t) {
                    L.i("content" + content + " exception: " + t.toString());
                }

                @Override
                public void log(String content) {
                    L.i("miui: " + content);
                }
            };
            Logger.setLogger(context, newLogger);
        }
    }


    private static boolean shouldInit(Context context) {
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = context.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void unregister(Context context) {
        MiuiReceiver.clearPushInterface();
        MiPushClient.unregisterPush(context);
    }

    @Override
    public void setPushInterface(PushInterface pushInterface) {
        if (pushInterface != null) {
            MiuiReceiver.registerInterface(pushInterface);
        }
    }

    @Override
    public void setAlias(Context context, String alias) {
        MiPushClient.setAlias(context, alias, null);

    }

    @Override
    public TokenModel getToken(Context context) {
        if (context == null)
            return null;
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        result.setToken(MiPushClient.getRegId(context));
        return result;
    }

    @Override
    public void pause(Context context) {
        MiPushClient.pausePush(context, null);
        if (MiuiReceiver.getPushInterface() != null) {
            MiuiReceiver.getPushInterface().onPaused(context);
        }
    }

    @Override
    public void resume(Context context) {
        MiPushClient.resumePush(context, null);
        if (MiuiReceiver.getPushInterface() != null) {
            MiuiReceiver.getPushInterface().onResume(context);
        }
    }
}
