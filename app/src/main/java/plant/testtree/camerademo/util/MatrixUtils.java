package plant.testtree.camerademo.util;

import android.opengl.Matrix;

/* loaded from: classes.dex */
public class MatrixUtils {
    public static void updateProjectionFit(int i, int i2, int i3, int i4, float[] fArr) {
        float f = i3 / i4;
        float f2 = i / i2;
        if (f2 > f) {
            Matrix.orthoM(fArr, 0, -1.0f, 1.0f, (-f2) / f, f2 / f, -1.0f, 1.0f);
        } else {
            Matrix.orthoM(fArr, 0, (-f) / f2, f / f2, -1.0f, 1.0f, -1.0f, 1.0f);
        }
    }

    public static void updateProjectionCrop(int i, int i2, int i3, int i4, float[] fArr) {
        float f = i3 / i4;
        float f2 = i / i2;
        if (f2 < f) {
            Matrix.orthoM(fArr, 0, -1.0f, 1.0f, (-f2) / f, f2 / f, -1.0f, 1.0f);
        } else {
            Matrix.orthoM(fArr, 0, (-f) / f2, f / f2, -1.0f, 1.0f, -1.0f, 1.0f);
        }
    }
}
