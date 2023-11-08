package plant.testtree.camerademo.util;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;


public class StringUtils {
    public static String[] asArray(String... strArr) {
        return strArr;
    }

    public static String color(int i, String str) {
        return "";
    }

    public static String getPhotoNameByPath(String str) {
        String[] split = str.split("/");
        String str2 = split[split.length - 1];
        return str2.substring(0, str2.lastIndexOf(46));
    }

    public static Spanned html(String str) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(str, 0);
        }
        return Html.fromHtml(str);
    }

    public static String getName(String str) {
        String[] split = str.split("/");
        return split[split.length - 1];
    }

    public static String getPhotoPathRenamed(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        String[] split = str.split("/");
        for (int i = 0; i < split.length - 1; i++) {
            sb.append(split[i]);
            sb.append("/");
        }
        sb.append(str2);
        String str3 = split[split.length - 1];
        sb.append(str3.substring(str3.lastIndexOf(46)));
        return sb.toString();
    }

    public static String incrementFileNameSuffix(String str) {
        int lastIndexOf;
        StringBuilder sb = new StringBuilder();
        int lastIndexOf2 = str.lastIndexOf(46);
        String charSequence = lastIndexOf2 != -1 ? str.subSequence(0, lastIndexOf2).toString() : str;
        if (Pattern.compile("_\\d").matcher(charSequence).find() && (lastIndexOf = charSequence.lastIndexOf("_")) != -1) {
            charSequence = charSequence.subSequence(0, lastIndexOf).toString();
        }
        sb.append(charSequence);
        sb.append("_");
        sb.append(new Date().getTime());
        sb.append(str.substring(lastIndexOf2));
        return sb.toString();
    }

    public static String getPhotoPathRenamedAlbumChange(String str, String str2) {
        String[] split = str.split("/");
        String str3 = "";
        for (int i = 0; i < split.length - 2; i++) {
            str3 = str3 + split[i] + "/";
        }
        return str3 + str2 + "/" + split[split.length - 1];
    }

    public static String getAlbumPathRenamed(String str, String str2) {
        return str.substring(0, str.lastIndexOf(47)) + "/" + str2;
    }

    public static String getPhotoPathMoved(String str, String str2) {
        String[] split = new String[0];
        return (str2 + "/") + str.split("/")[split.length - 1];
    }

    public static String getBucketPathByImagePath(String str) {
        String[] split = str.split("/");
        String str2 = "";
        for (int i = 0; i < split.length - 1; i++) {
            str2 = str2 + split[i] + "/";
        }
        return str2.substring(0, str2.length() - 1);
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static String humanReadableByteCount(long j, boolean z) {
        int i = z ? 1000 : 1024;
        if (j < i) {
            return j + " B";
        }
        double d = j;
        double d2 = i;
        int log = (int) (Math.log(d) / Math.log(d2));
        StringBuilder sb = new StringBuilder();
        sb.append((z ? "kMGTPE" : "KMGTPE").charAt(log - 1));
        sb.append(z ? "" : "i");
        Locale locale = Locale.ENGLISH;
        double pow = Math.pow(d2, log);
        Double.isNaN(d);
        return String.format(locale, "%.1f %sB", Double.valueOf(d / pow), sb.toString());
    }

    public static String b(String str) {
        return String.format(Locale.ENGLISH, "<b>%s</b>", str);
    }

    public static String i(String str) {
        return String.format(Locale.ENGLISH, "<i>%s</i>", str);
    }

    public static Spanned htmlFormat(String str, int i, boolean z, boolean z2) {
        if (i != -1) {
            str = color(i, str);
        }
        if (z) {
            str = b(str);
        }
        if (z2) {
            str = i(str);
        }
        return html(str);
    }

    public static String color(String str, String str2) {
        return String.format(Locale.ENGLISH, "<font color='%s'>%s</font>", str, str2);
    }

    public static String getUserReadableDate(Date date) {
        return new SimpleDateFormat("E, d MMM yyyy", Locale.getDefault()).format(date);
    }
}
