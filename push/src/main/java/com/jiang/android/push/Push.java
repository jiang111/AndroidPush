package com.jiang.android.push;

import android.content.Context;
import android.text.TextUtils;

import com.jiang.android.push.emui.EMPushManager;
import com.jiang.android.push.jpush.JPushManager;
import com.jiang.android.push.miui.MiuiPushManager;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.oppo.OppoPushManager;
import com.jiang.android.push.utils.RomUtil;
import com.jiang.android.push.utils.Target;
import com.jiang.android.push.vivo.ViVOPushManager;

/**
 * Created by jiang on 2016/10/8.
 */

public class Push {


    private static IPushManager pushManager;


    public static void initPushManager(IPushManager pushManager) {
        Push.pushManager = pushManager;
    }

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
    public static void register(Context context, boolean debug, final PushInterface pushInterface) {
        if (context == null) return;
        init();
        pushManager.register(context, debug, pushInterface);


    }

    private static void init() {

        if (pushManager != null) return;

        if (RomUtil.rom() == Target.EMUI) {
            pushManager = new EMPushManager();
        }
        if (RomUtil.rom() == Target.MIUI) {
            pushManager = new MiuiPushManager();
        }

        if (RomUtil.rom() == Target.JPUSH) {
            pushManager = new JPushManager();
        }

        if (RomUtil.rom() == Target.VIVO) {
            pushManager = new ViVOPushManager();

        }
        if (RomUtil.rom() == Target.OPPO) {
            pushManager = new OppoPushManager();
        }


    }


    public static void unregister(Context context) {
        if (context == null) return;
        pushManager.unregister(context);
    }


    /**
     * @param pushInterface
     */
    public static void setPushInterface(PushInterface pushInterface) {
        if (pushInterface == null) return;
        pushManager.setPushInterface(pushInterface);
    }


    /**
     * 设置别名，
     * 华为不支持alias的写法，所以只能用tag，tag只能放map，所以alias作为value,key为name
     *
     * @param context
     * @param alias
     */
    public static void setAlias(final Context context, String alias) {
        if (context == null) return;
        if (TextUtils.isEmpty(alias)) return;
        pushManager.setAlias(context, alias);
    }

    /**
     * 获取唯一的token
     *
     * @param context
     * @return
     */
    public static TokenModel getToken(Context context) {
        if (context == null) return null;
        return pushManager.getToken(context);
    }


    /**
     * 停止推送
     */
    public static void pause(Context context) {
        if (context == null) return;
        pushManager.pause(context);
    }


    /**
     * 开始推送
     */
    public static void resume(Context context) {
        if (context == null) return;
        pushManager.resume(context);

    }

}
