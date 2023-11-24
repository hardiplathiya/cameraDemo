package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.drew.metadata.mp4.media.Mp4VideoDirectory;


public class MxProFilter extends MxOneHashBaseFilter {
    @Override 
    public void init() {
        super.init();
    }

    @Override 
    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
    }

    public MxProFilter(Context context) {
        super(context, "filter/fsh/mx/mx_pro.glsl");
        rgbMap = new int[]{0, 1, 3, 4, 6, 7, 9, 10, 12, 13, 15, 17, 18, 20, 21, 23, 24, 26, 27, 29, 30, 32, 33, 35, 36, 38, 39, 41, 42, 44, 45, 47, 48, 50, 51, 53, 54, 56, 57, 59, 60, 61, 63, 64, 66, 67, 69, 70, 71, 73, 74, 75, 77, 78, 79, 81, 82, 83, 85, 86, 87, 89, 90, 91, 92, 94, 95, 96, 97, 99, 100, 101, 102, 103, 105, 106, 107, 108, 109, 111, 112, 113, 114, 115, 116, 117, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, NikonType2MakernoteDirectory.TAG_ADAPTER, 131, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_DIGITAL_ZOOM, NikonType2MakernoteDirectory.TAG_FLASH_USED, 136, 137, 138, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, NikonType2MakernoteDirectory.TAG_SCENE_ASSIST, 157, 157, 158, 159, 160, CanonMakernoteDirectory.TAG_TONE_CURVE_TABLE, 162, 163, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_DELETED_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 169, 170, 171, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, 174, 174, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 176, 177, 178, 179, 179, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, 182, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_AF_TUNE, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, 188, 188, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 191, 192, 192, 193, 194, 195, 195, 196, 197, 198, 198, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 202, 203, 204, 204, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 215, 216, 217, 217, 218, 219, 219, 220, 221, 222, 222, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 225, 226, 226, 227, 228, 228, 229, 230, 230, 231, 232, 232, 233, 234, 234, 235, 236, 236, 237, 238, 238, 239, 240, 240, 241, 242, 242, 243, 243, 244, 245, 245, 246, 247, 247, 248, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 252, 253, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255};
    }
}
