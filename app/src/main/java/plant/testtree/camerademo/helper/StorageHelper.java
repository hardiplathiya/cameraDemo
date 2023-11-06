package plant.testtree.camerademo.helper;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;

import plant.testtree.camerademo.R;
import plant.testtree.camerademo.util.ApplicationUtils;
import plant.testtree.camerademo.util.ErrorCause;
import plant.testtree.camerademo.util.MediaHelper;
import plant.testtree.camerademo.util.ProgressException;
import plant.testtree.camerademo.util.StringUtils;

/* loaded from: classes.dex */
public class StorageHelper {
    private static final String PRIMARY_VOLUME_NAME = "primary";
    private static final String TAG = "StorageHelper";

    private static boolean isWritable(File file) {
        boolean exists = file.exists();
        try {
            new FileOutputStream(file, true).close();
        } catch (IOException unused) {
        }
        boolean canWrite = file.canWrite();
        if (!exists) {
            file.delete();
        }
        return canWrite;
    }

    public static boolean mkdir(Context context, File file) {
        boolean exists = file.exists();
        if (!exists) {
            exists = file.mkdir();
        }
        if (!exists && Build.VERSION.SDK_INT >= 21) {
            DocumentFile documentFile = getDocumentFile(context, file, true, true);
            exists = documentFile != null && documentFile.exists();
        }
        if (exists) {
            MediaHelper.scanFile(context, new String[]{file.getPath()});
        }
        return exists;
    }

    private static File getTargetFile(File file, File file2) {
        File file3 = new File(file2, file.getName());
        return (file.getParentFile().equals(file2) || file3.exists()) ? new File(file2, StringUtils.incrementFileNameSuffix(file.getName())) : file3;
    }

