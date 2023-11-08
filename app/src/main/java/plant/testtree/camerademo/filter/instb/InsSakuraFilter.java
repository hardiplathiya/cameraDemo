package plant.testtree.camerademo.filter.instb;

import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.drew.metadata.mp4.media.Mp4VideoDirectory;
import okhttp3.internal.ws.WebSocketProtocol;
import plant.testtree.camerademo.filter.MultipleTextureFilter;
import plant.testtree.camerademo.util.TextureUtils;


public class InsSakuraFilter extends MultipleTextureFilter {
    public InsSakuraFilter(Context context) {
        super(context, "filter/fsh/instb/sakura.glsl");
        this.textureSize = 1;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].setImageTextureId(prepareRawTexture1());
    }

    @Override 
    public void onPreDrawElements() {
        super.onPreDrawElements();
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelWidthOffset", 1.0f / this.surfaceWidth);
        setUniform1f(this.glSimpleProgram.getProgramId(), "texelHeightOffset", 1.0f / this.surfaceHeight);
    }

    private int prepareRawTexture1() {
        byte[] bArr = new byte[1024];
        int[] iArr = {95, 95, 96, 97, 97, 98, 99, 99, 100, 101, 101, 102, 103, 104, 104, 105, 106, 106, 107, 108, 108, 109, 110, 111, 111, 112, 113, 113, 114, 115, 115, 116, 117, 117, 118, 119, 120, 120, 121, 122, 122, 123, 124, 124, 125, 126, 127, 127, 128, 129, 129, NikonType2MakernoteDirectory.TAG_ADAPTER, 131, 131, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM, NikonType2MakernoteDirectory.TAG_FLASH_USED, 136, 136, 137, 138, 138, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 140, 140, 141, 142, 143, 143, 144, 145, 145, 146, 147, 147, 148, 149, 149, 150, 151, 152, 152, 153, 154, 154, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST, 157, 158, 159, 159, 160, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, 162, 163, 163, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_FLASH_INFO, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 169, 170, 170, 171, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, 174, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 176, 177, 177, 178, 179, 179, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, 182, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_AF_TUNE, 186, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, 188, 188, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 191, 191, 192, 193, 193, 194, 195, 195, 196, 197, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 202, 203, 204, 204, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 216, 216, 217, 218, 218, 219, 220, 220, 221, 222, 223, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 225, 225, 226, 227, 227, 228, 229, 229, 230, 231, 232, 232, 233, 234, 234, 235, 236, 236, 237, 238, 239, 239, 240, 241, 241, 242, 243, 243, 244, 245, 245, 246, 247, 248, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 252, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
        for (int i = 0; i < 256; i++) {
            int i2 = i * 4;
            bArr[i2] = (byte) iArr[i];
            bArr[i2 + 1] = (byte) iArr[i];
            bArr[i2 + 2] = (byte) iArr[i];
            bArr[i2 + 3] = (byte) iArr[i];
        }
        return TextureUtils.getTextureFromByteArray(bArr, 256, 1);
    }
}
