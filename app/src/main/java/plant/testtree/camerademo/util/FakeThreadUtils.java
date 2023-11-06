package plant.testtree.camerademo.util;

import android.os.AsyncTask;
import android.util.Log;
import java.io.File;

/* loaded from: classes.dex */
public class FakeThreadUtils {
    private static final String TAG = "FakeThreadUtils";

    /* loaded from: classes.dex */
    public static class SaveFileTask extends AsyncTask<Void, Integer, Boolean> {
        private String fileName;
        private FileUtils.FileSavedCallback fileSavedCallback;
        private String inputPath;
        private String outputPath;

        public SaveFileTask(String str, String str2, String str3, FileUtils.FileSavedCallback fileSavedCallback) {
            this.outputPath = str;
            this.fileName = str2;
            this.inputPath = str3;
            this.fileSavedCallback = fileSavedCallback;
        }

        @Override // android.os.AsyncTask
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void... voidArr) {
            FileUtils.copyFileFromTo(this.outputPath, this.fileName, this.inputPath);
            return true;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(Boolean bool) {
            Log.d(FakeThreadUtils.TAG, "onPostExecute: " + this.outputPath + " " + this.fileName + " " + this.inputPath);
            this.fileSavedCallback.onFileSaved(new File(this.outputPath, this.fileName).getAbsolutePath());
        }
    }

    public static void postTask(Runnable runnable) {
        new Thread(runnable).start();
    }
}
