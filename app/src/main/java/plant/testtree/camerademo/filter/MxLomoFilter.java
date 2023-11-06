package plant.testtree.camerademo.filter;

import android.content.Context;
import android.opengl.GLES20;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;
import plant.testtree.camerademo.util.TextureUtils;

/* loaded from: classes.dex */
public class MxLomoFilter extends SimpleFragmentShaderFilter {
    public MxLomoFilter(Context context) {
        super(context, "filter/fsh/mx/mx_lomo.glsl");
    }

    @Override // com.cameraediter.iphone11pro.filter.base.SimpleFragmentShaderFilter, com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "rOffset", 1.0f);
        setUniform1f(this.glSimpleProgram.getProgramId(), "gOffset", 1.0f);
        setUniform1f(this.glSimpleProgram.getProgramId(), "bOffset", 1.0f);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}
