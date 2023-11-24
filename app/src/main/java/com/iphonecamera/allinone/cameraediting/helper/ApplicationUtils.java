package com.iphonecamera.allinone.cameraediting.helper;

import android.content.Context;


public class ApplicationUtils {
    private static String PACKAGE_NAME;

    public static void init(Context context) {
        PACKAGE_NAME = context.getPackageName();
    }

    public static String getPackageName() {
        return PACKAGE_NAME;
    }
}
