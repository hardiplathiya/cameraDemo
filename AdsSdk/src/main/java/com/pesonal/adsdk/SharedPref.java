package com.pesonal.adsdk;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPreferences mSharedPref;
    public static final String PREF_NAME = "Translator";
    public static final int MODE = Context.MODE_PRIVATE;

    private SharedPref() {
    }

    public static void init(Context context) {
        if (mSharedPref == null) {
            mSharedPref = context.getSharedPreferences(context.getPackageName(), 0);
        }
    }

    public static void setBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static void setInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int getInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void setString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String getString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }
}
