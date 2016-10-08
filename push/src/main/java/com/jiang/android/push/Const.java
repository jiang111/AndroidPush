package com.jiang.android.push;

import android.text.TextUtils;

/**
 * Created by jiang on 2016/10/8.
 */

public class Const {

    private static String miui_app_id = "12";
    private static String miui_app_key = "12";
    private static String flyme_app_id = "12";
    private static String flyme_app_key = "12";

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


    public static void setMiui_app_id(String miui_app_id) {
        Const.miui_app_id = miui_app_id;
    }

    public static void setMiui_app_key(String miui_app_key) {
        Const.miui_app_key = miui_app_key;
    }

    public static void setFlyme_app_id(String flyme_app_id) {
        Const.flyme_app_id = flyme_app_id;
    }

    public static void setFlyme_app_key(String flyme_app_key) {
        Const.flyme_app_key = flyme_app_key;
    }
}
