package plant.testtree.camerademo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CameraUtil {
    private static float sPixelDensity = 1.0f;
    private static class ImageFileNamer {
        private final SimpleDateFormat mFormat;
        private long mLastDate;
        private int mSameSecondCount;

        public ImageFileNamer(String str) {
            this.mFormat = new SimpleDateFormat(str);
        }

        public String generateName(long j) {
            String format = this.mFormat.format(new Date(j));
            if (j / 1000 == this.mLastDate / 1000) {
                this.mSameSecondCount++;
                return format + "_" + this.mSameSecondCount;
            }
            this.mLastDate = j;
            this.mSameSecondCount = 0;
            return format;
        }
    }

    public static boolean isSupported(String str, List<String> list) {
        return list != null && list.indexOf(str) >= 0;
    }

    public static int dpToPixel(int i) {
        return Math.round(sPixelDensity * i);
    }
}
