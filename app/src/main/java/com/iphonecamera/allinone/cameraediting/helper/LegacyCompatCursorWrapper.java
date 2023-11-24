package com.iphonecamera.allinone.cameraediting.helper;

import android.database.Cursor;
import android.database.CursorWrapper;
import java.util.Arrays;


public class LegacyCompatCursorWrapper extends CursorWrapper {
    final int fakeDataColumn;
    final int fakeMimeTypeColumn;
    private final String mimeType;

    public LegacyCompatCursorWrapper(Cursor cursor) {
        this(cursor, null);
    }

    public LegacyCompatCursorWrapper(Cursor cursor, String str) {
        super(cursor);
        if (cursor.getColumnIndex("_data") >= 0) {
            this.fakeDataColumn = -1;
        } else {
            this.fakeDataColumn = cursor.getColumnCount();
        }
        if (cursor.getColumnIndex("mime_type") >= 0) {
            this.fakeMimeTypeColumn = -1;
        } else {
            int i = this.fakeDataColumn;
            if (i == -1) {
                this.fakeMimeTypeColumn = cursor.getColumnCount();
            } else {
                this.fakeMimeTypeColumn = i + 1;
            }
        }
        this.mimeType = str;
    }

    @Override 
    public int getColumnCount() {
        int columnCount = super.getColumnCount();
        if (!cursorHasDataColumn()) {
            columnCount++;
        }
        return !cursorHasMimeTypeColumn() ? columnCount + 1 : columnCount;
    }

    @Override 
    public int getColumnIndex(String str) {
        if (cursorHasDataColumn() || !"_data".equalsIgnoreCase(str)) {
            return (cursorHasMimeTypeColumn() || !"mime_type".equalsIgnoreCase(str)) ? super.getColumnIndex(str) : this.fakeMimeTypeColumn;
        }
        return this.fakeDataColumn;
    }

    @Override 
    public String getColumnName(int i) {
        return i == this.fakeDataColumn ? "_data" : i == this.fakeMimeTypeColumn ? "mime_type" : super.getColumnName(i);
    }

    @Override 
    public String[] getColumnNames() {
        if (cursorHasDataColumn() && cursorHasMimeTypeColumn()) {
            return super.getColumnNames();
        }
        String[] strArr = (String[]) Arrays.copyOf(super.getColumnNames(), getColumnCount());
        if (!cursorHasDataColumn()) {
            strArr[this.fakeDataColumn] = "_data";
        }
        if (!cursorHasMimeTypeColumn()) {
            strArr[this.fakeMimeTypeColumn] = "mime_type";
        }
        return strArr;
    }

    @Override 
    public String getString(int i) {
        if (cursorHasDataColumn() || i != this.fakeDataColumn) {
            return (cursorHasMimeTypeColumn() || i != this.fakeMimeTypeColumn) ? super.getString(i) : this.mimeType;
        }
        return null;
    }

    @Override 
    public int getType(int i) {
        if (cursorHasDataColumn() || i != this.fakeDataColumn) {
            if (cursorHasMimeTypeColumn() || i != this.fakeMimeTypeColumn) {
                return super.getType(i);
            }
            return Cursor.FIELD_TYPE_STRING;
        }
        return Cursor.FIELD_TYPE_STRING;
    }

    private boolean cursorHasDataColumn() {
        return this.fakeDataColumn == -1;
    }

    private boolean cursorHasMimeTypeColumn() {
        return this.fakeMimeTypeColumn == -1;
    }
}
