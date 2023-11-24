package com.iphonecamera.allinone.cameraediting.filter;

import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.drew.metadata.mp4.media.Mp4VideoDirectory;
import com.iphonecamera.allinone.cameraediting.util.MxOneHashBaseFilter;


public class PastTimeFilter extends MxOneHashBaseFilter {
    @Override 
    public void init() {
        super.init();
    }

    @Override 
    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
    }

    public PastTimeFilter(Context context) {
        super(context, "filter/fsh/mx/mx_past_time.glsl");
        rgbMap = new int[]{0, 1, 3, 5, 7, 9, 10, 12, 14, 16, 18, 19, 21, 23, 25, 27, 28, 30, 32, 34, 35, 37, 39, 41, 43, 44, 46, 48, 50, 51, 53, 55, 57, 58, 60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 82, 84, 86, 88, 89, 91, 92, 94, 96, 97, 99, 101, 102, 104, 105, 107, 109, 110, 112, 113, 115, 117, 118, 120, 121, 123, 124, 126, 127, 129, NikonType2MakernoteDirectory.TAG_ADAPTER, NikonType2MakernoteDirectory.TAG_LENS, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_FLASH_USED, 136, 137, NikonType2MakernoteDirectory.TAG_LENS_STOPS, 140, 142, 143, 145, 146, 147, 149, 150, 151, 153, 154, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, 157, 158, 159, 160, 162, 163, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 169, 170, 171, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, 174, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 176, 177, 178, 179, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, 182, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, NikonType2MakernoteDirectory.TAG_AF_TUNE, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, 188, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 202, 203, 204, 204, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, Mp4VideoDirectory.TAG_VERTICAL_RESOLUTION, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_COLOR_TABLE, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 215, 216, 217, 217, 218, 218, 219, 220, 220, 221, 222, 222, 223, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 225, 225, 226, 227, 227, 228, 228, 229, 229, 230, 230, 231, 231, 231, 232, 232, 233, 233, 234, 234, 235, 235, 235, 236, 236, 237, 237, 237, 238, 238, 239, 239, 239, 240, 240, 240, 241, 241, 242, 242, 242, 243, 243, 243, 244, 244, 244, 245, 245, 245, 245, 246, 246, 246, 247, 247, 247, 248, 248, 248, 248, 249, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 251, 252, 252, 252, 252, 253, 253, 253, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255};
    }
}
