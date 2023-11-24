package com.iphonecamera.allinone.cameraediting.util;

import android.webkit.MimeTypeMap;


public class MimeTypeUtils {
    public static final String UNKNOWN_MIME_TYPE = "unknown/unknown";

    public static String getMimeType(String str) {
        int lastIndexOf;
        String mimeTypeFromExtension;
        return (str == null || (lastIndexOf = str.lastIndexOf(46)) == -1 || (mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(lastIndexOf + 1).toLowerCase())) == null) ? UNKNOWN_MIME_TYPE : mimeTypeFromExtension;
    }

    public static String getGenericMIME(String str) {
        return str.split("/")[0] + "/*";
    }

    public static String getTypeMime(String str) {
        return str.split("/")[0];
    }
}
