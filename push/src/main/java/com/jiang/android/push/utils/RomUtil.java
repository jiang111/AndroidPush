package com.jiang.android.push.utils;

import java.io.IOException;


/**
 * Created by jiang on 2016/10/8.
 */

public class RomUtil {
    private static Target mTarget = null;
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_FLYME_ID_FALG_KEY = "ro.build.display.id";
    private static final String KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme";
    private static final String KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme";
    private static final String KEY_FLYME_PUBLISH_FALG = "ro.flyme.published";


    /**
     * 华为rom
     *
     * @return
     */
    private static boolean isEMUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_EMUI_VERSION_CODE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 小米rom
     *
     * @return
     */
    private static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            /*String rom = "" + prop.getProperty(KEY_MIUI_VERSION_CODE, null) + prop.getProperty(KEY_MIUI_VERSION_NAME, null)+prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null);
            Log.d("Android_Rom", rom);*/
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 魅族rom
     *
     * @return
     */
    private static boolean isFlyme() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            /*String rom = "" + prop.getProperty(KEY_MIUI_VERSION_CODE, null) + prop.getProperty(KEY_MIUI_VERSION_NAME, null)+prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null);
            Log"Android_Rom", rom);*/
            return prop.getProperty(KEY_FLYME_ID_FALG_KEY, null) != null
                    || prop.getProperty(KEY_FLYME_ID_FALG_VALUE_KEYWORD, null) != null
                    || prop.getProperty(KEY_FLYME_ICON_FALG, null) != null
                    || prop.getProperty(KEY_FLYME_SETUP_FALG, null) != null
                    || prop.getProperty(KEY_FLYME_PUBLISH_FALG, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }


    public static Target rom() {
        if (mTarget != null)
            return mTarget;

        if (isEMUI()) {
            mTarget = Target.EMUI;
            return mTarget;
        }
        if (isFlyme()) {
            mTarget = Target.FLYME;
            return mTarget;
        }
        if (isMIUI()) {
            mTarget = Target.MIUI;
            return mTarget;
        }
        mTarget = Target.JPUSH;
        return mTarget;
    }

}
