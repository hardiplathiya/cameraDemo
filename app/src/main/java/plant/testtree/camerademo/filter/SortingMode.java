package plant.testtree.camerademo.filter;

/* loaded from: classes.dex */
public enum SortingMode {
    NAME(0, "_display_name", "bucket_display_name"),
    DATE(1, "date_modified", "max(date_modified)"),
    SIZE(2, "_size", "count(*)"),
    TYPE(3, "mime_type"),
    NUMERIC(4, "_display_name", "bucket_display_name");
    
    String albumsColumn;
    String mediaColumn;
    int value;

    SortingMode(int i, String str) {
        this.value = i;
        this.mediaColumn = str;
        this.albumsColumn = str;
    }

    SortingMode(int i, String str, String str2) {
        this.value = i;
        this.mediaColumn = str;
        this.albumsColumn = str2;
    }

    public String getMediaColumn() {
        return this.mediaColumn;
    }

    public String getAlbumsColumn() {
        return this.albumsColumn;
    }

    public int getValue() {
        return this.value;
    }

    public static SortingMode fromValue(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return NUMERIC;
                    }
                    return NAME;
                }
                return TYPE;
            }
            return SIZE;
        }
        return DATE;
    }
}
