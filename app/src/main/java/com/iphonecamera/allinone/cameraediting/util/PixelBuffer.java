package com.iphonecamera.allinone.cameraediting.util;

import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.util.Log;
import com.drew.metadata.exif.makernotes.FujifilmMakernoteDirectory;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;


public class PixelBuffer {
    static final boolean LIST_CONFIGS = false;
    static final String TAG = "PixelBuffer";
    Bitmap mBitmap;
    EGLConfig mEGLConfig;
    EGLConfig[] mEGLConfigs;
    EGLContext mEGLContext;
    EGLSurface mEGLSurface;
    GL10 mGL;
    int mHeight;
    GLSurfaceView.Renderer mRenderer;
    String mThreadOwner;
    int mWidth;
    EGL10 mEGL = (EGL10) EGLContext.getEGL();
    EGLDisplay mEGLDisplay = this.mEGL.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

    public PixelBuffer(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        int[] iArr = {12375, this.mWidth, 12374, this.mHeight, 12344};
        this.mEGL.eglInitialize(this.mEGLDisplay, new int[2]);
        this.mEGLConfig = chooseConfig();
        this.mEGLContext = this.mEGL.eglCreateContext(this.mEGLDisplay, this.mEGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
        this.mEGLSurface = this.mEGL.eglCreatePbufferSurface(this.mEGLDisplay, this.mEGLConfig, iArr);
        EGL10 egl10 = this.mEGL;
        EGLDisplay eGLDisplay = this.mEGLDisplay;
        EGLSurface eGLSurface = this.mEGLSurface;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.mEGLContext);
        this.mGL = (GL10) this.mEGLContext.getGL();
        this.mThreadOwner = Thread.currentThread().getName();
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        this.mRenderer = renderer;
        if (!Thread.currentThread().getName().equals(this.mThreadOwner)) {
            Log.e(TAG, "setRenderer: This thread does not own the OpenGL context.");
            return;
        }
        this.mRenderer.onSurfaceCreated(this.mGL, this.mEGLConfig);
        this.mRenderer.onSurfaceChanged(this.mGL, this.mWidth, this.mHeight);
    }

    public Bitmap getBitmap() {
        if (this.mRenderer == null) {
            Log.e(TAG, "getBitmap: Renderer was not set.");
            return null;
        } else if (!Thread.currentThread().getName().equals(this.mThreadOwner)) {
            Log.e(TAG, "getBitmap: This thread does not own the OpenGL context.");
            return null;
        } else {
            this.mRenderer.onDrawFrame(this.mGL);
            this.mRenderer.onDrawFrame(this.mGL);
            convertToBitmap();
            return this.mBitmap;
        }
    }

    public void destroy() {
        this.mRenderer.onDrawFrame(this.mGL);
        this.mRenderer.onDrawFrame(this.mGL);
        this.mEGL.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        this.mEGL.eglDestroySurface(this.mEGLDisplay, this.mEGLSurface);
        this.mEGL.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
        this.mEGL.eglTerminate(this.mEGLDisplay);
    }

    private EGLConfig chooseConfig() {
        int[] iArr = new int[1];
        int[] iArr2 = {12325, 0, 12326, 0, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344};
        this.mEGL.eglChooseConfig(this.mEGLDisplay, iArr2, null, 0, iArr);
        int i = iArr[0];
        this.mEGLConfigs = new EGLConfig[i];
        this.mEGL.eglChooseConfig(this.mEGLDisplay, iArr2, this.mEGLConfigs, i, iArr);
        return this.mEGLConfigs[0];
    }

    private void listConfig() {
        EGLConfig[] eGLConfigArr;
        Log.i(TAG, "Config List {");
        for (EGLConfig eGLConfig : this.mEGLConfigs) {
            Log.i(TAG, "    <d,s,r,g,b,a> = <" + getConfigAttrib(eGLConfig, 12325) + "," + getConfigAttrib(eGLConfig, 12326) + "," + getConfigAttrib(eGLConfig, 12324) + "," + getConfigAttrib(eGLConfig, 12323) + "," + getConfigAttrib(eGLConfig, 12322) + "," + getConfigAttrib(eGLConfig, 12321) + ">");
        }
        Log.i(TAG, "}");
    }

    private int getConfigAttrib(EGLConfig eGLConfig, int i) {
        int[] iArr = new int[1];
        if (this.mEGL.eglGetConfigAttrib(this.mEGLDisplay, eGLConfig, i, iArr)) {
            return iArr[0];
        }
        return 0;
    }

    private void convertToBitmap() {
        int i = this.mWidth * this.mHeight;
        int[] iArr = new int[i];
        IntBuffer allocate = IntBuffer.allocate(i);
        this.mGL.glReadPixels(0, 0, this.mWidth, this.mHeight, 6408, FujifilmMakernoteDirectory.TAG_FILM_MODE, allocate);
        int[] array = allocate.array();
        int i2 = 0;
        while (true) {
            int i3 = this.mHeight;
            if (i2 >= i3) {
                this.mBitmap = Bitmap.createBitmap(this.mWidth, i3, Bitmap.Config.ARGB_8888);
                this.mBitmap.copyPixelsFromBuffer(IntBuffer.wrap(iArr));
                return;
            }
            int i4 = 0;
            while (true) {
                int i5 = this.mWidth;
                if (i4 >= i5) {
                    break;
                }
                iArr[(((this.mHeight - i2) - 1) * i5) + i4] = array[(i5 * i2) + i4];
                i4++;
            }
            i2++;
        }
    }
}
