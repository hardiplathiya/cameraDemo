package plant.testtree.camerademo.activity.gallary;

import android.database.Cursor;

public interface CursorHandler<T> {

    final class CC {
        public static String[] getProjection() {
            return new String[0];
        }
    }

    T handle(Cursor cursor);
}
