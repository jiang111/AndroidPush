package com.jiang.android.push.model;

import com.jiang.android.push.utils.Target;

/**
 * Created by jiang on 2016/10/8.
 */

public class TokenModel {
    private String mToken;
    private Target mTarget;

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public Target getTarget() {
        return mTarget;
    }

    public void setTarget(Target mTarget) {
        this.mTarget = mTarget;
    }
}
