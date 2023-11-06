package plant.testtree.camerademo.util;

/* loaded from: classes.dex */
public class PlaneTextureRotationUtils {
    public static final float[] TEXTURE_NO_ROTATION = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    public static final float[] TEXTURE_ROTATED_180 = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};
    public static final float[] TEXTURE_ROTATED_270 = {0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
    public static final float[] TEXTURE_ROTATED_90 = {1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f};

    private static float flip(float f) {
        return 1.0f - f;
    }

    private PlaneTextureRotationUtils() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.cameraediter.iphone11pro.filter.util.PlaneTextureRotationUtils$1 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$cameraediter$iphone11pro$filter$constant$Rotation = new int[Rotation.values().length];

        static {
            try {
                $SwitchMap$com$cameraediter$iphone11pro$filter$constant$Rotation[Rotation.ROTATION_90.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$cameraediter$iphone11pro$filter$constant$Rotation[Rotation.ROTATION_180.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$cameraediter$iphone11pro$filter$constant$Rotation[Rotation.ROTATION_270.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static float[] getRotation(Rotation rotation, boolean z, boolean z2) {
        float[] fArr;
        int i = AnonymousClass1.$SwitchMap$com$cameraediter$iphone11pro$filter$constant$Rotation[rotation.ordinal()];
        if (i == 1) {
            fArr = TEXTURE_ROTATED_90;
        } else if (i == 2) {
            fArr = TEXTURE_ROTATED_180;
        } else if (i == 3) {
            fArr = TEXTURE_ROTATED_270;
        } else {
            fArr = TEXTURE_NO_ROTATION;
        }
        if (z) {
            fArr = new float[]{flip(fArr[0]), fArr[1], flip(fArr[2]), fArr[3], flip(fArr[4]), fArr[5], flip(fArr[6]), fArr[7]};
        }
        return !z2 ? fArr : new float[]{fArr[0], flip(fArr[1]), fArr[2], flip(fArr[3]), fArr[4], flip(fArr[5]), fArr[6], flip(fArr[7])};
    }
}
