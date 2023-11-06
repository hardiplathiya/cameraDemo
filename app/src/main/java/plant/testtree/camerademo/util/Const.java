package plant.testtree.camerademo.util;

import android.os.Environment;
import java.io.File;

/* loaded from: classes.dex */
public class Const {
    private static final String SD_CARD_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    private static final String ROOT = File.separator + "iCamera";
    public static final String PATH = SD_CARD_PATH + ROOT;
}
