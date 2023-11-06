package plant.testtree.camerademo.filter;

import android.opengl.GLES20;
import android.opengl.Matrix;
import com.otaliastudios.cameraview.CameraLogger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes2.dex */
public class GlUtils {
    private static final String TAG = GlUtils.class.getSimpleName();
    private static final CameraLogger LOG = CameraLogger.create(TAG);
    public static final float[] IDENTITY_MATRIX = new float[16];

    static {
        Matrix.setIdentityM(IDENTITY_MATRIX, 0);
    }

    public static void checkError(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            throw new RuntimeException(LOG.e("Error during", str, "glError 0x", Integer.toHexString(glGetError)));
        }
    }

    public static void checkLocation(int i, String str) {
        if (i < 0) {
            throw new RuntimeException(LOG.e("Unable to locate", str, "in program"));
        }
    }

    public static int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        checkError("glCreateShader type=" + i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            LOG.e("Could not compile shader", Integer.valueOf(i), ":", GLES20.glGetShaderInfoLog(glCreateShader));
            GLES20.glDeleteShader(glCreateShader);
            return 0;
        }
        return glCreateShader;
    }

    public static int createProgram(String str, String str2) {
        int loadShader;
        int loadShader2 = loadShader(35633, str);
        if (loadShader2 == 0 || (loadShader = loadShader(35632, str2)) == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        checkError("glCreateProgram");
        if (glCreateProgram == 0) {
            LOG.e("Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, loadShader2);
        checkError("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, loadShader);
        checkError("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] != 1) {
            LOG.e("Could not link program:", GLES20.glGetProgramInfoLog(glCreateProgram));
            GLES20.glDeleteProgram(glCreateProgram);
            return 0;
        }
        return glCreateProgram;
    }

    public static FloatBuffer floatBuffer(float[] fArr) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = allocateDirect.asFloatBuffer();
        asFloatBuffer.put(fArr);
        asFloatBuffer.position(0);
        return asFloatBuffer;
    }
}
