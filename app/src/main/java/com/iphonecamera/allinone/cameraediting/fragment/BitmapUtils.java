package com.iphonecamera.allinone.cameraediting.fragment;

import android.content.Context;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.io.InputStream;


public class BitmapUtils {
    public static int getOrientation(Uri uri, Context context) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                return 0;
            }
            int attributeInt = new ExifInterface(openInputStream).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                openInputStream.close();
                return 180;
            } else if (attributeInt == 6) {
                openInputStream.close();
                return 90;
            } else if (attributeInt != 8) {
                openInputStream.close();
                return 0;
            } else {
                openInputStream.close();
                return 270;
            }
        } catch (IOException unused) {
            return 0;
        } catch (Throwable unused2) {
            try {
                throw null;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}