    public static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, ApplicationUtils.getPackageName() + ".provider", file);
    }

    public static boolean copyFile(Context context, File file, File file2) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            boolean copyToFile = copyToFile(fileInputStream, file2);
            fileInputStream.close();
            return copyToFile;
        } catch (IOException unused) {
            return false;
        }
    }

    public static boolean copyToFile(InputStream inputStream, File file) {
        try {
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            fileOutputStream.flush();
            try {
                fileOutputStream.getFD().sync();
            } catch (IOException unused) {
            }
            fileOutputStream.close();
            return true;
        } catch (IOException unused2) {
            return false;
        }
    }

    private static boolean isFileOnSdCard(Context context, File file) {
        String sdcardPath = getSdcardPath(context);
        return sdcardPath != null && file.getPath().startsWith(sdcardPath);
    }

    public static boolean moveFile(Context context, File file, File file2) {
        boolean renameTo = file.renameTo(file2);
        if (renameTo) {
            return renameTo;
        }
        boolean copyFile = copyFile(context, file, file2);
        if (copyFile) {
            try {
                deleteFile(context, file);
                return true;
            } catch (ProgressException unused) {
                return false;
            }
        }
        return copyFile;
    }

    private static Uri getUriFromFile(Context context, String str) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor query = contentResolver.query(MediaStore.Files.getContentUri("external"), new String[]{"_id"}, "_data = ?", new String[]{str}, "date_added desc");
        if (query == null) {
            return null;
        }
        query.moveToFirst();
        if (query.isAfterLast()) {
            query.close();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", str);
            return contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues);
        }
        @SuppressLint("Range") Uri build = MediaStore.Files.getContentUri("external").buildUpon().appendPath(Integer.toString(query.getInt(query.getColumnIndex("_id")))).build();
        query.close();
        return build;
    }

    public static boolean deleteFilesInFolder(Context context, File file) {
        String[] list = file.list();
        if (list == null) {
            return true;
        }
        boolean z = true;
        for (String str : list) {
            File file2 = new File(file, str);
            if (!file2.isDirectory()) {
                try {
                    deleteFile(context, file2);
                } catch (ProgressException e) {
                    Log.e(TAG, "Failed to delete file", e);
                    z = false;
                }
            }
        }
        return z;
    }

    public static void deleteFile(Context context, File file) throws ProgressException {
        boolean z;
        ErrorCause errorCause = new ErrorCause(file.getName());
        try {
            z = file.delete();
        } catch (Exception e) {
            errorCause.addCause(e.getLocalizedMessage());
            z = false;
        }
        if (!z && Build.VERSION.SDK_INT >= 21) {
            DocumentFile documentFile = getDocumentFile(context, file, false, false);
            z = documentFile != null && documentFile.delete();
            errorCause.addCause("Failed SAF");
        }
        if (!z && Build.VERSION.SDK_INT == 19) {
            ContentResolver contentResolver = context.getContentResolver();
            try {
                Uri uriForFile = getUriForFile(context, file);
                if (uriForFile != null) {
                    contentResolver.delete(uriForFile, null, null);
                }
                z = !file.exists();
            } catch (Exception e2) {
                errorCause.addCause(String.format("Failed CP: %s", e2.getLocalizedMessage()));
                Log.e(TAG, "Error when deleting file " + file.getAbsolutePath(), e2);
                z = false;
            }
        }
        if (!z) {
            throw new ProgressException(errorCause);
        }
        MediaHelper.scanFile(context, new String[]{file.getPath()});
    }

    public static HashSet<File> getStorageRoots(Context context) {
        File[] externalFilesDirs;
        HashSet<File> hashSet = new HashSet<>();
        for (File file : context.getExternalFilesDirs("external")) {
            if (file != null) {
                int lastIndexOf = file.getAbsolutePath().lastIndexOf("/Android/data");
                if (lastIndexOf < 0) {
                    Log.w("asd", "Unexpected external file dir: " + file.getAbsolutePath());
                } else {
                    hashSet.add(new File(file.getAbsolutePath().substring(0, lastIndexOf)));
                }
            }
        }
        return hashSet;
    }

    public static String getSdcardPath(Context context) {
        File[] externalFilesDirs;
        for (File file : context.getExternalFilesDirs("external")) {
            if (file != null && !file.equals(context.getExternalFilesDir("external"))) {
                int lastIndexOf = file.getAbsolutePath().lastIndexOf("/Android/data");
                if (lastIndexOf >= 0) {
                    return new File(file.getAbsolutePath().substring(0, lastIndexOf)).getPath();
                }
                Log.w("asd", "Unexpected external file dir: " + file.getAbsolutePath());
            }
        }
        return null;
    }

    public static boolean rmdir(Context context, File file) {
        if (file.exists() || file.isDirectory()) {
            String[] list = file.list();
            if (list == null || list.length <= 0) {
                if (file.delete()) {
                    return true;
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    DocumentFile documentFile = getDocumentFile(context, file, true, true);
                    return documentFile != null && documentFile.delete();
                }
                if (Build.VERSION.SDK_INT == 19) {
                    ContentResolver contentResolver = context.getContentResolver();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_data", file.getAbsolutePath());
                    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    contentResolver.delete(MediaStore.Files.getContentUri("external"), "_data=?", new String[]{file.getAbsolutePath()});
                }
                return !file.exists();
            }
            return false;
        }
        return false;
    }

    private static DocumentFile getDocumentFile(Context context, File file, boolean z, boolean z2) {
        String str;
        Uri treeUri = getTreeUri(context);
        if (treeUri == null) {
            return null;
        }
        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(context, treeUri);
        String savedSdcardPath = getSavedSdcardPath(context);
        if (savedSdcardPath != null) {
            str = file.getPath().indexOf(savedSdcardPath) != -1 ? file.getAbsolutePath().substring(savedSdcardPath.length()) : null;
        } else {
            Iterator<File> it2 = getStorageRoots(context).iterator();
            String str2 = null;
            while (it2.hasNext()) {
                File next = it2.next();
                if (next != null && file.getPath().indexOf(next.getPath()) != -1) {
                    str2 = file.getAbsolutePath().substring(file.getPath().length());
                }
            }
            str = str2;
        }
        if (str == null) {
            Log.d(TAG, "unable to find the document file, filePath:" + file.getPath() + " root: " + savedSdcardPath);
            return null;
        }
        if (str.startsWith(File.separator)) {
            str = str.substring(1);
        }
        String[] split = str.split("/");
        for (int i = 0; i < split.length; i++) {
            if (fromTreeUri.findFile(split[i]) != null) {
                fromTreeUri = fromTreeUri.findFile(split[i]);
            } else if (i < split.length - 1) {
                if (!z2) {
                    return null;
                }
                fromTreeUri = fromTreeUri.createDirectory(split[i]);
            } else if (!z) {
                return fromTreeUri.createFile("image", split[i]);
            } else {
                fromTreeUri = fromTreeUri.createDirectory(split[i]);
            }
        }
        return fromTreeUri;
    }

    private static Uri getTreeUri(Context context) {
        String string = MyApp.getInstance().preferences.getString(context.getString(R.string.preference_internal_uri_extsdcard_photos), null);
        if (string == null) {
            return null;
        }
        return Uri.parse(string);
    }

    public static void saveSdCardInfo(Context context, Uri uri) {
        MyApp.getInstance().preferences.edit().putString(context.getString(R.string.preference_internal_uri_extsdcard_photos), uri == null ? null : uri.toString()).apply();
        MyApp.getInstance().preferences.edit().putString("sd_card_path", getSdcardPath(context)).apply();
    }

    private static String getSavedSdcardPath(Context context) {
        return MyApp.getInstance().preferences.getString("sd_card_path", null);
    }

    public static String getMediaPath(Context context, Uri uri) {
        int i = 0;
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if (PRIMARY_VOLUME_NAME.equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else {
                if (isMediaDocument(uri)) {
                    String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str = split2[0];
                    if ("image".equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
                }
            }
        } else if ("downloads".equals(uri.getAuthority())) {
            String[] split3 = uri.toString().split("/");
            return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(split3[split3.length - 1]).longValue()), null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (query == null || !query.moveToFirst()) {
                if (query != null) {
                    query.close();
                    return null;
                }
                return null;
            }
            String string = query.getString(query.getColumnIndexOrThrow("_data"));
            if (query != null) {
                query.close();
            }
            return string;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                th2.printStackTrace();
                return str;
            }
        }
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
