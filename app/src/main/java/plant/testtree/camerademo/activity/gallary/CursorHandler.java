package plant.testtree.camerademo.activity.gallary;

import android.database.Cursor;

/* loaded from: classes.dex */
public interface CursorHandler<T> {

    /* loaded from: classes.dex */
    public static final class CC {
        public static String[] getProjection() {
            return new String[0];
        }
    }

    T handle(Cursor cursor);
}
