package plant.testtree.camerademo.filter.insta;

import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;

import plant.testtree.camerademo.util.TextureUtils;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.drew.metadata.mp4.media.Mp4VideoDirectory;
import okhttp3.internal.ws.WebSocketProtocol;
import plant.testtree.camerademo.filter.MultipleTextureFilter;


public class InsCalmFilter extends MultipleTextureFilter {
    public InsCalmFilter(Context context) {
        super(context, "filter/fsh/insta/calm.glsl");
        this.textureSize = 3;
    }

    @Override 
    public void init() {
        super.init();
        this.externalBitmapTextures[0].load(this.context, "filter/textures/inst/calm_mask1.jpg");
        this.externalBitmapTextures[1].load(this.context, "filter/textures/inst/calm_mask2.jpg");
        this.externalBitmapTextures[2].setImageTextureId(prepareRawTexture1());
    }

    private int prepareRawTexture1() {
        byte[] bArr = new byte[3072];
        int[] iArr = {38, 39, 40, 41, 41, 42, 43, 44, 45, 46, 47, 47, 48, 49, 50, 51, 52, 52, 53, 54, 55, 56, 57, 58, 58, 59, 60, 61, 62, 63, 64, 64, 65, 66, 67, 68, 69, 69, 70, 71, 72, 73, 74, 75, 75, 76, 77, 78, 79, 80, 81, 81, 82, 83, 84, 85, 86, 87, 87, 88, 89, 90, 91, 92, 92, 93, 94, 95, 96, 97, 98, 98, 99, 100, 101, 102, 103, 104, 104, 105, 106, 107, 108, 109, 109, 110, 111, 112, 113, 114, 115, 115, 116, 117, 118, 119, 120, 121, 121, 122, 123, 124, 125, 126, 127, 127, 128, 129, NikonType2MakernoteDirectory.TAG_ADAPTER, 131, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM, NikonType2MakernoteDirectory.TAG_FLASH_USED, 136, 137, 138, 138, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 140, 141, 142, 143, 144, 144, 145, 146, 147, 148, 149, 149, 150, 151, 152, 153, 154, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST, 157, 158, 159, 160, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, 162, 163, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 169, 170, 171, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, 174, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 176, 177, 178, 178, 179, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, 182, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_AF_TUNE, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, 188, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 191, 192, 193, 194, 195, 195, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 201, 202, 203, 204, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 216, 217, 218, 218, 219, 220, 221, 222, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 225, 226, 227, 228, 229, 229, 230, 231, 232, 233, 234, 235, 235, 236, 237, 238, 239, 240, 241, 241, 242, 243, 244, 245, 246, 246, 247, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 252, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255};
        for (int i = 0; i < 256; i++) {
            int i2 = i * 4;
            bArr[i2] = (byte) iArr[i];
            bArr[i2 + 1] = (byte) iArr[i];
            bArr[i2 + 2] = (byte) iArr[i];
            bArr[i2 + 3] = -1;
        }
        int[] iArr2 = {0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 13, 14, 15, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 93, 94, 95, 96, 97, 98, 99, 99, 100, 101, 102, 103, 104, 104, 105, 106, 107, 108, 109, 109, 110, 111, 112, 113, 114, 114, 115, 116, 117, 118, 118, 119, 120, 121, 122, 123, 123, 124, 125, 126, 127, 127, 128, 129, NikonType2MakernoteDirectory.TAG_ADAPTER, 131, 131, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM, NikonType2MakernoteDirectory.TAG_FLASH_USED, NikonType2MakernoteDirectory.TAG_FLASH_USED, 136, 137, 138, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 140, 140, 141, 142, 143, 144, 145, 145, 146, 147, 148, 149, 150, 150, 151, 152, 153, 154, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST, 157, 157, 158, 159, 160, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, 162, 163, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 169, 170, 171, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, 174, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 176, 177, 178, 179, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, 182, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_AF_TUNE, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, 188, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 191, 192, 193, 194, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 216, 217, 218, 220, 221, 222, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 226, 227, 228, 229, 230, 232, 233, 234, 235, 237, 238, 239, 240, 241, 243, 244, 245, 246, 248, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255};
        int[] iArr3 = {0, 1, 1, 2, 3, 3, 4, 5, 6, 6, 7, 8, 8, 9, 10, 11, 11, 12, 13, 14, 15, 15, 16, 17, 18, 19, 20, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 36, 37, 38, 39, 40, 42, 43, 44, 46, 47, 48, 50, 51, 53, 54, 56, 57, 59, 60, 62, 64, 65, 67, 69, 70, 72, 74, 75, 77, 79, 80, 82, 84, 85, 87, 89, 91, 92, 94, 96, 97, 99, 101, 102, 104, 106, 107, 109, 111, 112, 114, 115, 117, 118, 120, 121, 123, 124, 126, 127, 129, NikonType2MakernoteDirectory.TAG_ADAPTER, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM, 136, 137, 138, 140, 141, 142, 144, 145, 146, 147, 149, 150, 151, 152, 153, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST, 157, 158, 159, 160, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, 163, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 169, 170, 171, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, 174, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 176, 177, 178, 179, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, 182, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_AF_TUNE, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, 188, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 191, 191, 192, 193, 194, 195, 196, 196, 197, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 203, 204, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_FRAME_RATE, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 216, 216, 217, 218, 218, 219, 220, 220, 221, 222, 222, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 225, 226, 226, 227, 227, 228, 229, 229, 230, 231, 231, 232, 233, 233, 234, 234, 235, 236, 236, 237, 237, 238, 239, 239, 240, 240, 241, 242, 242, 243, 243, 244, 245, 245, 246, 246, 247, 247, 248, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 252, 252, 253, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255};
        for (int i3 = 0; i3 < 256; i3++) {
            int i4 = (i3 * 4) + 1024;
            bArr[i4] = (byte) iArr3[i3];
            bArr[i4 + 1] = (byte) iArr3[i3];
            bArr[i4 + 2] = (byte) iArr2[i3];
            bArr[i4 + 3] = -1;
        }
        int[] iArr4 = {0, 1, 3, 4, 5, 7, 8, 10, 11, 12, 14, 15, 16, 18, 19, 20, 22, 23, 24, 26, 27, 28, 30, 31, 33, 34, 35, 37, 38, 39, 41, 42, 43, 45, 46, 47, 49, 50, 51, 53, 54, 55, 57, 58, 59, 61, 62, 63, 64, 66, 67, 68, 70, 71, 72, 74, 75, 76, 77, 79, 80, 81, 83, 84, 85, 86, 88, 89, 90, 91, 93, 94, 95, 96, 98, 99, 100, 101, 103, 104, 105, 106, 108, 109, 110, 111, 112, 114, 115, 116, 117, 118, 119, 121, 122, 123, 124, 125, 126, 128, 129, NikonType2MakernoteDirectory.TAG_ADAPTER, 131, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM, 136, 137, 138, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 140, 141, 142, 143, 144, 145, 147, 148, 149, 150, 151, 152, 153, 154, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST, 157, 158, 159, 160, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, 162, 163, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 169, 170, 171, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, 174, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 176, 177, 178, 179, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, 182, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_AF_TUNE, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, 188, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 191, 192, 193, 194, 194, 195, 196, 197, 198, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 202, 203, 204, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, 208, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_FRAME_RATE, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 216, 216, 217, 218, 218, 219, 220, 221, 221, 222, 223, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 225, 225, 226, 227, 227, 228, 229, 229, 230, 231, 231, 232, 233, 233, 234, 235, 235, 236, 237, 237, 238, 239, 239, 240, 240, 241, 242, 242, 243, 244, 244, 245, 246, 246, 247, 247, 248, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 252, 252, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255};
        int[] iArr5 = {0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 20, 20, 21, 21, 21, 22, 22, 23, 23, 24, 24, 24, 25, 25, 26, 26, 27, 27, 28, 28, 29, 29, 30, 30, 31, 31, 32, 33, 33, 34, 34, 35, 35, 36, 37, 37, 38, 38, 39, 40, 40, 41, 42, 42, 43, 44, 44, 45, 46, 47, 47, 48, 49, 49, 50, 51, 52, 53, 53, 54, 55, 56, 57, 57, 58, 59, 60, 61, 62, 63, 64, 65, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 81, 82, 83, 84, 85, 86, 87, 88, 90, 91, 92, 93, 94, 96, 97, 98, 99, 101, 102, 103, 105, 106, 107, 109, 110, 111, 113, 114, 115, 117, 118, 120, 121, 123, 124, 126, 127, 129, NikonType2MakernoteDirectory.TAG_ADAPTER, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_FLASH_USED, 137, 138, 140, 142, 143, 145, 147, 148, 150, 152, 153, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, 157, 159, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, 162, 164, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 170, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, 174, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 177, 179, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_AF_TUNE, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 191, 193, 195, 197, 199, 201, 203, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_COLOR_TABLE, 215, 217, 219, 221, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 226, 228, 230, 232, 234, 236, 238, 240, 242, 244, 247, 249, 251, 253, 255};
        int[] iArr6 = {0, 0, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9, 9, 10, 10, 10, 11, 11, 11, 12, 12, 13, 13, 13, 14, 14, 14, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 19, 19, 20, 20, 20, 21, 21, 21, 22, 22, 23, 23, 23, 24, 24, 24, 25, 25, 25, 25, 26, 26, 27, 27, 28, 28, 28, 28, 29, 29, 30, 29, 31, 31, 31, 31, 32, 32, 33, 33, 34, 34, 34, 34, 35, 35, 36, 36, 37, 37, 37, 38, 38, 39, 39, 39, 40, 40, 40, 41, 42, 42, 43, 43, 44, 44, 45, 45, 45, 46, 47, 47, 48, 48, 49, 50, 51, 51, 52, 52, 53, 53, 54, 55, 55, 56, 57, 57, 58, 59, 60, 60, 61, 62, 63, 63, 64, 65, 66, 67, 68, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 88, 89, 90, 91, 93, 94, 95, 96, 97, 98, 100, 101, 103, 104, 105, 107, 108, 110, 111, 113, 115, 116, 118, 119, 120, 122, 123, 125, 127, 128, NikonType2MakernoteDirectory.TAG_ADAPTER, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM, NikonType2MakernoteDirectory.TAG_FLASH_USED, 137, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 141, 143, 144, 146, 148, 150, 152, 154, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST, 158, 160, 163, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, 169, 171, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 178, 180, 182, NikonType2MakernoteDirectory.TAG_AF_TUNE, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 192, 194, 197, 199, 201, 204, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_FRAME_RATE, 216, 219, 221, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 226, 229, 232, 234, 236, 239, 241, 245, 247, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 252, 255};
        for (int i5 = 0; i5 < 256; i5++) {
            int i6 = (i5 * 4) + 2048;
            bArr[i6] = (byte) iArr4[i5];
            bArr[i6 + 1] = (byte) iArr5[i5];
            bArr[i6 + 2] = (byte) iArr6[i5];
            bArr[i6 + 3] = -1;
        }
        return TextureUtils.getTextureFromByteArray(bArr, 256, 3);
    }
}
