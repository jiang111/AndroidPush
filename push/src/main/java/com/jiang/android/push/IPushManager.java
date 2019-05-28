package com.jiang.android.push;

import android.content.Context;

import com.jiang.android.push.model.TokenModel;

public interface IPushManager {
    void register(Context context, boolean debug, final PushInterface pushInterface);

    void unregister(Context context);

    void setPushInterface(PushInterface pushInterface);

    void setAlias(final Context context, String alias);

    TokenModel getToken(Context context);

    void pause(Context context);

    void resume(Context context);
}