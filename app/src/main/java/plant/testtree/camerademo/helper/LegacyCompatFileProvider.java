package plant.testtree.camerademo.helper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;

/* loaded from: classes.dex */
public class LegacyCompatFileProvider extends FileProvider {
    @Override // androidx.core.content.FileProvider, android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return new LegacyCompatCursorWrapper(super.query(uri, strArr, str, strArr2, str2));
    }

    public static Uri getUri(Context context, File file) {
        return getUriForFile(context, ApplicationUtils.getPackageName() + ".provider", file);
    }
}
