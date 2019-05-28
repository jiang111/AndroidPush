package com.jiang.android.push;

import android.text.TextUtils;

/**
 * Created by jiang on 2016/10/8.
 */

public class Const {

    private static String miui_app_id = null;
    private static String miui_app_key = null;
    private static String flyme_app_id = null;
    private static String flyme_app_key = null;
    private static String color_app_key = null;
    private static String color_app_secret = null;

    public static String getMiui_app_id() {
        if (TextUtils.isEmpty(miui_app_id)) {
            throw new NullPointerException("please config miui_app_id before use it");
        }
        return miui_app_id;
    }

    public static String getMiui_app_key() {
        if (TextUtils.isEmpty(miui_app_key)) {
            throw new NullPointerException("please config miui_app_key before use it");
        }
        return miui_app_key;
    }

    public static String getFlyme_app_id() {
        if (TextUtils.isEmpty(flyme_app_id)) {
            throw new NullPointerException("please config flyme_app_id before use it");
        }
        return flyme_app_id;
    }

    public static String getFlyme_app_key() {
        if (TextUtils.isEmpty(flyme_app_key)) {
            throw new NullPointerException("please config flyme_app_key before use it");
        }
        return flyme_app_key;
    }


    public static void setMiUI_APP(String miui_app_id, String miui_app_key) {
        setMiui_app_id(miui_app_id);
        setMiui_app_key(miui_app_key);
    }

    public static void setFlyme_APP(String flyme_app_id, String flyme_app_key) {
        setFlyme_app_id(flyme_app_id);
        setFlyme_app_key(flyme_app_key);
    }

    public static void setColor_APP(String app_key, String app_secret) {
        Const.color_app_key = app_key;
        Const.color_app_secret = app_secret;
    }

    public static String getColor_app_key() {
        if (TextUtils.isEmpty(color_app_key)) {
            throw new NullPointerException("please config color_app_key before use it");
        }
        return color_app_key;
    }

    public static String getColor_app_secret() {
        if (TextUtils.isEmpty(color_app_secret)) {
            throw new NullPointerException("please config color_app_secret before use it");
        }
        return color_app_secret;
    }

    private static void setMiui_app_id(String miui_app_id) {
        Const.miui_app_id = miui_app_id;
    }

    private static void setMiui_app_key(String miui_app_key) {
        Const.miui_app_key = miui_app_key;
    }

    private static void setFlyme_app_id(String flyme_app_id) {
        Const.flyme_app_id = flyme_app_id;
    }

    private static void setFlyme_app_key(String flyme_app_key) {
        Const.flyme_app_key = flyme_app_key;
    }
}
