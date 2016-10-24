package com.jiang.android.push;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.huawei.android.pushagent.api.PushManager;
import com.jiang.android.push.emui.EMHuaweiPushReceiver;
import com.jiang.android.push.flyme.FlymeReceiver;
import com.jiang.android.push.jpush.JPushReceiver;
import com.jiang.android.push.miui.MiuiReceiver;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.L;
import com.jiang.android.push.utils.RomUtil;
import com.jiang.android.push.utils.Target;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by jiang on 2016/10/8.
 */

public class Push {

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
                        L.i("content" + content + " exception: " + t.toString());
                    }

                    @Override
                    public void log(String content) {
                        L.i("miui: " + content);
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
     *
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

    public static void unregister(Context context) {
        if (context == null)
            return;
        if (RomUtil.rom() == Target.EMUI) {
            EMHuaweiPushReceiver.clearPushInterface();
            PushManager.deregisterToken(context, getToken(context).getToken());
            return;

        }
        if (RomUtil.rom() == Target.MIUI) {
            MiuiReceiver.clearPushInterface();
            MiPushClient.unregisterPush(context);
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            FlymeReceiver.clearPushInterface();
            com.meizu.cloud.pushsdk.PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
            JPushReceiver.clearPushInterface();
            JPushInterface.stopPush(context);
            return;
        }
    }


    /**
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
     * 设置别名，
     * 华为不支持alias的写法，所以只能用tag，tag只能放map，所以alias作为value,key为name
     *
     * @param context
     * @param alias
     */
    public static void setAlias(final Context context, String alias) {
        if (TextUtils.isEmpty(alias))
            return;
        if (RomUtil.rom() == Target.EMUI) {
            Map<String, String> tag = new HashMap<>();
            tag.put("name", alias);
            PushManager.setTags(context, tag);
            return;

        }
        if (RomUtil.rom() == Target.MIUI) {
            MiPushClient.setAlias(context, alias, null);

            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            com.meizu.cloud.pushsdk.PushManager.subScribeAlias(context, Const.getFlyme_app_id(), Const.getFlyme_app_key(), getToken(context).getToken(), alias);
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
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
    public static void pause(Context context) {
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
                MiuiReceiver.getPushInterface().onPaused(context);
            }
            return;
        }
        if (RomUtil.rom() == Target.FLYME) {
            com.meizu.cloud.pushsdk.PushManager.unRegister(context, Const.getFlyme_app_id(), Const.getFlyme_app_key());
            if (FlymeReceiver.getPushInterface() != null) {
                FlymeReceiver.getPushInterface().onPaused(context);
            }
            return;
        }

        if (RomUtil.rom() == Target.JPUSH) {
            if (!JPushInterface.isPushStopped(context)) {
                JPushInterface.stopPush(context);
                if (JPushReceiver.getPushInterface() != null) {
                    JPushReceiver.getPushInterface().onPaused(context);
                }
            }
            return;
        }

    }


    /**
     * 开始推送
     */
    public static void resume(Context context) {
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
