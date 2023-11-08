package plant.testtree.camerademo.filter.instb;

import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;
import plant.testtree.camerademo.filter.MultipleTextureFilter;
import plant.testtree.camerademo.util.SimpleFragmentShaderFilter;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.drew.metadata.mp4.media.Mp4VideoDirectory;
import okhttp3.internal.ws.WebSocketProtocol;
import plant.testtree.camerademo.util.TextureUtils;


public class InsSweetsFilter extends MultipleTextureFilter {
    public InsSweetsFilter(Context context) {
        super(context, "filter/fsh/instb/sweets.glsl");
        this.textureSize = 2;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].setImageTextureId(prepareRawTexture1());
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/rise_mask2.jpg");
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelWidthOffset", 1.0f / this.surfaceWidth);
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelHeightOffset", 1.0f / this.surfaceHeight);
    }

    private int prepareRawTexture1() {
        byte[] bArr = new byte[1024];
        int[] iArr = {0, 1, 2, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10, 10, 11, 12, 13, 14, 14, 15, 16, 17, 18, 19, 19, 20, 21, 22, 23, 24, 24, 25, 26, 27, 28, 29, 30, 30, 31, 32, 33, 34, 35, 36, 37, 38, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 71, 72, 73, 74, 75, 76, 77, 79, 80, 81, 82, 83, 84, 86, 87, 88, 89, 90, 92, 93, 94, 95, 96, 98, 99, 100, 101, 103, 104, 105, 106, 108, 109, 110, 111, 113, 114, 115, 116, 118, 119, 120, 121, 123, 124, 125, 126, 128, 129, NikonType2MakernoteDirectory.TAG_ADAPTER, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM, NikonType2MakernoteDirectory.TAG_FLASH_USED, 137, 138, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 140, 142, 143, 144, 145, 147, 148, 149, 150, 152, 153, 154, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, 157, 158, 159, 160, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, 163, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, 169, 170, 171, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, 174, 176, 177, 178, 179, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, 182, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, 188, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 216, 217, 217, 218, 219, 220, 221, 222, 222, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 225, 226, 227, 227, 228, 229, 230, 230, 231, 232, 233, 234, 234, 235, 236, 237, 237, 238, 239, 240, 240, 241, 242, 243, 243, 244, 245, 246, 246, 247, 248, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 252, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255};
        for (int i = 0; i < 256; i++) {
            int i2 = i * 4;
            bArr[i2] = (byte) iArr[i];
            bArr[i2 + 1] = (byte) iArr[i];
            bArr[i2 + 2] = (byte) iArr[i];
            bArr[i2 + 3] = (byte) i;
        }
        return TextureUtils.getTextureFromByteArray(bArr, 256, 1);
    }
}
