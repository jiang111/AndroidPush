package com.jiang.android.push;

import android.os.Bundle;

/**
 * Created by jiang on 2016/10/8.
 */

public class Message {
    private String title;
    private String desc;
    private Bundle extra;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bundle getExtra() {
        return extra;
    }

    public void setExtra(Bundle extra) {
        this.extra = extra;
    }
}
