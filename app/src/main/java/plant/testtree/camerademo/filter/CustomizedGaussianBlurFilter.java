package plant.testtree.camerademo.filter;

import android.opengl.GLES20;

import plant.testtree.camerademo.util.AbsFilter;
import plant.testtree.camerademo.util.GLSimpleProgram;
import plant.testtree.camerademo.util.TextureUtils;

/* loaded from: classes.dex */
public class CustomizedGaussianBlurFilter extends AbsFilter {
    protected GLSimpleProgram glSimpleProgram;
    private boolean scale = false;
    private float texelHeightOffset = 0.0f;
    private float texelWidthOffset = 0.0f;

    public CustomizedGaussianBlurFilter(int i, double d) {
        this.glSimpleProgram = new GLSimpleProgram(generateCustomizedGaussianBlurVertexShader(i, d), generateCustomizedGaussianBlurFragmentShader(i, d));
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
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelWidthOffset", this.texelWidthOffset / this.surfaceWidth);
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelHeightOffset", this.texelHeightOffset / this.surfaceHeight);
        TextureUtils.bindTexture2D(i, 33984, this.glSimpleProgram.getTextureSamplerHandle(), 0);
        GLES20.glViewport(0, 0, this.surfaceWidth, this.surfaceHeight);
        this.plane.draw();
    }

    public CustomizedGaussianBlurFilter setTexelHeightOffset(float f) {
        this.texelHeightOffset = f;
        return this;
    }

    public CustomizedGaussianBlurFilter setTexelWidthOffset(float f) {
        this.texelWidthOffset = f;
        return this;
    }

    @Override // com.cameraediter.iphone11pro.filter.base.AbsFilter
    public void onFilterChanged(int i, int i2) {
        if (!this.scale) {
            super.onFilterChanged(i, i2);
        } else {
            super.onFilterChanged(i / 4, i2 / 4);
        }
    }

    public CustomizedGaussianBlurFilter setScale(boolean z) {
        this.scale = z;
        return this;
    }

