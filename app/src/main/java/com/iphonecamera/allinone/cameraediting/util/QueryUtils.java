package com.iphonecamera.allinone.cameraediting.util;

import android.content.ContentResolver;
import android.database.Cursor;
import io.reactivex.Observable;

import com.iphonecamera.allinone.cameraediting.activity.gallary.CursorHandler;


public class QueryUtils {
    public static <T> Observable<T> query(final Query query, final ContentResolver contentResolver, final CursorHandler<T> cursorHandler) {
        return Observable.create(observableEmitter -> {
            Cursor cursor = null;
            try {
                cursor = query.getCursor(contentResolver);
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        observableEmitter.onNext(cursorHandler.handle(cursor));
                    }
                }
                observableEmitter.onComplete();
                if (cursor == null) {
                    return;
                }
            } catch (Exception e) {
                observableEmitter.onError(e);
                if (cursor == null) {
                    return;
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
            cursor.close();
        });
    }

}
