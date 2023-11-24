package com.iphonecamera.allinone.cameraediting.activity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import com.iphonecamera.allinone.cameraediting.filter.FilterMode;
import com.iphonecamera.allinone.cameraediting.filter.SortingMode;
import com.iphonecamera.allinone.cameraediting.filter.SortingOrder;

public class AlbumSettings implements Serializable, Parcelable {
    public String coverPath;
    public FilterMode filterMode;
    public boolean pinned;
    public int sortingMode;
    public int sortingOrder;

    @Override
    public int describeContents() {
        return 0;
    }

    public static AlbumSettings getDefaults() {
        return new AlbumSettings(null, SortingMode.NAME.getValue(), 1, 0);
    }

    AlbumSettings(String str, int i, int i2, int i3) {
        this.filterMode = FilterMode.ALL;
        this.coverPath = str;
        this.sortingMode = i;
        this.sortingOrder = i2;
        this.pinned = i3 == 1;
    }

    public SortingMode getSortingMode() {
        return SortingMode.fromValue(this.sortingMode);
    }

    public SortingOrder getSortingOrder() {
        return SortingOrder.fromValue(this.sortingOrder);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.coverPath);
        parcel.writeInt(this.sortingMode);
        parcel.writeInt(this.sortingOrder);
        parcel.writeByte(this.pinned ? (byte) 1 : (byte) 0);
        FilterMode filterMode = this.filterMode;
        parcel.writeInt(filterMode == null ? -1 : filterMode.ordinal());
    }

    protected AlbumSettings(Parcel parcel) {
        this.filterMode = FilterMode.ALL;
        this.coverPath = parcel.readString();
        this.sortingMode = parcel.readInt();
        this.sortingOrder = parcel.readInt();
        this.pinned = parcel.readByte() != 0;
        int readInt = parcel.readInt();
        this.filterMode = readInt == -1 ? null : FilterMode.values()[readInt];
    }

    public static final Creator<AlbumSettings> CREATOR = new Creator<AlbumSettings>() {
        @Override
        public AlbumSettings createFromParcel(Parcel parcel) {
            return new AlbumSettings(parcel);
        }

        @Override
        public AlbumSettings[] newArray(int i) {
            return new AlbumSettings[i];
        }
    };
}
