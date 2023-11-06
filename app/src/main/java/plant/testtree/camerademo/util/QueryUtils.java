package plant.testtree.camerademo.util;

import android.content.ContentResolver;
import android.database.Cursor;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import plant.testtree.camerademo.activity.gallary.CursorHandler;

/* loaded from: classes.dex */
public class QueryUtils {
    public static <T> Observable<T> query(final Query query, final ContentResolver contentResolver, final CursorHandler<T> cursorHandler) {
        return Observable.create(new ObservableOnSubscribe<T>() { // from class: com.cameraediter.iphone11pro.utils.QueryUtils.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<T> observableEmitter) throws Exception {
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
            }
        });
    }

    public static <T> Observable<T> querySingle(final Query query, final ContentResolver contentResolver, final CursorHandler<T> cursorHandler) {
        return Observable.create(new ObservableOnSubscribe<T>() { // from class: com.cameraediter.iphone11pro.utils.QueryUtils.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(ObservableEmitter<T> observableEmitter) throws Exception {
                Cursor cursor = null;
                try {
                    cursor = query.getCursor(contentResolver);
                    if (cursor != null && cursor.moveToFirst()) {
                        observableEmitter.onNext(cursorHandler.handle(cursor));
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
            }
        });
    }
}
