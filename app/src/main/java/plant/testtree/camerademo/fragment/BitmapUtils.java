package plant.testtree.camerademo.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class BitmapUtils {
    private static Throwable r5;

    public static Bitmap addWhiteBorder(Bitmap bitmap, int i) {
        int i2 = i * 2;
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() + i2, bitmap.getHeight() + i2, bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        float f = i;
        canvas.drawBitmap(bitmap, f, f, (Paint) null);
        return createBitmap;
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            return Bitmap.createBitmap(bitmap, (bitmap.getWidth() / 2) - (bitmap.getHeight() / 2), 0, bitmap.getHeight(), bitmap.getHeight());
        }
        return Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() / 2) - (bitmap.getWidth() / 2), bitmap.getWidth(), bitmap.getWidth());
    }

    public static int getOrientation(Uri uri, Context context) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                if (openInputStream != null) {
                    openInputStream.close();
                }
                return 0;
            }
            int attributeInt = new ExifInterface(openInputStream).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                if (openInputStream != null) {
                    openInputStream.close();
                    return 180;
                }
                return 180;
            } else if (attributeInt == 6) {
                if (openInputStream != null) {
                    openInputStream.close();
                    return 90;
                }
                return 90;
            } else if (attributeInt != 8) {
                if (openInputStream != null) {
                    openInputStream.close();
                }
                return 0;
            } else if (openInputStream != null) {
                openInputStream.close();
                return 270;
            } else {
                return 270;
            }
        } catch (IOException unused) {
            return 0;
        } catch (Throwable unused2) {
            Throwable th = r5;
            try {
                throw null;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
}
