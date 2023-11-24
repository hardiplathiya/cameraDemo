package com.iphonecamera.allinone.cameraediting.activity.gallary;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;

import com.iphonecamera.allinone.cameraediting.filter.FilterMode;
import com.iphonecamera.allinone.cameraediting.filter.SortingMode;
import com.iphonecamera.allinone.cameraediting.filter.SortingOrder;
import com.iphonecamera.allinone.cameraediting.model.Media;
import com.iphonecamera.allinone.cameraediting.activity.AlbumSettings;
import com.iphonecamera.allinone.cameraediting.util.StringUtils;

public class Album implements CursorHandler, Parcelable {
    public static final long ALL_MEDIA_ALBUM_ID = 8000;
    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel parcel) {
            return new Album(parcel);
        }

        @Override
        public Album[] newArray(int i) {
            return new Album[i];
        }
    };
    private int count;
    private long dateModified;
    private boolean found_id_album;
    private long id;
    private Media lastMedia;
    private String name;
    private String path;
    private boolean selected;
    public AlbumSettings settings;

    @Override
    @Deprecated
    public int describeContents() {
        return 0;
    }

    @Deprecated
    public int moveSelectedMedia(Context context, String str) {
        return -1;
    }

    public boolean renameAlbum(Context context, String str) {
        return false;
    }

    public void sortPhotos() {
    }

    public Album(String str, String str2) {
        this.id = -1L;
        this.count = -1;
        this.selected = false;
        this.settings = null;
        this.lastMedia = null;
        this.found_id_album = false;
        this.name = str2;
        this.path = str;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public Album(String str, long j) {
        this.id = -1L;
        this.count = -1;
        this.selected = false;
        this.settings = null;
        this.lastMedia = null;
        this.found_id_album = false;
        this.name = str;
        this.id = j;
    }

    public Album(String str, String str2, long j, int i, long j2) {
        this(str, str2);
        this.count = i;
        this.id = j;
        this.dateModified = j2;
    }

    public Album(String str, String str2, int i, long j) {
        this(str, str2, -1L, i, j);
    }

    public Album(Cursor cursor) {
        this(StringUtils.getBucketPathByImagePath(cursor.getString(3)), cursor.getString(1), cursor.getLong(0), cursor.getInt(2), cursor.getLong(4));
        setLastMedia(new Media(cursor.getString(3)));
    }

    @Override
    public Album handle(Cursor cursor) {
        return new Album(cursor);
    }

    @Deprecated
    public Album(Context context, String str, long j, String str2, int i) {
        this(str, str2, j, i, 0L);
        this.settings = AlbumSettings.getDefaults();
    }

    public static Album getEmptyAlbum() {
        String str = null;
        Album album = new Album(str, str);
        album.settings = AlbumSettings.getDefaults();
        return album;
    }

    public static Album getAllMediaAlbum() {
        Album album = new Album("All Media", (long) ALL_MEDIA_ALBUM_ID);
        album.settings = AlbumSettings.getDefaults();
        return album;
    }

    static Album withPath(String str) {
        Album emptyAlbum = getEmptyAlbum();
        emptyAlbum.path = str;
        return emptyAlbum;
    }

    public Album withSettings(AlbumSettings albumSettings) {
        this.settings = albumSettings;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public int getCount() {
        return this.count;
    }

    public Long getDateModified() {
        return Long.valueOf(this.dateModified);
    }

    public Media getCover() {
        if (hasCover()) {
            return new Media(this.settings.coverPath);
        }
        Media media = this.lastMedia;
        return media != null ? media : new Media();
    }

    public void setLastMedia(Media media) {
        this.lastMedia = media;
    }

    public void setCover(String str) {
        this.settings.coverPath = str;
    }

    public long getId() {
        return this.id;
    }

    public boolean isHidden() {
        return new File(this.path, ".nomedia").exists();
    }

    public boolean isPinned() {
        return this.settings.pinned;
    }

    public boolean hasCover() {
        return this.settings.coverPath != null;
    }

    public FilterMode filterMode() {
        AlbumSettings albumSettings = this.settings;
        return albumSettings != null ? albumSettings.filterMode : FilterMode.ALL;
    }

    public void setFilterMode(FilterMode filterMode) {
        this.settings.filterMode = filterMode;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public String toString() {
        return "Album{name='" + this.name + "', path='" + this.path + "', id=" + this.id + ", count=" + this.count + '}';
    }

    public ArrayList<String> getParentsFolders() {
        ArrayList<String> arrayList = new ArrayList<>();
        for (File file = new File(getPath()); file != null && file.canRead(); file = file.getParentFile()) {
            arrayList.add(file.getPath());
        }
        return arrayList;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public void setName(String str) {
        this.name = str;
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

    public void removeCoverAlbum() {
        this.settings.coverPath = null;
    }

    public void setSortingMode(SortingMode sortingMode) {
        this.settings.sortingMode = sortingMode.getValue();
    }

    public void setSortingOrder(SortingOrder sortingOrder) {
        this.settings.sortingOrder = sortingOrder.getValue();
    }

    public boolean togglePinAlbum() {
        AlbumSettings albumSettings = this.settings;
        albumSettings.pinned = !albumSettings.pinned;
        return this.settings.pinned;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Album) {
            return this.path.equals(((Album) obj).getPath());
        }
        return super.equals(obj);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.path);
        parcel.writeLong(this.id);
        parcel.writeLong(this.dateModified);
        parcel.writeInt(this.count);
        parcel.writeByte(this.selected ? (byte) 1 : (byte) 0);
        parcel.writeSerializable(this.settings);
        parcel.writeParcelable(this.lastMedia, i);
    }

    protected Album(Parcel parcel) {
        this.id = -1L;
        this.count = -1;
        this.selected = false;
        this.settings = null;
        this.lastMedia = null;
        this.found_id_album = false;
        this.name = parcel.readString();
        this.path = parcel.readString();
        this.id = parcel.readLong();
        this.dateModified = parcel.readLong();
        this.count = parcel.readInt();
        this.selected = parcel.readByte() != 0;
        this.settings = (AlbumSettings) parcel.readSerializable();
        this.lastMedia = (Media) parcel.readParcelable(Media.class.getClassLoader());
    }
}
