package plant.testtree.camerademo.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class MetaDataItem {
    private static final int ORIENTATION_NORMAL = 1;
    private static final int ORIENTATION_ROTATE_180 = 3;
    private static final int ORIENTATION_ROTATE_270 = 8;
    private static final int ORIENTATION_ROTATE_90 = 6;
    private static final String TAG = "MetaData";
    private String exposureTime;
    private Date dateOriginal = null;
    private String fNumber = null;
    private int height = -1;
    private String iso = null;
    private GeoLocation location = null;
    private String make = null;
    private String model = null;
    private int orientation = -1;
    private int width = -1;

    public static MetaDataItem getMetadata(Context context, Uri uri) {
        return new MetaDataItem(context, uri);
    }

    private MetaDataItem(Context context, Uri uri) {
        load(context, uri);
    }

    private void load(Context context, Uri uri) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                if (openInputStream != null) {
                    openInputStream.close();
                    return;
                }
                return;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(openInputStream, null, options);
            this.width = options.outWidth;
            this.height = options.outHeight;
            if (openInputStream != null) {
                openInputStream.close();
            }
            try {
                InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
                if (openInputStream2 == null) {
                    if (openInputStream2 != null) {
                        openInputStream2.close();
                        return;
                    }
                    return;
                }
                Metadata readMetadata = ImageMetadataReader.readMetadata(openInputStream2);
                handleDirectoryBase((ExifDirectoryBase) readMetadata.getFirstDirectoryOfType(ExifIFD0Directory.class));
                ExifSubIFDDirectory exifSubIFDDirectory = (ExifSubIFDDirectory) readMetadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                if (exifSubIFDDirectory != null) {
                    this.dateOriginal = exifSubIFDDirectory.getDateOriginal(TimeZone.getDefault());
                    handleDirectoryBase(exifSubIFDDirectory);
                }
                GpsDirectory gpsDirectory = (GpsDirectory) readMetadata.getFirstDirectoryOfType(GpsDirectory.class);
                if (gpsDirectory != null) {
                    this.location = gpsDirectory.getGeoLocation();
                }
                if (openInputStream2 != null) {
                    openInputStream2.close();
                }
            } catch (ImageProcessingException e) {
                Log.e(TAG, "loadMetadata -> file type not supported", e);
                throw null;
            } catch (FileNotFoundException e2) {
                Log.e(TAG, "loadMetadata -> file not found", e2);
                throw null;
            } catch (IOException e3) {
                Log.e(TAG, "loadMetadata -> IOException", e3);
                throw null;
            } catch (Throwable unused) {
                throw null;
            }
        } catch (FileNotFoundException e4) {
            Log.e(TAG, "loadMetadata -> file not found", e4);
        } catch (IOException e5) {
            Log.e(TAG, "loadMetadata -> IOException", e5);
        } catch (Throwable unused2) {
        }
    }

    private void handleDirectoryBase(ExifDirectoryBase exifDirectoryBase) {
        if (exifDirectoryBase != null) {
            if (exifDirectoryBase.containsTag(271)) {
                this.make = exifDirectoryBase.getString(271);
            }
            if (exifDirectoryBase.containsTag(272)) {
                this.model = exifDirectoryBase.getString(272);
            }
            if (exifDirectoryBase.containsTag(ExifDirectoryBase.TAG_ISO_EQUIVALENT)) {
                this.iso = exifDirectoryBase.getString(ExifDirectoryBase.TAG_ISO_EQUIVALENT);
            }
            if (exifDirectoryBase.containsTag(ExifDirectoryBase.TAG_EXPOSURE_TIME) && exifDirectoryBase.getRational(ExifDirectoryBase.TAG_EXPOSURE_TIME) != null) {
                this.exposureTime = new DecimalFormat("0.000").format(exifDirectoryBase.getRational(ExifDirectoryBase.TAG_EXPOSURE_TIME));
            }
            if (exifDirectoryBase.containsTag(ExifDirectoryBase.TAG_FNUMBER)) {
                this.fNumber = exifDirectoryBase.getString(ExifDirectoryBase.TAG_FNUMBER);
            }
            if (exifDirectoryBase.containsTag(ExifDirectoryBase.TAG_DATETIME_ORIGINAL)) {
                this.dateOriginal = exifDirectoryBase.getDate(ExifDirectoryBase.TAG_DATETIME_ORIGINAL);
            }
        }
    }

    public String getResolution() {
        return (this.width == -1 || -1 == this.height) ? "¿x?" : String.format(Locale.getDefault(), "%dx%d", Integer.valueOf(this.width), Integer.valueOf(this.height));
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int i) {
        if (i != 1) {
            if (i != 3 && i != 6) {
                if (i != 8) {
                    return;
                }
                this.orientation = 270;
            }
            this.orientation = 180;
            this.orientation = 270;
        }
        this.orientation = 0;
        this.orientation = 90;
        this.orientation = 180;
        this.orientation = 270;
    }

    public Date getDateOriginal() {
        return this.dateOriginal;
    }

    public GeoLocation getLocation() {
        return this.location;
    }

    public String getCameraInfo() {
        String str;
        String str2 = this.make;
        if (str2 == null || (str = this.model) == null) {
            return null;
        }
        return str.contains(str2) ? this.model : String.format("%s %s", this.make, this.model);
    }

    public String getExifInfo() {
        StringBuilder sb = new StringBuilder();
        String str = getfNumber();
        if (str != null) {
            sb.append(str);
            sb.append(" ");
        }
        String exposureTime = getExposureTime();
        if (exposureTime != null) {
            sb.append(exposureTime);
            sb.append(" ");
        }
        String iso = getIso();
        if (iso != null) {
            sb.append(iso);
            sb.append(" ");
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }

    private String getfNumber() {
        String str = this.fNumber;
        if (str == null) {
            return null;
        }
        return String.format("f/%s", str);
    }

    private String getIso() {
        String str = this.iso;
        if (str == null) {
            return null;
        }
        return String.format("ISO-%s", str);
    }

    private String getExposureTime() {
        String str = this.exposureTime;
        if (str == null) {
            return null;
        }
        return String.format("%ss", str);
    }
}
