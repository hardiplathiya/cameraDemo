package plant.testtree.camerademo.stylableimage;

/* loaded from: classes2.dex */
public class StyleMatrixs {
    private static final float[] COMMON = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] GREY_SCALE = {0.33f, 0.59f, 0.11f, 0.0f, 0.0f, 0.33f, 0.59f, 0.11f, 0.0f, 0.0f, 0.33f, 0.59f, 0.11f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] INVERT = {-1.0f, 0.0f, 0.0f, 0.0f, 255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f, 255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] RGB_TO_BGR = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] SEPIA = {0.393f, 0.769f, 0.189f, 0.0f, 0.0f, 0.349f, 0.686f, 0.168f, 0.0f, 0.0f, 0.272f, 0.534f, 0.131f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] BRIGHT = {1.438f, -0.122f, -0.016f, 0.0f, 0.0f, -0.062f, 1.378f, -0.016f, 0.0f, 0.0f, -0.062f, -0.122f, 1.483f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] BLACK_AND_WHITE = {1.5f, 1.5f, 1.5f, 0.0f, -255.0f, 1.5f, 1.5f, 1.5f, 0.0f, -255.0f, 1.5f, 1.5f, 1.5f, 0.0f, -255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] VINTAGE_PINHOLE = {0.6279346f, 0.32021835f, -0.039654084f, 0.0f, 9.651286f, 0.025783977f, 0.64411885f, 0.032591276f, 0.0f, 7.462829f, 0.046605557f, -0.0851233f, 0.5241648f, 0.0f, 5.1591907f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] KODACHROME = {1.1285583f, -0.39673823f, -0.03992559f, 0.0f, 63.729588f, -0.1640434f, 1.0835252f, -0.054988053f, 0.0f, 24.732409f, -0.1678601f, -0.56034166f, 1.6014851f, 0.0f, 35.62983f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    private static final float[] TECHNICOLOR = {1.9125278f, -0.8545345f, -0.09155508f, 0.0f, 11.793604f, -0.30878335f, 1.7658908f, -0.10601743f, 0.0f, -70.35205f, -0.23110338f, -0.7501899f, 1.8475978f, 0.0f, 30.950941f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};

    public static final float[] common() {
        return (float[]) COMMON.clone();
    }

    public static final float[] greyScale() {
        return (float[]) GREY_SCALE.clone();
    }

    public static final float[] invert() {
        return (float[]) INVERT.clone();
    }

    public static final float[] rgbToBgr() {
        return (float[]) RGB_TO_BGR.clone();
    }

    public static final float[] sepia() {
        return (float[]) SEPIA.clone();
    }

    public static final float[] bright() {
        return (float[]) BRIGHT.clone();
    }

    public static final float[] blackAndWhite() {
        return (float[]) BLACK_AND_WHITE.clone();
    }

    public static final float[] vintagePinhole() {
        return (float[]) VINTAGE_PINHOLE.clone();
    }

    public static final float[] kodachrome() {
        return (float[]) KODACHROME.clone();
    }

    public static final float[] technicolor() {
        return (float[]) TECHNICOLOR.clone();
    }
}
