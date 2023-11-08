package plant.testtree.camerademo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import androidx.core.view.ViewCompat;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import kotlin.UByte;
import plant.testtree.camerademo.adapter.FilterType;


public class BitmapUtils {

    public static void saveBitmap(Bitmap bitmap, String str, IWorkerCallback iWorkerCallback) {
        mkDirs(str);
        try {
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bufferedOutputStream);
                try {
                    bufferedOutputStream.close();
                } catch (IOException unused) {
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    th2.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (iWorkerCallback == null) {
            iWorkerCallback.onPostExecute(null);
        }
    }

    public static void saveBitmapWithFilterApplied(final Context context, final FilterType filterType, final Bitmap bitmap, final String str, final IWorkerCallback iWorkerCallback) {
        FakeThreadUtils.postTask(() -> {
            Logger.updateCurrentTime();
            GLImageRender gLImageRender = new GLImageRender(context, bitmap, filterType);
            PixelBuffer pixelBuffer = new PixelBuffer(bitmap.getWidth(), bitmap.getHeight());
            Logger.logPassedTime("new PixelBuffer");
            pixelBuffer.setRenderer(gLImageRender);
            Bitmap bitmap2 = pixelBuffer.getBitmap();
            bitmap.recycle();
            Logger.logPassedTime("getBitmap");
            pixelBuffer.destroy();
            BitmapUtils.saveBitmap(bitmap2, str, iWorkerCallback);
            bitmap2.recycle();
            System.gc();
        });
    }

    public static Bitmap loadBitmapFromFile(String str) {
        new BitmapFactory.Options().inScaled = false;
        return BitmapFactory.decodeFile(str);
    }

    public static Bitmap loadBitmapFromAssets(Context context, String str) {
        InputStream inputStream;
        try {
            inputStream = context.getResources().getAssets().open(str);
        } catch (IOException e) {
            e.printStackTrace();
            inputStream = null;
        }
        if (inputStream == null) {
            return null;
        }
        new BitmapFactory.Options().inScaled = false;
        return BitmapFactory.decodeStream(inputStream);
    }

    public static Bitmap loadBitmapFromRaw(Context context, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        return BitmapFactory.decodeResource(context.getResources(), i, options);
    }

    public static void mkDirs(String str) {
        File file = new File(str);
        if (file.getParentFile().exists()) {
            return;
        }
        file.getParentFile().mkdirs();
    }

}
