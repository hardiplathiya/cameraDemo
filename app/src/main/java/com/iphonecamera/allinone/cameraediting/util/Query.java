package com.iphonecamera.allinone.cameraediting.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import java.util.Arrays;
import java.util.function.Function;


public class Query {
    public String[] args;
    public boolean ascending;
    public int limit;
    public String[] projection;
    public String selection;
    public String sort;
    public Uri uri;

   
    public static final class Builder {
        Object[] args = null;
        boolean ascending = false;
        int limit = -1;
        String[] projection = null;
        String selection = null;
        String sort = null;
        Uri uri = null;

        public Builder uri(Uri uri) {
            this.uri = uri;
            return this;
        }

        public Builder projection(String[] strArr) {
            this.projection = strArr;
            return this;
        }

        public Builder selection(String str) {
            this.selection = str;
            return this;
        }

        public Builder args(Object... objArr) {
            this.args = objArr;
            return this;
        }

        public Builder sort(String str) {
            this.sort = str;
            return this;
        }

        public Builder limit(int i) {
            this.limit = i;
            return this;
        }

        public Builder ascending(boolean z) {
            this.ascending = z;
            return this;
        }

        public Query build() {
            return new Query(this);
        }

        public String[] getStringArgs() {
            return (String[]) Arrays.stream(this.args).map((Function) obj -> obj.toString()).toArray(i -> Builder.lambda$getStringArgs$0(i));
        }

        public static String[] lambda$getStringArgs$0(int i) {
            return new String[i];
        }
    }

    Query(Builder builder) {
        this.uri = builder.uri;
        this.projection = builder.projection;
        this.selection = builder.selection;
        this.args = builder.getStringArgs();
        this.sort = builder.sort;
        this.ascending = builder.ascending;
        this.limit = builder.limit;
    }

    public Cursor getCursor(ContentResolver contentResolver) {
        return contentResolver.query(this.uri, this.projection, this.selection, this.args, hack());
    }

    private String hack() {
        if (this.sort == null && this.limit == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String str = this.sort;
        if (str != null) {
            sb.append(str);
        } else {
            sb.append(1);
        }
        sb.append(" ");
        if (!this.ascending) {
            sb.append("DESC");
            sb.append(" ");
        }
        if (this.limit != -1) {
            sb.append("LIMIT");
            sb.append(" ");
            sb.append(this.limit);
        }
        return sb.toString();
    }

    public String toString() {
        return "Query{\nuri=" + this.uri + "\nprojection=" + Arrays.toString(this.projection) + "\nselection='" + this.selection + "'\nargs=" + Arrays.toString(this.args) + "\nsortMode='" + this.sort + "'\nascending='" + this.ascending + "'\nlimit='" + this.limit + "'}";
    }
}
