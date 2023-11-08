package plant.testtree.camerademo.filter.beautify;

import android.content.Context;
import android.opengl.GLES20;

import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;
import plant.testtree.camerademo.util.TextureUtils;


public class BeautifyFilterA extends SimpleFragmentShaderFilter {
    private float texelHeightOffset;
    private float texelWidthOffset;

    public BeautifyFilterA(Context context) {
        super(context, "filter/fsh/beautify/beautify_a.glsl");
        this.texelHeightOffset = 2.0f;
        this.texelWidthOffset = 2.0f;
    }

    @Override
    public void onDrawFrame(int i) {
        onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelWidthOffset", this.texelWidthOffset / this.surfaceWidth);
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelHeightOffset", this.texelHeightOffset / this.surfaceHeight);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public BeautifyFilterA setStepLength(int i) {
        float f = i;
        this.texelHeightOffset = f;
        this.texelWidthOffset = f;
        return this;
    }
}
