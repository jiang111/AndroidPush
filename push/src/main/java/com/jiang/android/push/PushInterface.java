package com.jiang.android.push;

import android.content.Context;

/**
 * Created by jiang on 2016/10/8.
 */

public interface PushInterface {

    public void onRegister(Context context);

    public void onUnRegister(Context context, boolean b);

    public void onToken(Context context, String token);

    public void onMessage(Context context, boolean b);


}
