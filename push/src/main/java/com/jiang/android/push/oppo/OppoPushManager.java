package com.jiang.android.push.oppo;

import android.content.Context;

import com.coloros.mcssdk.PushManager;
import com.coloros.mcssdk.callback.PushCallback;
import com.coloros.mcssdk.mode.SubscribeResult;
import com.jiang.android.push.Const;
import com.jiang.android.push.IPushManager;
import com.jiang.android.push.PushInterface;
import com.jiang.android.push.model.TokenModel;
import com.jiang.android.push.utils.RomUtil;

import java.util.ArrayList;
import java.util.List;

public class OppoPushManager implements IPushManager {
    @Override
    public void register(Context context, boolean debug, PushInterface pushInterface) {
        PushManager.getInstance().register(context, Const.getColor_app_key(), Const.getColor_app_secret(), new PushCallback() {
            @Override
            public void onRegister(int i, String s) {


            }

            @Override
            public void onUnRegister(int i) {

            }

            @Override
            public void onGetAliases(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onSetAliases(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onUnsetAliases(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onSetUserAccounts(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onUnsetUserAccounts(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onGetUserAccounts(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onSetTags(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onUnsetTags(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onGetTags(int i, List<SubscribeResult> list) {

            }

            @Override
            public void onGetPushStatus(int i, int i1) {

            }

            @Override
            public void onSetPushTime(int i, String s) {

            }

            @Override
            public void onGetNotificationStatus(int i, int i1) {

            }
        });
    }

    @Override
    public void unregister(Context context) {
        PushManager.getInstance().unRegister();

    }

    @Override
    public void setPushInterface(PushInterface pushInterface) {

    }

    @Override
    public void setAlias(Context context, String alias) {

        List<String> list = new ArrayList<>();
        list.add(alias);
        PushManager.getInstance().setAliases(list);
    }

    @Override
    public TokenModel getToken(Context context) {
        if (context == null)
            return null;
        TokenModel result = new TokenModel();
        result.setTarget(RomUtil.rom());
        result.setToken(PushManager.getInstance().getRegisterID());
        return result;
    }

    @Override
    public void pause(Context context) {
        PushManager.getInstance().pausePush();

    }

    @Override
    public void resume(Context context) {
        PushManager.getInstance().resumePush();
    }
}
