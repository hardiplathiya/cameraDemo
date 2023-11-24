package com.iphonecamera.allinone.cameraediting.util;

import android.content.Context;
import android.content.SharedPreferences;

final class SharedPrefs {
    private static final int PREFERENCES_MODE = 0;
    private static final String PREFERENCES_NAME = "com.editphotogallery.photovideogallery.SHARED_PREFS";
    private final SharedPreferences sharedPrefs;

    public SharedPrefs(Context context) {
        this.sharedPrefs = context.getApplicationContext().getSharedPreferences(PREFERENCES_NAME, 0);
    }

    private SharedPreferences.Editor getEditor() {
        return this.sharedPrefs.edit();
    }

    public int get(String str, int i) {
        return this.sharedPrefs.getInt(str, i);
    }

    public void put(String str, int i) {
        getEditor().putInt(str, i).commit();
    }

    public boolean get(String str, boolean z) {
        return this.sharedPrefs.getBoolean(str, z);
    }

    public void put(String str, boolean z) {
        getEditor().putBoolean(str, z).commit();
    }
}
