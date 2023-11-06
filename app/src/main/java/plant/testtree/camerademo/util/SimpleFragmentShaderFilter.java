package plant.testtree.camerademo.util;

import android.content.Context;
import android.opengl.GLES20;

/* loaded from: classes.dex */
public class SimpleFragmentShaderFilter extends AbsFilter {
    protected GLSimpleProgram glSimpleProgram;

    public SimpleFragmentShaderFilter(Context context, String str) {
        this.glSimpleProgram = new GLSimpleProgram(context, "filter/vsh/base/simple.glsl", str);
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void init() {
        this.glSimpleProgram.create();
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onPreDrawElements() {
        super.onPreDrawElements();
        this.glSimpleProgram.use();
        this.plane.uploadTexCoordinateBuffer(this.glSimpleProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glSimpleProgram.getPositionHandle());
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void destroy() {
        this.glSimpleProgram.onDestroy();
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onDrawFrame(int i) {
        onPreDrawElements();
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}
