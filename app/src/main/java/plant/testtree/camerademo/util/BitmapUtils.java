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

/* loaded from: classes.dex */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";

    /* loaded from: classes.dex */
    private static class SaveBitmapTask extends AsyncTask<Void, Integer, Boolean> {
        Context context;
        String filePath;
        FileUtils.FileSavedCallback fileSavedCallback;
        int height;
        IntBuffer rgbaBuf;
        long start;
        int width;

        public SaveBitmapTask(IntBuffer intBuffer, int i, int i2, Context context, FileUtils.FileSavedCallback fileSavedCallback) {
            this.rgbaBuf = intBuffer;
            this.width = i;
            this.height = i2;
            this.context = context;
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "Camera");
            if (!file.exists()) {
                file.mkdirs();
            }
            this.filePath = file.getAbsolutePath() + FileUtils.getPicName();
            this.fileSavedCallback = fileSavedCallback;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            this.start = System.nanoTime();
            super.onPreExecute();
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voidArr) {
            BitmapUtils.saveIntBufferAsBitmap(this.rgbaBuf, this.filePath, this.width, this.height);
            return true;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean bool) {
            Log.d(BitmapUtils.TAG, "saveBitmap time: " + ((System.nanoTime() - this.start) / 1000000) + " ms");
            this.fileSavedCallback.onFileSaved(this.filePath);
        }
    }

    public static Bitmap getScreenShot(int i, int i2) {
        int i3 = i * i2;
        IntBuffer allocate = IntBuffer.allocate(i3);
        GLES20.glReadPixels(0, 0, i, i2, 6408, 5121, allocate);
        int[] iArr = new int[i3];
        int[] array = allocate.array();
        for (int i4 = 0; i4 < i2; i4++) {
            for (int i5 = 0; i5 < i; i5++) {
                iArr[(((i2 - i4) - 1) * i) + i5] = array[(i4 * i) + i5];
            }
        }
        return Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
    }

    public static void sendImage(int i, int i2, Context context, FileUtils.FileSavedCallback fileSavedCallback) {
        IntBuffer allocate = IntBuffer.allocate(i * i2);
        long nanoTime = System.nanoTime();
        GLES20.glReadPixels(0, 0, i, i2, 6408, 5121, allocate);
        long nanoTime2 = System.nanoTime();
        Log.d(TAG, "glReadPixels time: " + ((nanoTime2 - nanoTime) / 1000000) + " ms");
        new SaveBitmapTask(allocate, i, i2, context, fileSavedCallback).execute(new Void[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:90:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void saveIntBufferAsBitmap(IntBuffer r7, String r8, int r9, int r10) {
        /*
            mkDirs(r8)
            int r0 = r9 * r10
            int[] r0 = new int[r0]
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Creating "
            r1.append(r2)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "BitmapUtils"
            android.util.Log.d(r2, r1)
            r1 = 0
            int[] r7 = r7.array()     // Catch: java.io.IOException -> L73
            r2 = 0
            r3 = 0
        L24:
            if (r3 >= r10) goto L3d
            r4 = 0
        L27:
            if (r4 >= r9) goto L3a
            int r5 = r10 - r3
            int r5 = r5 + (-1)
            int r5 = r5 * r9
            int r5 = r5 + r4
            int r6 = r3 * r9
            int r6 = r6 + r4
            r6 = r7[r6]     // Catch: java.io.IOException -> L73
            r0[r5] = r6     // Catch: java.io.IOException -> L73
            int r4 = r4 + 1
            goto L27
        L3a:
            int r3 = r3 + 1
            goto L24
        L3d:
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch: java.io.IOException -> L73
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L73
            r2.<init>(r8)     // Catch: java.io.IOException -> L73
            r7.<init>(r2)     // Catch: java.io.IOException -> L73
            android.graphics.Bitmap$Config r8 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Throwable -> L5f
            android.graphics.Bitmap r8 = android.graphics.Bitmap.createBitmap(r9, r10, r8)     // Catch: java.lang.Throwable -> L5f
            java.nio.IntBuffer r9 = java.nio.IntBuffer.wrap(r0)     // Catch: java.lang.Throwable -> L5f
            r8.copyPixelsFromBuffer(r9)     // Catch: java.lang.Throwable -> L5f
            android.graphics.Bitmap$CompressFormat r9 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Throwable -> L5f
            r10 = 90
            r8.compress(r9, r10, r7)     // Catch: java.lang.Throwable -> L5f
            r8.recycle()     // Catch: java.lang.Throwable -> L5f
            goto L66
        L5f:
            r8 = move-exception
            throw r8     // Catch: java.lang.Throwable -> L61
        L61:
            r8 = move-exception
            r8.printStackTrace()     // Catch: java.io.IOException -> L6f
            r1 = r7
        L66:
            r7.close()     // Catch: java.io.IOException -> L6a
            goto L81
        L6a:
            r7 = move-exception
            r7.printStackTrace()     // Catch: java.io.IOException -> L73
            goto L81
        L6f:
            r8 = move-exception
            r1 = r7
            r7 = r8
            goto L74
        L73:
            r7 = move-exception
        L74:
            r7.printStackTrace()
            if (r1 != 0) goto L81
            r1.close()     // Catch: java.io.IOException -> L7d
            goto L81
        L7d:
            r7 = move-exception
            r7.printStackTrace()
        L81:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.cameraediter.iphone11pro.filter.util.BitmapUtils.saveIntBufferAsBitmap(java.nio.IntBuffer, java.lang.String, int, int):void");
    }

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

    public static void saveByteArray(final byte[] bArr, final String str, final IWorkerCallback iWorkerCallback, final Handler handler) {
        mkDirs(str);
        FakeThreadUtils.postTask(new Runnable() { // from class: com.cameraediter.iphone11pro.filter.util.BitmapUtils.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(str);
                    fileOutputStream.write(bArr);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    handler.post(new Runnable() { // from class: com.cameraediter.iphone11pro.filter.util.BitmapUtils.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (iWorkerCallback != null) {
                                iWorkerCallback.onPostExecute(null);
                            }
                        }
                    });
                } catch (Exception e) {
                    handler.post(new Runnable() { // from class: com.cameraediter.iphone11pro.filter.util.BitmapUtils.1.2
                        @Override // java.lang.Runnable
                        public void run() {
                            if (iWorkerCallback != null) {
                                iWorkerCallback.onPostExecute(e);
                            }
                        }
                    });
                }
            }
        });
    }

    public static void saveBitmapWithFilterApplied(final Context context, final FilterType filterType, final Bitmap bitmap, final String str, final IWorkerCallback iWorkerCallback) {
        FakeThreadUtils.postTask(new Runnable() { // from class: com.cameraediter.iphone11pro.filter.util.BitmapUtils.2
            @Override // java.lang.Runnable
            public void run() {
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
            }
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

    /* JADX WARN: Removed duplicated region for block: B:73:0x0035 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void savePNGBitmap(Bitmap r3, String r4) {
        /*
            mkDirs(r4)
            r0 = 0
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch: java.io.IOException -> L2f
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L2f
            r2.<init>(r4)     // Catch: java.io.IOException -> L2f
            r1.<init>(r2)     // Catch: java.io.IOException -> L2f
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.PNG     // Catch: java.lang.Throwable -> L1b
            r2 = 100
            r3.compress(r4, r2, r1)     // Catch: java.lang.Throwable -> L1b
            r1.close()     // Catch: java.io.IOException -> L19 java.lang.Throwable -> L1b
            return
        L19:
            r3 = move-exception
            goto L23
        L1b:
            r3 = move-exception
            throw r3     // Catch: java.lang.Throwable -> L1d
        L1d:
            r3 = move-exception
            r3.printStackTrace()     // Catch: java.io.IOException -> L2c
            r3 = r0
            r0 = r1
        L23:
            r3.printStackTrace()     // Catch: java.lang.Exception -> L27
            goto L38
        L27:
            r3 = move-exception
            r3.printStackTrace()     // Catch: java.io.IOException -> L2f
            goto L38
        L2c:
            r3 = move-exception
            r0 = r1
            goto L30
        L2f:
            r3 = move-exception
        L30:
            r3.printStackTrace()
            if (r0 != 0) goto L38
            r0.close()     // Catch: java.io.IOException -> L38
        L38:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.cameraediter.iphone11pro.filter.util.BitmapUtils.savePNGBitmap(android.graphics.Bitmap, java.lang.String):void");
    }

    public static Bitmap loadARGBBitmapFromRGBAByteArray(byte[] bArr, int i, int i2, boolean z) {
        int i3 = i * i2;
        if (bArr.length == i3 * 4) {
            int[] iArr = new int[i3];
            int i4 = 0;
            int i5 = 0;
            while (i4 < i3) {
                iArr[i4] = 0;
                iArr[i4] = iArr[i4] | ((bArr[i5 + 0] & UByte.MAX_VALUE) << 16);
                iArr[i4] = iArr[i4] | ((bArr[i5 + 1] & UByte.MAX_VALUE) << 8);
                iArr[i4] = iArr[i4] | ((bArr[i5 + 2] & UByte.MAX_VALUE) << 0);
                iArr[i4] = iArr[i4] | (z ? ViewCompat.MEASURED_STATE_MASK : (bArr[i5 + 3] & UByte.MAX_VALUE) << 24);
                i4++;
                i5 += 4;
            }
            return Bitmap.createBitmap(iArr, i, i2, Bitmap.Config.ARGB_8888);
        }
        throw new RuntimeException("Illegal argument");
    }
}
