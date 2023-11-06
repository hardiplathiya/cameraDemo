package plant.testtree.camerademo.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import plant.testtree.camerademo.util.ApplicationUtils;
import plant.testtree.camerademo.util.Prefs;

/* loaded from: classes.dex */
public class MyApp extends Application {
    private static Context context;
    private static MyApp ourInstance;
    public boolean isFromEdit = false;
    public boolean isFromPuzzle = false;
    String path;
    public SharedPreferences preferences;
    int size;

    public static MyApp getInstance() {
        return ourInstance;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    public boolean isFromPuzzle() {
        return this.isFromPuzzle;
    }

    public void setFromPuzzle(boolean z) {
        this.isFromPuzzle = z;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        context = this;
        Prefs.init(this);
        ApplicationUtils.init(this);
        this.preferences = getApplicationContext().getSharedPreferences("GalleryApp", 0);
    }

    public static Context getAppContext() {
        return context;
    }
}