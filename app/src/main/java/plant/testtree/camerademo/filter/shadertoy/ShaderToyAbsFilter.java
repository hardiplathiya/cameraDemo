package plant.testtree.camerademo.filter.shadertoy;

import android.content.Context;
import android.opengl.GLES20;
import java.nio.FloatBuffer;

import plant.testtree.camerademo.filter.MultipleTextureFilter;
import plant.testtree.camerademo.util.TextureUtils;


public class ShaderToyAbsFilter extends MultipleTextureFilter {
    private final long START_TIME;

    public ShaderToyAbsFilter(Context context, String str) {
        super(context, str);
        this.START_TIME = System.currentTimeMillis();
    }

    @Override 
    public void onDrawFrame(int i) {
        onPreDrawElements();
        GLES20.glUniform3fv(GLES20.glGetUniformLocation(this.glSimpleProgram.getProgramId(), "iResolution"), 1, FloatBuffer.wrap(new float[]{this.surfaceWidth, this.surfaceHeight, 1.0f}));
        setUniform1f(this.glSimpleProgram.getProgramId(), "iGlobalTime", ((float) (System.currentTimeMillis() - this.START_TIME)) / 1000.0f);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}
