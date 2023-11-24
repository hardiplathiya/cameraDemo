package com.iphonecamera.allinone.cameraediting.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;


public class LegacyCompatFileProvider extends FileProvider {
    @Override
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return new LegacyCompatCursorWrapper(super.query(uri, strArr, str, strArr2, str2));
    }

    public static Uri getUri(Context context, File file) {
        return getUriForFile(context, ApplicationUtils.getPackageName() + ".provider", file);
    }
}
