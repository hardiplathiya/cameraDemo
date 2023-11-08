package plant.testtree.camerademo.util;

import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

import plant.testtree.camerademo.model.Image;


public class Const {
    public static ArrayList<Image> imgList = new ArrayList<>();
    private static final String SD_CARD_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    private static final String ROOT = File.separator + "iCamera";
    public static final String PATH = SD_CARD_PATH + ROOT;
}
