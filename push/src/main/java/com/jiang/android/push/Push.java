package com.jiang.android.push;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.huawei.android.pushagent.api.PushManager;
import com.jiang.android.push.emui.EMHuaweiPushReceiver;
import com.jiang.android.push.flyme.FlymeReceiver;
import com.jiang.android.push.jpush.JPushReceiver;
import com.jiang.android.push.miui.MiuiReceiver;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.RomUtil;
import com.jiang.android.push.utils.Target;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jiang on 2016/10/8.
 */

public class Push {
    private static final String TAG = "Push";

    /**
     * 初始化配置
     *
     * @param context
     * @param debug
     */
    public static void register(Context context, boolean debug) {
        register(context, debug, null);
    }

    /**
     * 注册
     *
     * @param context
     * @param debug
     * @param pushInterface
     */
    public static void register(Context context, boolean debug, PushInterface pushInterface) {
        if (context == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            if (pushInterface != null) {
                EMHuaweiPushReceiver.registerInterface(pushInterface);
            }
            PushManager.requestToken(context);
            return;

        }
        if (RomUtil.rom() == Target.MIUI) {
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
                        Log.d(TAG, "miui:" + content, t);
                    }

                    @Override
                    public void log(String content) {
                        Log.d(TAG, "miui: " + content);
                    }
                };
                Logger.setLogger(context, newLogger);
            }

            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            if (pushInterface != null) {
                FlymeReceiver.registerInterface(pushInterface);
            }
            com.meizu.cloud.pushsdk.PushManager.register(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
            if (pushInterface != null) {
                JPushReceiver.registerInterface(pushInterface);
            }
            JPushInterface.init(context);
            JPushInterface.setDebugMode(debug);
            return;
        }


    }

    /**
     * 用于小米推送的注册
     * @param context
     * @return
     */
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

    public void unregister(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            PushManager.deregisterToken(context, getToken(context).getToken());
            return;

        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.unregisterPush(context);
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            com.meizu.cloud.pushsdk.PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
            JPushInterface.stopPush(context);
            return;
        }
    }


    /**
     * 只有当第一次调用才会生效，后续调用不会生效
     *
     * @param pushInterface
     */
    public static void setPushInterface(PushInterface pushInterface) {
        if (pushInterface == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            if (pushInterface != null) {
                EMHuaweiPushReceiver.registerInterface(pushInterface);
            }
            return;

        }
        if (RomUtil.rom() == Target.MIUI) {
            if (pushInterface != null) {
                MiuiReceiver.registerInterface(pushInterface);
            }
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            if (pushInterface != null) {
                FlymeReceiver.registerInterface(pushInterface);
            }
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
            if (pushInterface != null) {
                JPushReceiver.registerInterface(pushInterface);
            }
            return;
        }
    }

    /**
     * 获取唯一的token
     *
     * @param context
     * @return
     */
    public static TokenModel getToken(Context context) {
        if (context == null)
            return null;
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        if (RomUtil.rom() == Target.EMUI) {
            result.setToken(EMHuaweiPushReceiver.getmToken());
        }
        if (RomUtil.rom() == Target.MIUI) {
            result.setToken(MiPushClient.getRegId(context));
        }
        if (RomUtil.rom() == Target.FLYME) {
            result.setToken(com.meizu.cloud.pushsdk.PushManager.getPushId(context));
        }

        if (RomUtil.rom() == Target.JPUSH) {
            result.setToken(JPushInterface.getRegistrationID(context));
        }
        return result;

    }


    /**
     * 停止推送
     */
    public static void stop(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            PushManager.enableReceiveNormalMsg(context, false);
            PushManager.enableReceiveNotifyMsg(context, false);
            if (EMHuaweiPushReceiver.getPushInterface() != null) {
                EMHuaweiPushReceiver.getPushInterface().onPaused(context);
            }
            return;

        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.pausePush(context, null);
            if (MiuiReceiver.getPushInterface() != null) {
                MiuiReceiver.getPushInterface().onResume(context);
            }
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            com.meizu.cloud.pushsdk.PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
            if (FlymeReceiver.getPushInterface() != null) {
                FlymeReceiver.getPushInterface().onResume(context);
            }
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
            if (!JPushInterface.isPushStopped(context)) {
                JPushInterface.stopPush(context);
                if (JPushReceiver.getPushInterface() != null) {
                    JPushReceiver.getPushInterface().onResume(context);
                }
            }
            return;
        }

    }


    /**
     * 开始推送
     */
    public static void start(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            PushManager.enableReceiveNormalMsg(context, true);
            PushManager.enableReceiveNotifyMsg(context, true);
            if (EMHuaweiPushReceiver.getPushInterface() != null) {
                EMHuaweiPushReceiver.getPushInterface().onResume(context);
            }
            return;
        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.resumePush(context, null);
            if (MiuiReceiver.getPushInterface() != null) {
                MiuiReceiver.getPushInterface().onResume(context);
            }
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            com.meizu.cloud.pushsdk.PushManager.register(context, Const.getMiui_app_id(), Const.getFlyme_app_key());
            if (FlymeReceiver.getPushInterface() != null) {
                FlymeReceiver.getPushInterface().onResume(context);
            }
            return;
        }
        if (RomUtil.rom() == Target.JPUSH) {
            if (JPushInterface.isPushStopped(context)) {
                JPushInterface.resumePush(context);
                if (JPushReceiver.getPushInterface() != null) {
                    JPushReceiver.getPushInterface().onResume(context);
                }
            }
            return;
        }
    }

}
