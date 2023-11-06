package plant.testtree.camerademo.util;

import android.content.Context;

/* loaded from: classes.dex */
public class ApplicationUtils {
    private static String PACKAGE_NAME;

    public static String getAppVersion() {
        return "1.0";
    }

    public static boolean isDebug() {
        return false;
    }

    public static void init(Context context) {
        PACKAGE_NAME = context.getPackageName();
    }

    public static String getPackageName() {
        return PACKAGE_NAME;
    }
}
