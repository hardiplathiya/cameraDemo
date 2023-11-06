package plant.testtree.camerademo.filter;

import android.content.Context;
import android.opengl.GLES20;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;
import plant.testtree.camerademo.util.TextureUtils;

/* loaded from: classes.dex */
public class MxFaceBeautyFilter extends SimpleFragmentShaderFilter {
    public MxFaceBeautyFilter(Context context) {
        super(context, "filter/fsh/mx/mx_face_beauty.glsl");
    }

    @Override // com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "stepSizeX", 1.0f / this.surfaceWidth);
        setUniform1f(this.glSimpleProgram.getProgramId(), "stepSizeY", 1.0f / this.surfaceHeight);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}
