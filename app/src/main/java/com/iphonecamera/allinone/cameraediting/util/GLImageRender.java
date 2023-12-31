package com.iphonecamera.allinone.cameraediting.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.iphonecamera.allinone.cameraediting.filter.AbsFilter;
import com.iphonecamera.allinone.cameraediting.adapter.FilterType;


public class GLImageRender implements GLSurfaceView.Renderer {
    private Bitmap bitmap;
    private BitmapTexture bitmapTexture = new BitmapTexture();
    private Context context;
    private AbsFilter filter;

    public GLImageRender(Context context, Bitmap bitmap, FilterType filterType) {
        this.context = context;
        this.filter = FilterFactory.createFilter(filterType, context).resetPlane(false);
        this.bitmap = bitmap;
    }

    @Override 
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.filter.init();
        this.bitmapTexture.loadBitmap(this.bitmap);
    }

    @Override 
    public void onDrawFrame(GL10 gl10) {
        this.filter.onDrawFrame(this.bitmapTexture.getImageTextureId());
    }

    @Override 
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        GLES20.glViewport(0, 0, i, i2);
        this.filter.onFilterChanged(i, i2);
    }
}
