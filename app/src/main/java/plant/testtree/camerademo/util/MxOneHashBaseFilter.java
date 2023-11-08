package plant.testtree.camerademo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;


public class MxOneHashBaseFilter extends SimpleFragmentShaderFilter {
    private static final int HISTOGRAM_SIZE = 256;
    public static int[] rgbMap;
    private BitmapTexture bitmapTexture;
    private int[] mHistogram;
    private int uTextureSamplerHandle2;

    public MxOneHashBaseFilter(Context context, String str) {
        super(context, str);
        this.mHistogram = new int[256];
    }

    @Override 
    public void init() {
        super.init();
        for (int i = 0; i < 256; i++) {
            this.mHistogram[i] = rgbMap[i] << 24;
        }
        this.bitmapTexture = new BitmapTexture();
        this.bitmapTexture.loadBitmap(Bitmap.createBitmap(this.mHistogram, 256, 1, Bitmap.Config.ARGB_8888));
        this.uTextureSamplerHandle2 = GLES20.glGetUniformLocation(this.glSimpleProgram.getProgramId(), "sTexture2");
        ShaderUtils.checkGlError("glGetUniformLocation sTexture2");
    }

    @Override 
    public void onDrawFrame(int i) {
        onPreDrawElements();
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        TextureUtils.bindTexture2D(this.bitmapTexture.getImageTextureId(), 33985, this.uTextureSamplerHandle2, 1);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }
}
