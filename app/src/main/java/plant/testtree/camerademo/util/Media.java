package plant.testtree.camerademo.util;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.bumptech.glide.signature.ObjectKey;
import java.io.File;
import java.io.IOException;

import plant.testtree.camerademo.activity.gallary.CursorHandler;

/* loaded from: classes.dex */
public class Media implements TimelineItem, CursorHandler, Parcelable {
    private long dateModified;
    private double latitude;
    private double longitude;
    private String mimeType;
    private int orientation;
    public String path;
    private boolean selected;
    private long size;
    private String uriString;
    public static final Creator<Media> CREATOR = new Creator<Media>() { // from class: com.cameraediter.iphone11pro.utils.Media.1
        @Override // android.os.Parcelable.Creator
        public Media createFromParcel(Parcel parcel) {
            return new Media(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Media[] newArray(int i) {
            return new Media[i];
        }
    };
    private static final String[] sProjection = {"_data", "datetaken", "mime_type", "_size", "orientation", "latitude", "longitude"};
    private static final int CURSOR_POS_DATA = ArrayUtils.getIndex(sProjection, "_data");
    private static final int CURSOR_POS_DATE_TAKEN = ArrayUtils.getIndex(sProjection, "datetaken");
    private static final int CURSOR_POS_LATITUDE = ArrayUtils.getIndex(sProjection, "latitude");
    private static final int CURSOR_POS_LONGITUDE = ArrayUtils.getIndex(sProjection, "longitude");
    private static final int CURSOR_POS_MIME_TYPE = ArrayUtils.getIndex(sProjection, "mime_type");
    private static final int CURSOR_POS_ORIENTATION = ArrayUtils.getIndex(sProjection, "orientation");
    private static final int CURSOR_POS_SIZE = ArrayUtils.getIndex(sProjection, "_size");

    @Deprecated
    private long getDateTaken() {
        return 1L;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.cameraediter.iphone11pro.timeline.TimelineItem
    public int getTimelineType() {
        return 102;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double d) {
        this.latitude = d;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double d) {
        this.longitude = d;
    }

    public Media() {
        this.path = null;
        this.dateModified = -1L;
        this.mimeType = MimeTypeUtils.UNKNOWN_MIME_TYPE;
        this.orientation = 0;
        this.latitude = 0.0d;
        this.longitude = 0.0d;
        this.uriString = null;
        this.size = -1L;
        this.selected = false;
    }

    public Media(String str, long j) {
        this.path = null;
        this.dateModified = -1L;
        this.mimeType = MimeTypeUtils.UNKNOWN_MIME_TYPE;
        this.orientation = 0;
        this.latitude = 0.0d;
        this.longitude = 0.0d;
        this.uriString = null;
        this.size = -1L;
        this.selected = false;
        this.path = str;
        this.dateModified = j;
        this.mimeType = MimeTypeUtils.getMimeType(str);
    }

    public Media(File file) {
        this(file.getPath(), file.lastModified());
        Float valueOf;
        Float valueOf2;
        this.size = file.length();
        this.mimeType = MimeTypeUtils.getMimeType(this.path);
        try {
            ExifInterface exifInterface = new ExifInterface(this.path);
            String attribute = exifInterface.getAttribute(androidx.exifinterface.media.ExifInterface.TAG_GPS_LATITUDE);
            String attribute2 = exifInterface.getAttribute(androidx.exifinterface.media.ExifInterface.TAG_GPS_LATITUDE_REF);
            String attribute3 = exifInterface.getAttribute(androidx.exifinterface.media.ExifInterface.TAG_GPS_LONGITUDE);
            String attribute4 = exifInterface.getAttribute(androidx.exifinterface.media.ExifInterface.TAG_GPS_LONGITUDE_REF);
            if (attribute == null || attribute2 == null || attribute3 == null || attribute4 == null) {
                return;
            }
            if (attribute2.equals("N")) {
                valueOf = convertToDegree(attribute);
            } else {
                valueOf = Float.valueOf(0.0f - convertToDegree(attribute).floatValue());
            }
            if (attribute4.equals(androidx.exifinterface.media.ExifInterface.LONGITUDE_EAST)) {
                valueOf2 = convertToDegree(attribute3);
            } else {
                valueOf2 = Float.valueOf(0.0f - convertToDegree(attribute3).floatValue());
            }
            this.latitude = valueOf.floatValue();
            this.longitude = valueOf2.floatValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Float convertToDegree(String str) {
        String[] split = str.split(",", 3);
        String[] split2 = split[0].split("/", 2);
        Double valueOf = Double.valueOf(new Double(split2[0]).doubleValue() / new Double(split2[1]).doubleValue());
        String[] split3 = split[1].split("/", 2);
        Double valueOf2 = Double.valueOf(new Double(split3[0]).doubleValue() / new Double(split3[1]).doubleValue());
        String[] split4 = split[2].split("/", 2);
        return new Float(valueOf.doubleValue() + (valueOf2.doubleValue() / 60.0d) + (Double.valueOf(new Double(split4[0]).doubleValue() / new Double(split4[1]).doubleValue()).doubleValue() / 3600.0d));
    }

    public Media(String str) {
        this(str, -1L);
    }

    public Media(Uri uri) {
        this.path = null;
        this.dateModified = -1L;
        this.mimeType = MimeTypeUtils.UNKNOWN_MIME_TYPE;
        this.orientation = 0;
        this.latitude = 0.0d;
        this.longitude = 0.0d;
        this.uriString = null;
        this.size = -1L;
        this.selected = false;
        this.uriString = uri.toString();
        this.path = null;
        this.mimeType = MimeTypeUtils.getMimeType(this.uriString);
    }

    public Media(Cursor cursor) {
        this.path = null;
        this.dateModified = -1L;
        this.mimeType = MimeTypeUtils.UNKNOWN_MIME_TYPE;
        this.orientation = 0;
        this.latitude = 0.0d;
        this.longitude = 0.0d;
        this.uriString = null;
        this.size = -1L;
        this.selected = false;
        this.path = cursor.getString(CURSOR_POS_DATA);
        this.dateModified = cursor.getLong(CURSOR_POS_DATE_TAKEN);
        this.mimeType = cursor.getString(CURSOR_POS_MIME_TYPE);
        this.size = cursor.getLong(CURSOR_POS_SIZE);
        this.orientation = cursor.getInt(CURSOR_POS_ORIENTATION);
        this.latitude = cursor.getDouble(CURSOR_POS_LATITUDE);
        this.longitude = cursor.getDouble(CURSOR_POS_LONGITUDE);
    }

    @Override // com.cameraediter.iphone11pro.utils.CursorHandler
    public Media handle(Cursor cursor) {
        return new Media(cursor);
    }

    public static String[] getProjection() {
        return sProjection;
    }

    public void setUri(String str) {
        this.uriString = str;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public boolean setSelected(boolean z) {
        if (this.selected == z) {
            return false;
        }
        this.selected = z;
        return true;
    }

    public boolean toggleSelected() {
        this.selected = !this.selected;
        return this.selected;
    }

    public boolean isGif() {
        return this.mimeType.endsWith("gif");
    }

    public boolean isImage() {
        return this.mimeType.startsWith("image");
    }

    public boolean isVideo() {
        return this.mimeType.startsWith("video");
    }

    public Uri getUri() {
        String str = this.uriString;
        return str != null ? Uri.parse(str) : Uri.fromFile(new File(this.path));
    }

    public String getDisplayPath() {
        String str = this.path;
        return str != null ? str : getUri().getEncodedPath();
    }

    public String getName() {
        return StringUtils.getPhotoNameByPath(this.path);
    }

    public long getSize() {
        return this.size;
    }

    public String getPath() {
        return this.path;
    }

    public Long getDateModified() {
        return Long.valueOf(this.dateModified);
    }

    public ObjectKey getSignature() {
        return new ObjectKey(getDateModified() + getPath() + getOrientation());
    }

    public int getOrientation() {
        return this.orientation;
    }

    @Deprecated
    public Bitmap getBitmap() {
        Bitmap decodeFile = BitmapFactory.decodeFile(this.path, new BitmapFactory.Options());
        return Bitmap.createScaledBitmap(decodeFile, decodeFile.getWidth(), decodeFile.getHeight(), true);
    }

    @Deprecated
    public boolean setOrientation(final int i) {
        this.orientation = i;
        new Thread(new Runnable() { // from class: com.cameraediter.iphone11pro.utils.Media.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    ExifInterface exifInterface = new ExifInterface(Media.this.path);
                    int i2 = i != 0 ? i != 90 ? i != 180 ? i != 270 ? -1 : 8 : 3 : 6 : 1;
                    if (i2 != -1) {
                        exifInterface.setAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, String.valueOf(i2));
                        exifInterface.saveAttributes();
                    }
                } catch (IOException unused) {
                }
            }
        }).start();
        return true;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Media) {
            return getPath().equals(((Media) obj).getPath());
        }
        return super.equals(obj);
    }

    @Deprecated
    public boolean fixDate() {
        long dateTaken = getDateTaken();
        if (dateTaken == -1 || !new File(this.path).setLastModified(dateTaken)) {
            return false;
        }
        this.dateModified = dateTaken;
        return true;
    }

    public File getFile() {
        String str = this.path;
        if (str != null) {
            File file = new File(str);
            if (file.exists()) {
                return file;
            }
            return null;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.path);
        parcel.writeLong(this.dateModified);
        parcel.writeString(this.mimeType);
        parcel.writeInt(this.orientation);
        parcel.writeString(this.uriString);
        parcel.writeLong(this.size);
        parcel.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected Media(Parcel parcel) {
        this.path = null;
        this.dateModified = -1L;
        this.mimeType = MimeTypeUtils.UNKNOWN_MIME_TYPE;
        this.orientation = 0;
        this.latitude = 0.0d;
        this.longitude = 0.0d;
        this.uriString = null;
        this.size = -1L;
        this.selected = false;
        this.path = parcel.readString();
        this.dateModified = parcel.readLong();
        this.mimeType = parcel.readString();
        this.orientation = parcel.readInt();
        this.uriString = parcel.readString();
        this.size = parcel.readLong();
        this.selected = parcel.readByte() != 0;
    }
}
