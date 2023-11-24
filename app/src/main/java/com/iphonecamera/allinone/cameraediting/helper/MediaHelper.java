package com.iphonecamera.allinone.cameraediting.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import com.iphonecamera.allinone.cameraediting.activity.gallary.Album;
import com.iphonecamera.allinone.cameraediting.model.Media;
import com.iphonecamera.allinone.cameraediting.util.ProgressException;
import com.iphonecamera.allinone.cameraediting.util.StringUtils;

import java.io.File;
import java.util.ArrayList;


public class MediaHelper {
    public static Uri external = MediaStore.Files.getContentUri("external");

    public static void lambda$null$2(Media media) throws Exception {
    }

    public static Observable<Media> deleteMedia(final Activity context, final Media media) {
   
        return Observable.create(observableEmitter -> {
            try {
                observableEmitter.onNext(media);
            } catch (Exception e) {
                observableEmitter.onError(e);
            }
            observableEmitter.onComplete();
        });
    }
   
    public static class AnonymousClass2 implements ObservableOnSubscribe {
        final Album val$album;
        final  Activity val$context;

        AnonymousClass2(Album album, Activity context) {
            this.val$album = album;
            this.val$context = context;
        }

        @SuppressLint("CheckResult")
        @Override
        public final void subscribe(final ObservableEmitter observableEmitter) {
            final ArrayList arrayList = new ArrayList(this.val$album.getCount());
            Observable<Media> observeOn = CPHelper.getMedia(this.val$context, this.val$album).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            Consumer<? super Media> consumer = new Consumer() { 
                @Override
                public final void accept(Object obj) {
                    arrayList.add(MediaHelper.deleteMedia(val$context, (Media) obj));
                }
            };
            observableEmitter.getClass();
            observeOn.subscribe(consumer, (Consumer) obj -> observableEmitter.onError((Throwable) obj), () -> {
                Observable subscribeOn = Observable.mergeDelayError(arrayList).observeOn(AndroidSchedulers.mainThread(), true).subscribeOn(Schedulers.newThread());
                Consumer consumer1 = obj -> {
                    try {
                        MediaHelper.lambda$null$2((Media) obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
                observableEmitter.getClass();

                subscribeOn.subscribe(consumer1, obj -> observableEmitter.onError((Throwable) obj), () -> {
                    observableEmitter.onNext(AnonymousClass2.this.val$album);
                    observableEmitter.onComplete();
                });
            });
        }
    }

    public static Observable<Album> deleteAlbum(Activity context, Album album) {
        return Observable.create(new AnonymousClass2(album, context));
    }

    public static boolean internalDeleteMedia(Context context, Media media) throws ProgressException {
        File file = new File(media.getPath());
        StorageHelper.deleteFile(context, file);
        context.getContentResolver().delete(external, "_data=?", new String[]{file.getPath()});
        return true;
    }

    public static boolean renameMedia(Context context, Media media, String str) {
        try {
            File file = new File(media.getPath());
            File file2 = new File(StringUtils.getPhotoPathRenamed(media.getPath(), str));
            boolean moveFile = StorageHelper.moveFile(context, file, file2);
            if (moveFile) {
                try {
                    context.getContentResolver().delete(external, "_data=?", new String[]{file.getPath()});
                    scanFile(context, new String[]{file2.getAbsolutePath()});
                    media.setPath(file2.getAbsolutePath());
                } catch (Exception unused) {
                }
            }
            return moveFile;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean moveMedia(Context context, Media media, String str) {
        try {
            File file = new File(media.getPath());
            boolean moveFile = StorageHelper.moveFile(context, file, new File(str, file.getName()));
            if (moveFile) {
                try {
                    context.getContentResolver().delete(external, "_data=?", new String[]{file.getPath()});
                    scanFile(context, new String[]{StringUtils.getPhotoPathMoved(media.getPath(), str)});
                } catch (Exception unused) {
                }
            }
            return moveFile;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyMedia(Context context, Media media, String str) {
        try {
            boolean copyFile = StorageHelper.copyFile(context, new File(media.getPath()), new File(str));
            if (copyFile) {
                try {
                    scanFile(context, new String[]{StringUtils.getPhotoPathMoved(media.getPath(), str)});
                } catch (Exception unused) {
                }
            }
            return copyFile;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void scanFile(Context context, String[] strArr) {
        MediaScannerConnection.scanFile(context.getApplicationContext(), strArr, null, null);
    }
}
