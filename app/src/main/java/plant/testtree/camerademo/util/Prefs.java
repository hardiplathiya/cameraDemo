package plant.testtree.camerademo.util;

import android.content.Context;

import plant.testtree.camerademo.filter.SortingMode;
import plant.testtree.camerademo.filter.SortingOrder;

/* loaded from: classes.dex */
public class Prefs {
    private static SharedPrefs sharedPrefs;

    public static void init(Context context) {
        if (sharedPrefs == null) {
            sharedPrefs = new SharedPrefs(context);
            return;
        }
        throw new RuntimeException("Prefs has already been instantiated");
    }

    public static int getFolderColumnsPortrait() {
        return getPrefs().get(Keys.FOLDER_COLUMNS_PORTRAIT, 2);
    }

    public static int getFolderColumnsLandscape() {
        return getPrefs().get(Keys.FOLDER_COLUMNS_LANDSCAPE, 3);
    }

    public static int getMediaColumnsPortrait() {
        return getPrefs().get(Keys.MEDIA_COLUMNS_PORTRAIT, 3);
    }

    public static int getMediaColumnsLandscape() {
        return getPrefs().get(Keys.MEDIA_COLUMNS_LANDSCAPE, 4);
    }

    public static SortingMode getAlbumSortingMode() {
        return SortingMode.fromValue(getPrefs().get(Keys.ALBUM_SORTING_MODE, Defaults.ALBUM_SORTING_MODE));
    }

    public static SortingOrder getAlbumSortingOrder() {
        return SortingOrder.fromValue(getPrefs().get(Keys.ALBUM_SORTING_ORDER, Defaults.ALBUM_SORTING_ORDER));
    }

    public static boolean showVideos() {
        return getPrefs().get(Keys.SHOW_VIDEOS, true);
    }

    public static boolean showMediaCount() {
        return getPrefs().get(Keys.SHOW_MEDIA_COUNT, true);
    }

    public static boolean showAlbumPath() {
        return getPrefs().get(Keys.SHOW_ALBUM_PATH, false);
    }

    public static boolean showEasterEgg() {
        return getPrefs().get(Keys.SHOW_EASTER_EGG, false);
    }

    public static boolean animationsEnabled() {
        return !getPrefs().get(Keys.ANIMATIONS_DISABLED, false);
    }

    public static boolean timelineEnabled() {
        return getPrefs().get(Keys.TIMELINE_ENABLED, true);
    }

    public static int getLastVersionCode() {
        return getPrefs().get(Keys.LAST_VERSION_CODE, 0);
    }

    public static void setFolderColumnsPortrait(int i) {
        getPrefs().put(Keys.FOLDER_COLUMNS_PORTRAIT, i);
    }

    public static void setFolderColumnsLandscape(int i) {
        getPrefs().put(Keys.FOLDER_COLUMNS_LANDSCAPE, i);
    }

    public static void setMediaColumnsPortrait(int i) {
        getPrefs().put(Keys.MEDIA_COLUMNS_PORTRAIT, i);
    }

    public static void setMediaColumnsLandscape(int i) {
        getPrefs().put(Keys.MEDIA_COLUMNS_LANDSCAPE, i);
    }

    public static void setAlbumSortingMode(SortingMode sortingMode) {
        getPrefs().put(Keys.ALBUM_SORTING_MODE, sortingMode.getValue());
    }

    public static void setAlbumSortingOrder(SortingOrder sortingOrder) {
        getPrefs().put(Keys.ALBUM_SORTING_ORDER, sortingOrder.getValue());
    }

    public static void setShowVideos(boolean z) {
        getPrefs().put(Keys.SHOW_VIDEOS, z);
    }

    public static void setShowMediaCount(boolean z) {
        getPrefs().put(Keys.SHOW_MEDIA_COUNT, z);
    }

    public static void setShowAlbumPath(boolean z) {
        getPrefs().put(Keys.SHOW_ALBUM_PATH, z);
    }

    public static void setLastVersionCode(int i) {
        getPrefs().put(Keys.LAST_VERSION_CODE, i);
    }

    public static void setShowEasterEgg(boolean z) {
        getPrefs().put(Keys.SHOW_EASTER_EGG, z);
    }

    @Deprecated
    public static void setToggleValue(String str, boolean z) {
        getPrefs().put(str, z);
    }

    @Deprecated
    public static boolean getToggleValue(String str, boolean z) {
        return getPrefs().get(str, z);
    }

    private static SharedPrefs getPrefs() {
        SharedPrefs sharedPrefs2 = sharedPrefs;
        if (sharedPrefs2 != null) {
            return sharedPrefs2;
        }
        throw new RuntimeException("Prefs has not been instantiated. Call init() with context");
    }
}
