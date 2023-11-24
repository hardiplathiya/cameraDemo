package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;
import android.opengl.GLES20;

import com.iphonecamera.allinone.cameraediting.util.BitmapTexture;
import com.iphonecamera.allinone.cameraediting.util.MatrixUtils;
import com.iphonecamera.allinone.cameraediting.util.Plane;
import com.iphonecamera.allinone.cameraediting.util.TextureUtils;


public class DrawImageFilter extends PassThroughFilter {
    private BitmapTexture bitmapTexture;
    private String imagePath;
    private Plane imagePlane;

    public DrawImageFilter(Context context, String str) {
        super(context);
        this.bitmapTexture = new BitmapTexture();
        this.imagePath = str;
        this.imagePlane = new Plane(false);
    }

    @Override 
    public void init() {
        super.init();
        this.bitmapTexture.load(this.context, this.imagePath);
    }

    @Override 
    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(770, 771);
        TextureUtils.bindTexture2D(this.bitmapTexture.getImageTextureId(), 33984, this.glPassThroughProgram.getTextureSamplerHandle(), 0);
        this.imagePlane.uploadTexCoordinateBuffer(this.glPassThroughProgram.getTextureCoordinateHandle());
        this.imagePlane.uploadVerticesBuffer(this.glPassThroughProgram.getPositionHandle());
        MatrixUtils.updateProjectionFit(this.bitmapTexture.getImageWidth(), this.bitmapTexture.getImageHeight(), this.surfaceWidth, this.surfaceHeight, this.projectionMatrix);
        GLES20.glUniformMatrix4fv(this.glPassThroughProgram.getMVPMatrixHandle(), 1, false, this.projectionMatrix, 0);
        this.imagePlane.draw();
        GLES20.glDisable(3042);
    }

    @Override 
    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
    }
}