    private static String generateCustomizedGaussianBlurVertexShader(int i, double d) {
        int i2;
        double d2;
        if (i < 1) {
            return "";
        }
        double[] dArr = new double[i + 2];
        double d3 = 0.0d;
        int i3 = 0;
        while (true) {
            i2 = i + 1;
            if (i3 >= i2) {
                break;
            }
            dArr[i3] = (1.0d / Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d)) * Math.exp((-Math.pow(i3, 2.0d)) / (Math.pow(d, 2.0d) * 2.0d));
            if (i3 == 0) {
                d2 = dArr[i3];
            } else {
                d2 = dArr[i3] * 2.0d;
            }
            d3 += d2;
            i3++;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            dArr[i4] = dArr[i4] / d3;
        }
        int min = Math.min((i / 2) + (i % 2), 7);
        double[] dArr2 = new double[min];
        for (int i5 = 0; i5 < min; i5++) {
            int i6 = i5 * 2;
            int i7 = i6 + 1;
            double d4 = dArr[i7];
            int i8 = i6 + 2;
            double d5 = dArr[i8];
            double d6 = i7;
            Double.isNaN(d6);
            double d7 = i8;
            Double.isNaN(d7);
            dArr2[i5] = ((d6 * d4) + (d7 * d5)) / (d4 + d5);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\nvarying vec2 blurCoordinates[%d];\nvoid main(){\n\tgl_Position = aPosition;\n\tvec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n", Integer.valueOf((min * 2) + 1)));
        sb.append("\tblurCoordinates[0] = aTextureCoord.xy;\n");
        for (int i9 = 0; i9 < min; i9++) {
            int i10 = i9 * 2;
            sb.append(String.format("\tblurCoordinates[%d] = aTextureCoord.xy + singleStepOffset * %f;\n\tblurCoordinates[%d] = aTextureCoord.xy - singleStepOffset * %f;\n", Integer.valueOf(i10 + 1), Double.valueOf(dArr2[i9]), Integer.valueOf(i10 + 2), Double.valueOf(dArr2[i9])));
        }
        sb.append("}\n");
        return sb.toString();
    }

    private static String generateCustomizedGaussianBlurFragmentShader(int i, double d) {
        int i2;
        double d2;
        int i3 = 1;
        if (i < 1) {
            return "";
        }
        double[] dArr = new double[i + 2];
        double d3 = 0.0d;
        int i4 = 0;
        while (true) {
            i2 = i + 1;
            if (i4 >= i2) {
                break;
            }
            dArr[i4] = (1.0d / Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d)) * Math.exp((-Math.pow(i4, 2.0d)) / (Math.pow(d, 2.0d) * 2.0d));
            if (i4 == 0) {
                d2 = dArr[i4];
            } else {
                d2 = dArr[i4] * 2.0d;
            }
            d3 += d2;
            i4++;
            i3 = 1;
        }
        for (int i5 = 0; i5 < i2; i5++) {
            dArr[i5] = dArr[i5] / d3;
        }
        int i6 = 2;
        int i7 = (i / 2) + (i % 2);
        int min = Math.min(i7, 7);
        StringBuilder sb = new StringBuilder();
        sb.append("uniform sampler2D sTexture;\nuniform highp float texelWidthOffset;\nuniform highp float texelHeightOffset;\n");
        Object[] objArr = new Object[i3];
        objArr[0] = Integer.valueOf((min * 2) + i3);
        sb.append(String.format("varying highp vec2 blurCoordinates[%d];\n", objArr));
        sb.append("void main(){\n");
        sb.append("\tlowp vec4 sum = vec4(0.0);\n");
        Object[] objArr2 = new Object[i3];
        objArr2[0] = Double.valueOf(dArr[0]);
        sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[0]) * %f;\n", objArr2));
        for (int i8 = 0; i8 < min; i8++) {
            int i9 = i8 * 2;
            int i10 = i9 + 1;
            int i11 = i9 + 2;
            double d4 = dArr[i10] + dArr[i11];
            Object[] objArr3 = new Object[4];
            objArr3[0] = Integer.valueOf(i10);
            objArr3[i3] = Double.valueOf(d4);
            objArr3[2] = Integer.valueOf(i11);
            objArr3[3] = Double.valueOf(d4);
            sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[%d]) * %f;\n\tsum += texture2D(sTexture, blurCoordinates[%d]) * %f;\n", objArr3));
        }
        if (i7 > min) {
            sb.append("\thighp vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n");
            while (min < i7) {
                int i12 = min * 2;
                int i13 = i12 + 1;
                double d5 = dArr[i13];
                int i14 = i12 + i6;
                double d6 = dArr[i14];
                double d7 = d5 + d6;
                double d8 = i13;
                Double.isNaN(d8);
                double d9 = d5 * d8;
                double d10 = i14;
                Double.isNaN(d10);
                double d11 = (d9 + (d6 * d10)) / d7;
                sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[0] + singleStepOffset * %f) * %f;\n\tsum += texture2D(sTexture, blurCoordinates[0] - singleStepOffset * %f) * %f;\n", Double.valueOf(d11), Double.valueOf(d7), Double.valueOf(d11), Double.valueOf(d7)));
                min++;
                i6 = 2;
            }
        }
        sb.append("\tgl_FragColor = sum;\n");
        sb.append("}\n");
        return sb.toString();
    }

    public static CustomizedGaussianBlurFilter initWithBlurRadiusInPixels(int i) {
        int i2;
        if (i >= 1) {
            double d = i;
            int floor = (int) Math.floor(Math.sqrt(Math.pow(d, 2.0d) * (-2.0d) * Math.log(Math.sqrt(Math.pow(d, 2.0d) * 6.283185307179586d) * 0.00390625d)));
            i2 = floor + (floor % 2);
        } else {
            i2 = 0;
        }
        return new CustomizedGaussianBlurFilter(i2, i);
    }
}
