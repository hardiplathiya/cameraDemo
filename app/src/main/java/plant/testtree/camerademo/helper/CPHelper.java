package plant.testtree.camerademo.helper;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import plant.testtree.camerademo.activity.gallary.Album;
import plant.testtree.camerademo.activity.gallary.CursorHandler;
import plant.testtree.camerademo.filter.ImageFileFilter;
import plant.testtree.camerademo.filter.SortingMode;
import plant.testtree.camerademo.filter.SortingOrder;
import plant.testtree.camerademo.model.Media;
import plant.testtree.camerademo.util.Prefs;
import plant.testtree.camerademo.util.Query;
import plant.testtree.camerademo.util.QueryUtils;

import java.io.File;


public class CPHelper {
    public static Observable<Media> getMedia(Context context, Album album) {
        if (album.getId() == -1) {
            return getMediaFromStorage(context, album);
        }
        if (album.getId() == Album.ALL_MEDIA_ALBUM_ID) {
            return getAllMediaFromMediaStore(context, album.settings.getSortingMode(), album.settings.getSortingOrder());
        }
        return getMediaFromMediaStore(context, album, album.settings.getSortingMode(), album.settings.getSortingOrder());
    }

    private static Observable<Media> getMediaFromStorage(Context context, final Album album) {
        return Observable.create((ObservableOnSubscribe) observableEmitter -> {
            File[] listFiles = new File(album.getPath()).listFiles(new ImageFileFilter(Prefs.showVideos()));
            if (listFiles != null) {
                try {
                    if (listFiles.length > 0) {
                        for (File file : listFiles) {
                            observableEmitter.onNext(new Media(file));
                        }
                    }
                } catch (Exception e) {
                    observableEmitter.onError(e);
                    return;
                }
            }
            observableEmitter.onComplete();
        });
    }

    private static Observable<Media> getAllMediaFromMediaStore(Context context, SortingMode sortingMode, SortingOrder sortingOrder) {
        Query.Builder ascending = new Query.Builder().uri(MediaStore.Files.getContentUri("external")).projection(Media.getProjection()).sort(sortingMode.getMediaColumn()).ascending(sortingOrder.isAscending());
        if (Prefs.showVideos()) {
            ascending.selection(String.format("(%s=? or %s=?)", "media_type", "media_type"));
            ascending.args(1, 3);
        } else {
            ascending.selection(String.format("%s=?", "media_type"));
            ascending.args(1);
        }
        return QueryUtils.query(ascending.build(), context.getContentResolver(), new Media());
    }

    private static Observable<Media> getMediaFromMediaStore(Context context, Album album, SortingMode sortingMode, SortingOrder sortingOrder) {
        Query.Builder ascending = new Query.Builder().uri(MediaStore.Files.getContentUri("external")).projection(Media.getProjection()).sort(sortingMode.getMediaColumn()).ascending(sortingOrder.isAscending());
        if (Prefs.showVideos()) {
            ascending.selection(String.format("(%s=? or %s=?) and %s=?", "media_type", "media_type", "parent"));
            ascending.args(1, 3, Long.valueOf(album.getId()));
        } else {
            ascending.selection(String.format("%s=? and %s=?", "media_type", "parent"));
            ascending.args(1, Long.valueOf(album.getId()));
        }
        return QueryUtils.query(ascending.build(), context.getContentResolver(), (CursorHandler) cursor -> new Media(cursor));
    }
}
