package com.iphonecamera.allinone.cameraediting.filter.imgproc;

import android.opengl.GLES20;

import com.iphonecamera.allinone.cameraediting.filter.AbsFilter;
import com.iphonecamera.allinone.cameraediting.util.GLSimpleProgram;
import com.iphonecamera.allinone.cameraediting.util.TextureUtils;


public class CustomizedBoxBlurFilter extends AbsFilter {
    protected GLSimpleProgram glSimpleProgram;
    private boolean scale = false;
    private float texelHeightOffset = 0.0f;
    private float texelWidthOffset = 0.0f;

    public CustomizedBoxBlurFilter(int i) {
        this.glSimpleProgram = new GLSimpleProgram(generateCustomizedBoxBlurVertexShader(i), generateCustomizedBoxBlurFragmentShader(i));
    }

    @Override
    public void init() {
        this.glSimpleProgram.create();
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        this.glSimpleProgram.use();
        this.plane.uploadTexCoordinateBuffer(this.glSimpleProgram.getTextureCoordinateHandle());
        this.plane.uploadVerticesBuffer(this.glSimpleProgram.getPositionHandle());
    }

    @Override 
    public void destroy() {
        this.glSimpleProgram.onDestroy();
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

    public CustomizedBoxBlurFilter setTexelHeightOffset(float f) {
        this.texelHeightOffset = f;
        return this;
    }

    public CustomizedBoxBlurFilter setTexelWidthOffset(float f) {
        this.texelWidthOffset = f;
        return this;
    }

    @Override 
    public void onFilterChanged(int i, int i2) {
        if (!this.scale) {
            super.onFilterChanged(i, i2);
        } else {
            super.onFilterChanged(i / 4, i2 / 4);
        }
    }

    public CustomizedBoxBlurFilter setScale(boolean z) {
        this.scale = z;
        return this;
    }

    public static String generateCustomizedBoxBlurVertexShader(int i) {
        if (i < 1) {
            return "";
        }
        int min = Math.min((i / 2) + (i % 2), 7);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("attribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\nvarying vec2 blurCoordinates[%d];\nvoid main(){\n\tgl_Position = aPosition;\n\tvec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n", Integer.valueOf((min * 2) + 1)));
        sb.append("\tblurCoordinates[0] = aTextureCoord.xy;\n");
        for (int i2 = 0; i2 < min; i2++) {
            int i3 = i2 * 2;
            double d = i3;
            Double.isNaN(d);
            double d2 = d + 1.5d;
            sb.append(String.format("\tblurCoordinates[%d] = aTextureCoord.xy + singleStepOffset * %f;\n\tblurCoordinates[%d] = aTextureCoord.xy - singleStepOffset * %f;\n", Integer.valueOf(i3 + 1), Double.valueOf(d2), Integer.valueOf(i3 + 2), Double.valueOf(d2)));
        }
        sb.append("}\n");
        return sb.toString();
    }

    public static String generateCustomizedBoxBlurFragmentShader(int i) {
        if (i < 1) {
            return "";
        }
        int i2 = (i / 2) + (i % 2);
        int min = Math.min(i2, 7);
        StringBuilder sb = new StringBuilder();
        double d = (i * 2) + 1;
        Double.isNaN(d);
        double d2 = 1.0d / d;
        sb.append("uniform sampler2D sTexture;\nuniform highp float texelWidthOffset;\nuniform highp float texelHeightOffset;\n");
        sb.append(String.format("varying highp vec2 blurCoordinates[%d];\n", Integer.valueOf((min * 2) + 1)));
        sb.append("void main(){\n");
        sb.append("\tlowp vec4 sum = vec4(0.0);\n");
        sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[0]) * %f;\n", Double.valueOf(d2)));
        for (int i3 = 0; i3 < min; i3++) {
            int i4 = i3 * 2;
            double d3 = 2.0d * d2;
            sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[%d]) * %f;\n\tsum += texture2D(sTexture, blurCoordinates[%d]) * %f;\n", Integer.valueOf(i4 + 1), Double.valueOf(d3), Integer.valueOf(i4 + 2), Double.valueOf(d3)));
        }
        if (i2 > min) {
            sb.append("\thighp vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\n");
            while (min < i2) {
                double d4 = min * 2;
                Double.isNaN(d4);
                double d5 = d4 + 1.5d;
                double d6 = d2 * 2.0d;
                sb.append(String.format("\tsum += texture2D(sTexture, blurCoordinates[0] + singleStepOffset * %f) * %f;\n\tsum += texture2D(sTexture, blurCoordinates[0] - singleStepOffset * %f) * %f;\n", Double.valueOf(d5), Double.valueOf(d6), Double.valueOf(d5), Double.valueOf(d6)));
                min++;
            }
        }
        sb.append("\tgl_FragColor = sum;\n");
        sb.append("}\n");
        return sb.toString();
    }
}
