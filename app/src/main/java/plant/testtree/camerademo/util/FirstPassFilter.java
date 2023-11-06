package plant.testtree.camerademo.util;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import plant.testtree.camerademo.filter.GLPassThroughProgram;

/* loaded from: classes.dex */
public class FirstPassFilter extends AbsFilter {
    protected Context context;
    protected GLPassThroughProgram glPassThroughProgram;
    protected float[] projectionMatrix = new float[16];
    private Plane plane = new Plane(false);

    public FirstPassFilter(Context context) {
        this.context = context;
        this.glPassThroughProgram = new GLPassThroughProgram(context);
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        this.glPassThroughProgram.create();
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void destroy() {
        this.glPassThroughProgram.onDestroy();
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onDrawFrame(int i) {
        onPreDrawElements();
        this.glPassThroughProgram.use();
        Matrix.setIdentityM(this.projectionMatrix, 0);
        this.plane.uploadTexCoordinateBuffer(this.glPassThroughProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glPassThroughProgram.getPositionHandle());
        GLES20.glUniformMatrix4fv(this.glPassThroughProgram.getMVPMatrixHandle(), 1, false, this.projectionMatrix, 0);
        TextureUtils.bindTexture2D(i, 33984, this.glPassThroughProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onFilterChanged(int i, int i2) {
        super.onFilterChanged(i, i2);
    }
}
