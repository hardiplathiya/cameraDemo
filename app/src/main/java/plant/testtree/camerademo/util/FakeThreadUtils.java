package plant.testtree.camerademo.util;

import android.os.AsyncTask;
import android.util.Log;
import java.io.File;


public class FakeThreadUtils {

    public static void postTask(Runnable runnable) {
        new Thread(runnable).start();
    }
}
