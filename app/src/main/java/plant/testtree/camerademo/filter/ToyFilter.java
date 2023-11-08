package plant.testtree.camerademo.filter;

import android.content.Context;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.makernotes.CanonMakernoteDirectory;
import com.drew.metadata.exif.makernotes.NikonType2MakernoteDirectory;
import com.drew.metadata.mp4.media.Mp4VideoDirectory;
import okhttp3.internal.ws.WebSocketProtocol;
import plant.testtree.camerademo.util.MxOneHashBaseFilter;


public class ToyFilter extends MxOneHashBaseFilter {
    @Override 
    public void init() {
        super.init();
    }

    @Override 
    public void onDrawFrame(int i) {
        super.onDrawFrame(i);
    }

    public ToyFilter(Context context) {
        super(context, "filter/fsh/mx/mx_toy.glsl");
        rgbMap = new int[]{0, 0, 0, 1, 1, 2, 2, 2, 3, 3, 4, 4, 4, 5, 5, 6, 6, 6, 7, 7, 8, 8, 9, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 17, 17, 18, 18, 19, 19, 20, 21, 21, 22, 22, 23, 23, 24, 25, 25, 26, 27, 27, 28, 29, 29, 30, 31, 31, 32, 33, 34, 34, 35, 36, 37, 38, 38, 39, 40, 41, 42, 43, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 62, 63, 64, 65, 66, 67, 69, 70, 71, 72, 74, 75, 76, 78, 79, 81, 82, 83, 85, 86, 88, 89, 91, 92, 94, 95, 97, 98, 100, 101, 103, 105, 106, 108, 110, 111, 113, 114, 116, 118, 119, 121, 123, 125, 126, 128, NikonType2MakernoteDirectory.TAG_ADAPTER, 131, NikonType2MakernoteDirectory.TAG_MANUAL_FOCUS_DISTANCE, NikonType2MakernoteDirectory.TAG_FLASH_USED, 136, 138, 140, 142, 143, 145, 147, 148, 150, 152, 154, NikonType2MakernoteDirectory.TAG_UNKNOWN_10, 157, 159, 160, 162, 164, NikonType2MakernoteDirectory.TAG_IMAGE_COUNT, NikonType2MakernoteDirectory.TAG_EXPOSURE_SEQUENCE_NUMBER, NikonType2MakernoteDirectory.TAG_FLASH_INFO, 170, NikonType2MakernoteDirectory.TAG_IMAGE_STABILISATION, NikonType2MakernoteDirectory.TAG_AF_RESPONSE, NikonType2MakernoteDirectory.TAG_UNKNOWN_30, 177, 178, 180, NikonType2MakernoteDirectory.TAG_UNKNOWN_48, NikonType2MakernoteDirectory.TAG_AF_INFO_2, NikonType2MakernoteDirectory.TAG_FILE_INFO, 186, NikonType2MakernoteDirectory.TAG_UNKNOWN_49, NikonType2MakernoteDirectory.TAG_UNKNOWN_50, 190, 192, 193, 195, 196, 197, 199, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 201, 203, 204, Mp4VideoDirectory.TAG_HEIGHT, Mp4VideoDirectory.TAG_HORIZONTAL_RESOLUTION, 208, Mp4VideoDirectory.TAG_DEPTH, Mp4VideoDirectory.TAG_COMPRESSION_TYPE, Mp4VideoDirectory.TAG_GRAPHICS_MODE, Mp4VideoDirectory.TAG_OPCOLOR, Mp4VideoDirectory.TAG_FRAME_RATE, 215, 216, 217, 218, 219, 220, 221, 222, 223, CanonMakernoteDirectory.TAG_SENSOR_INFO_ARRAY, 225, 226, 227, 228, 229, 230, 230, 231, 232, 233, 234, 234, 235, 236, 236, 237, 238, 239, 239, 240, 240, 241, 242, 242, 243, 243, 244, 245, 245, 246, 246, 247, 247, 248, 248, 249, 249, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 251, 251, 252, 252, 253, 253, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, ExifDirectoryBase.TAG_NEW_SUBFILE_TYPE, 255};
    }
}
