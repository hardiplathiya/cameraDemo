package plant.testtree.camerademo.activity.gallary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import plant.testtree.camerademo.R;
import plant.testtree.camerademo.activity.EditImageActivity;
import plant.testtree.camerademo.adapter.ViewPagerAdapter;
import plant.testtree.camerademo.fragment.GifFragment;
import plant.testtree.camerademo.fragment.SingleMediaFragment;
import plant.testtree.camerademo.fragment.VideoFragment;
import plant.testtree.camerademo.helper.LegacyCompatFileProvider;
import plant.testtree.camerademo.helper.MyApp;
import plant.testtree.camerademo.util.CPHelper;
import plant.testtree.camerademo.model.Media;
import plant.testtree.camerademo.util.MediaHelper;
import plant.testtree.camerademo.util.MetaDataItem;
import plant.testtree.camerademo.util.Prefs;
import plant.testtree.camerademo.util.StringUtils;

/* loaded from: classes.dex */
public class GalleryappActivity extends AppCompatActivity {
    private String[] FilePathStrings;
    Float Latitude;
    Float Longitude;
    private Album album;
    CardView cardMenuList;
    File file;
    ViewPager gallery_pager;
    ImageView iv_back;
    ImageView iv_menu;
    private File[] listFile;
    LinearLayout ll_delete;
    LinearLayout ll_detail;
    LinearLayout ll_edit;
    LinearLayout ll_share;
    LinearLayout mAdView;
    MediaPagerAdapter pAdapter;
    RelativeLayout rlHideShow;
    RelativeLayout rlMenuList;
    RelativeLayout rlProgress;
    RelativeLayout rlSlideShow;
    TextView tvDetail;
    TextView tvError;
    TextView tvRename;
    TextView tvRotate;
    TextView tvSetas;
    TextView tvShowOnMap;
    TextView tvSlideshow;
    TextView tvTitleName;
    TextView tvTitleTime;
    ViewPagerAdapter viewAdapter;
    ViewPager vpSlideShow;
    CompositeDisposable disposables = new CompositeDisposable();
    String filename = "";
    String foldername = "iCamera";
    public boolean isImageEdited = false;
    public List<Media> mediaItems = new ArrayList();

    int itemPosition = -1;
    public SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public static void lambda$loadAlbum$6(Throwable th) throws Exception {
    }

    public static void lambda$loadAlbum$7() throws Exception {
    }

    /* loaded from: classes.dex */
    public class MediaPagerAdapter extends FragmentStatePagerAdapter {
        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MediaPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            //  GalleryappActivity.this = r1;
        }

        @Override
        // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.instantiateItem(viewGroup, i);
            GalleryappActivity.this.registeredFragments.put(i, fragment);
            return fragment;
        }

        @Override // androidx.fragment.app.FragmentStatePagerAdapter
        public Fragment getItem(int i) {
            Media media = GalleryappActivity.this.mediaItems.get(i);
            if (media.isVideo()) {
                return VideoFragment.newInstance(media);
            }
            if (media.isGif()) {
                return GifFragment.newInstance(media);
            }
            return SingleMediaFragment.newInstance(GalleryappActivity.this.mediaItems.get(i));
        }

        @Override
        // androidx.fragment.app.FragmentStatePagerAdapter, androidx.viewpager.widget.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            GalleryappActivity.this.registeredFragments.remove(i);
            super.destroyItem(viewGroup, i, obj);
        }

        public Fragment getRegisteredFragment(int i) {
            return GalleryappActivity.this.registeredFragments.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return GalleryappActivity.this.mediaItems.size();
        }

        public void addData(List<Media> Itemlist) {
            mediaItems = new ArrayList<>();
            mediaItems.addAll(Itemlist);
            notifyDataSetChanged();
        }

    }

    public static String formatFileSize(long j) {
        double d = j;
        Double.isNaN(d);
        double d2 = d / 1024.0d;
        double d3 = d2 / 1024.0d;
        double d4 = d3 / 1024.0d;
        double d5 = d4 / 1024.0d;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (d5 > 1.0d) {
            return decimalFormat.format(d5).concat(" TB");
        }
        if (d4 > 1.0d) {
            return decimalFormat.format(d4).concat(" GB");
        }
        if (d3 > 1.0d) {
            return decimalFormat.format(d3).concat(" MB");
        }
        if (d2 > 1.0d) {
            return decimalFormat.format(d2).concat(" KB");
        }
        return decimalFormat.format(d).concat(" Bytes");
    }

    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_gallery1);

        this.gallery_pager = (ViewPager) findViewById(R.id.gallery_pager);
        this.iv_back = (ImageView) findViewById(R.id.iv_back);
        this.iv_menu = (ImageView) findViewById(R.id.iv_menu);
        this.cardMenuList = (CardView) findViewById(R.id.cardMenuList);
        this.rlMenuList = (RelativeLayout) findViewById(R.id.rlMenuList);
        this.tvSlideshow = (TextView) findViewById(R.id.tvSlideshow);
        this.tvRotate = (TextView) findViewById(R.id.tvRotate);
        this.tvRename = (TextView) findViewById(R.id.tvRename);
        this.tvSetas = (TextView) findViewById(R.id.tvSetas);
        this.tvDetail = (TextView) findViewById(R.id.tvDetail);
        this.tvShowOnMap = (TextView) findViewById(R.id.tvShowOnMap);
        this.ll_edit = (LinearLayout) findViewById(R.id.ll_edit);
        this.ll_share = (LinearLayout) findViewById(R.id.ll_share);
        this.ll_delete = (LinearLayout) findViewById(R.id.ll_delete);
        this.ll_detail = (LinearLayout) findViewById(R.id.ll_detail);
        this.tvError = (TextView) findViewById(R.id.tvError);
        this.rlSlideShow = (RelativeLayout) findViewById(R.id.rlSlideShow);
        this.vpSlideShow = (ViewPager) findViewById(R.id.vpSlideShow);
        this.rlHideShow = (RelativeLayout) findViewById(R.id.rlHideShow);
        this.rlProgress = (RelativeLayout) findViewById(R.id.rlProgress);
        this.tvTitleName = (TextView) findViewById(R.id.tvTitleName);
        this.tvTitleTime = (TextView) findViewById(R.id.tvTitleTime);
        MyApp.getInstance().isFromPuzzle = false;
        try {
            Prefs.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getIntent().hasExtra("path")) {
            this.foldername = getIntent().getStringExtra("path");
            this.file = new File(getIntent().getStringExtra("dir"));
            this.filename = getIntent().getStringExtra("name");
        } else {
            this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + this.foldername);
        }
        this.album = new Album(this.file.getAbsolutePath(), this.foldername);
        this.pAdapter = new MediaPagerAdapter(getSupportFragmentManager());
        this.gallery_pager.setAdapter(this.pAdapter);
        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.1
            @Override // java.lang.Runnable
            public void run() {
                GalleryappActivity.this.loadAlbum();
            }
        }, 500L);
        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.2
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < GalleryappActivity.this.mediaItems.size(); i++) {
                    if (GalleryappActivity.this.filename.contains(GalleryappActivity.this.mediaItems.get(i).getName())) {
                        GalleryappActivity.this.gallery_pager.setCurrentItem(i);
                        return;
                    }
                }
            }
        }, 1000L);
        this.gallery_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                Date date = new Date(System.currentTimeMillis() - 86400000);
                Date date2 = new Date(System.currentTimeMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM");
                String format = simpleDateFormat.format(date);
                if (simpleDateFormat.format(date2).equalsIgnoreCase(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString())) {
                    GalleryappActivity.this.tvTitleName.setText("Today");
                    GalleryappActivity.this.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString());
                } else if (format.equalsIgnoreCase(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString())) {
                    GalleryappActivity.this.tvTitleName.setText("Yesterday");
                    GalleryappActivity.this.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString());
                } else {
                    GalleryappActivity.this.tvTitleName.setText(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString());
                    GalleryappActivity.this.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString());
                }
                if (GalleryappActivity.this.mediaItems.get(i).isVideo() || GalleryappActivity.this.mediaItems.get(i).isGif()) {
                    GalleryappActivity.this.ll_edit.setVisibility(View.GONE);
                    GalleryappActivity.this.tvRotate.setVisibility(View.GONE);
                    GalleryappActivity.this.ll_detail.setVisibility(View.VISIBLE);
                    GalleryappActivity.this.tvSetas.setVisibility(View.GONE);
                } else {
                    GalleryappActivity.this.ll_edit.setVisibility(View.VISIBLE);
                    GalleryappActivity.this.tvRotate.setVisibility(View.VISIBLE);
                    GalleryappActivity.this.ll_detail.setVisibility(View.VISIBLE);
                    GalleryappActivity.this.tvSetas.setVisibility(View.VISIBLE);
                }
                if (GalleryappActivity.this.mediaItems.get(i).getLatitude() == 0.0d || GalleryappActivity.this.mediaItems.get(i).getLongitude() == 0.0d) {
                    GalleryappActivity.this.tvShowOnMap.setVisibility(View.GONE);
                } else {
                    GalleryappActivity.this.tvShowOnMap.setVisibility(View.VISIBLE);
                }
            }
        });
        this.iv_back.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryappActivity.this.onBackPressed();
            }
        });
        this.rlMenuList.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalleryappActivity.lambda$onCreate$0(GalleryappActivity.this, view);
            }
        });
        this.iv_menu.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (GalleryappActivity.this.cardMenuList.getVisibility() == View.VISIBLE) {
                    GalleryappActivity.this.cardMenuList.setVisibility(View.GONE);
                    GalleryappActivity.this.rlMenuList.setVisibility(View.GONE);
                    return;
                }
                GalleryappActivity.this.cardMenuList.setVisibility(View.VISIBLE);
                GalleryappActivity.this.rlMenuList.setVisibility(View.VISIBLE);
            }
        });
        this.ll_share.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryappActivity galleryappActivity = GalleryappActivity.this;
                Uri uriForFile = FileProvider.getUriForFile(galleryappActivity, GalleryappActivity.this.getPackageName() + ".provider", new File(GalleryappActivity.this.mediaItems.get(GalleryappActivity.this.gallery_pager.getCurrentItem()).getPath()));
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                intent.setType("image/png");
                GalleryappActivity.this.startActivityForResult(Intent.createChooser(intent, "Share File"), 33);
            }
        });
        this.tvDetail.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryappActivity.this.cardMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.rlMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.showDetailsDialog();
            }
        });
        this.tvRename.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalleryappActivity.lambda$onCreate$1(GalleryappActivity.this, view);
            }
        });
        this.tvSetas.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalleryappActivity.lambda$onCreate$2(GalleryappActivity.this, view);
            }
        });
        this.tvRotate.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalleryappActivity.this.rotateMedia(90);
            }
        });
        this.ll_delete.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryappActivity.this.cardMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.rlMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.showDeleteConfirmationDialog();
            }
        });
        this.tvSlideshow.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GalleryappActivity.lambda$onCreate$4(GalleryappActivity.this, view);
            }
        });
        this.ll_edit.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryappActivity.this.cardMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.rlMenuList.setVisibility(View.GONE);
                Intent intent = new Intent(GalleryappActivity.this, EditImageActivity.class);
                intent.putExtra("FilePath", GalleryappActivity.this.mediaItems.get(GalleryappActivity.this.gallery_pager.getCurrentItem()).getPath());
                GalleryappActivity.this.startActivity(intent);
            }
        });
        this.tvShowOnMap.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryappActivity.this.cardMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.rlMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Double.valueOf(GalleryappActivity.this.mediaItems.get(GalleryappActivity.this.gallery_pager.getCurrentItem()).getLatitude()), Double.valueOf(GalleryappActivity.this.mediaItems.get(GalleryappActivity.this.gallery_pager.getCurrentItem()).getLongitude())))));
                Log.d("lat_long", GalleryappActivity.this.mediaItems.get(GalleryappActivity.this.gallery_pager.getCurrentItem()).getLatitude() + "  " + GalleryappActivity.this.mediaItems.get(GalleryappActivity.this.gallery_pager.getCurrentItem()).getLongitude());
            }
        });
        this.rlHideShow.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryappActivity.this.rlSlideShow.setVisibility(View.GONE);
                GalleryappActivity.this.gallery_pager.setCurrentItem(GalleryappActivity.this.vpSlideShow.getCurrentItem());
            }
        });
        this.ll_detail.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GalleryappActivity.this.cardMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.rlMenuList.setVisibility(View.GONE);
                GalleryappActivity.this.showDetailsDialog();
            }
        });
    }

    public static void lambda$onCreate$0(GalleryappActivity galleryappActivity, View view) {
        galleryappActivity.cardMenuList.setVisibility(View.GONE);
        galleryappActivity.rlMenuList.setVisibility(View.GONE);
    }

    public static void lambda$onCreate$1(GalleryappActivity galleryappActivity, View view) {
        galleryappActivity.cardMenuList.setVisibility(View.GONE);
        galleryappActivity.rlMenuList.setVisibility(View.GONE);
        galleryappActivity.showRenameDialog();
    }

    public static void lambda$onCreate$2(GalleryappActivity galleryappActivity, View view) {
        galleryappActivity.cardMenuList.setVisibility(View.GONE);
        galleryappActivity.rlMenuList.setVisibility(View.GONE);
        galleryappActivity.useMediaAs();
    }

    public static void lambda$onCreate$4(GalleryappActivity galleryappActivity, View view) {
        galleryappActivity.cardMenuList.setVisibility(View.GONE);
        galleryappActivity.rlMenuList.setVisibility(View.GONE);
        galleryappActivity.startSlideShow();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (MyApp.getInstance().isFromEdit) {
            new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.18
                @Override // java.lang.Runnable
                public void run() {
                    MyApp.getInstance().isFromEdit = false;
                    GalleryappActivity.this.loadAlbum();
                }
            }, 500L);
        }
    }

    public String toString() {
        return String.valueOf(this.Latitude) + ", " + String.valueOf(this.Longitude);
    }

    public void loadAlbum() {
        this.rlProgress.setVisibility(View.VISIBLE);
        this.mediaItems.clear();
        this.pAdapter.notifyDataSetChanged();
        if (getIntent().hasExtra("path")) {
            this.foldername = getIntent().getStringExtra("path");
            this.file = new File(getIntent().getStringExtra("dir"));
            this.filename = getIntent().getStringExtra("name");
        } else {
            this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + this.foldername);
        }
        this.album = new Album(this.file.getAbsolutePath(), this.foldername);
        CPHelper.getMedia(this, this.album).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.functions.Consumer<Media>() {
            @SuppressLint("CheckResult")
            @Override
            public void accept(Media media) throws Exception {
                try {
                    mediaItems.add(media);
                    Collections.sort(mediaItems, new Comparator<Media>() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.20
                        @Override // java.util.Comparator
                        public int compare(Media media2, Media media3) {
                            return media3.getDateModified().compareTo(media2.getDateModified());
                        }
                    });
                    if (mediaItems.size() == 0) {
                        tvError.setVisibility(View.VISIBLE);
                        rlProgress.setVisibility(View.GONE);
                        return;
                    }
                    pAdapter.notifyDataSetChanged();
                    tvError.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.21
                        @Override // java.lang.Runnable
                        public void run() {
                            int i;
                            MyApp.getInstance().setSize(GalleryappActivity.this.mediaItems.size());
                            Date date = new Date(System.currentTimeMillis() - 86400000);
                            Date date2 = new Date(System.currentTimeMillis());
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM");
                            String format = simpleDateFormat.format(date);
                            if (simpleDateFormat.format(date2).equalsIgnoreCase(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString())) {
                                GalleryappActivity.this.tvTitleName.setText("Today");
                                GalleryappActivity.this.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString());
                            } else if (format.equalsIgnoreCase(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString())) {
                                GalleryappActivity.this.tvTitleName.setText("Yesterday");
                                GalleryappActivity.this.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString());
                            } else {
                                GalleryappActivity.this.tvTitleName.setText(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString());
                                GalleryappActivity.this.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString());
                            }
                            if (GalleryappActivity.this.isImageEdited) {
                                GalleryappActivity.this.gallery_pager.setCurrentItem(GalleryappActivity.this.gallery_pager.getCurrentItem());
                                i = GalleryappActivity.this.gallery_pager.getCurrentItem();
                            } else {
                                i = 0;
                            }
                            if (GalleryappActivity.this.mediaItems.get(0).isVideo() || GalleryappActivity.this.mediaItems.get(0).isGif()) {
                                GalleryappActivity.this.ll_edit.setVisibility(View.GONE);
                                GalleryappActivity.this.tvRotate.setVisibility(View.GONE);
                                GalleryappActivity.this.ll_detail.setVisibility(View.VISIBLE);
                            } else {
                                GalleryappActivity.this.ll_edit.setVisibility(View.VISIBLE);
                                GalleryappActivity.this.tvRotate.setVisibility(View.VISIBLE);
                                GalleryappActivity.this.ll_detail.setVisibility(View.VISIBLE);
                            }
                            GalleryappActivity.this.rlProgress.setVisibility(View.GONE);
                            if (GalleryappActivity.this.mediaItems.get(0).getLatitude() == 0.0d || GalleryappActivity.this.mediaItems.get(i).getLongitude() == 0.0d) {
                                GalleryappActivity.this.tvShowOnMap.setVisibility(View.GONE);
                            } else {
                                GalleryappActivity.this.tvShowOnMap.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 500L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void lambda$loadAlbum$5(GalleryappActivity galleryappActivity, Media media) throws Exception {

    }

    private void useMediaAs() {
        Intent intent = new Intent("android.intent.action.ATTACH_DATA");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setDataAndType(LegacyCompatFileProvider.getUri(this, this.mediaItems.get(this.gallery_pager.getCurrentItem()).getFile()), this.mediaItems.get(this.gallery_pager.getCurrentItem()).getMimeType());
        intent.putExtra("mimeType", this.mediaItems.get(this.gallery_pager.getCurrentItem()).getMimeType());
        startActivity(Intent.createChooser(intent, "Use as"));
    }

    public void showDetailsDialog() {
        MetaDataItem metadata = MetaDataItem.getMetadata(this, this.mediaItems.get(this.gallery_pager.getCurrentItem()).getUri());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.image_dialog_details, (ViewGroup) null);
        builder.setView(inflate);
        TextView textView = (TextView) inflate.findViewById(R.id.tvPathValue);
        TextView textView2 = (TextView) inflate.findViewById(R.id.tvTypeValue);
        TextView textView3 = (TextView) inflate.findViewById(R.id.tvSizeValue);
        TextView textView4 = (TextView) inflate.findViewById(R.id.tvOrientationValue);
        TextView textView5 = (TextView) inflate.findViewById(R.id.tvResolutionValue);
        TextView textView6 = (TextView) inflate.findViewById(R.id.tvDateValue);
        TextView textView7 = (TextView) inflate.findViewById(R.id.tvTimeValue);
        //   loadintertialads.getInstance().refreshAd(this, (FrameLayout) inflate.findViewById(R.id.frameLayout));
        AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Media media = this.mediaItems.get(this.gallery_pager.getCurrentItem());
        if (!TextUtils.isEmpty(media.getDisplayPath())) {
            textView.setText(media.getDisplayPath());
        } else {
            textView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(metadata.getResolution())) {
            textView5.setText(metadata.getResolution());
        }
        if (!TextUtils.isEmpty(media.getMimeType())) {
            textView2.setText(media.getMimeType());
        } else {
            textView2.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(String.valueOf(media.getSize()))) {
            textView3.setText(formatFileSize(media.getSize()));
        } else {
            textView3.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(String.valueOf(media.getOrientation()))) {
            textView4.setText(String.valueOf(media.getOrientation()));
        } else {
            textView4.setVisibility(View.GONE);
        }
        textView6.setText(DateFormat.format("MMM dd, yyyy", new Date(media.getDateModified().longValue())).toString());
        textView7.setText(DateFormat.format("hh:mm:ss a", new Date(media.getDateModified().longValue())).toString());
        create.show();
    }

    private void showRenameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.image_rename_dialog, (ViewGroup) null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        final EditText editText = (EditText) inflate.findViewById(R.id.etRename);
        editText.setText(StringUtils.getPhotoNameByPath(this.mediaItems.get(this.gallery_pager.getCurrentItem()).getPath()));
        ((Button) inflate.findViewById(R.id.btnCancel)).setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                create.dismiss();
            }
        });
        ((Button) inflate.findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.23
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    Toast.makeText(GalleryappActivity.this, "Enter file name!", Toast.LENGTH_SHORT).show();
                    editText.requestFocus();
                    return;
                }
                create.dismiss();
                GalleryappActivity galleryappActivity = GalleryappActivity.this;
                boolean renameMedia = MediaHelper.renameMedia(galleryappActivity, galleryappActivity.mediaItems.get(GalleryappActivity.this.gallery_pager.getCurrentItem()), editText.getText().toString());
                GalleryappActivity.this.setResult(-1);
                if (renameMedia) {
                    return;
                }
                StringUtils.showToast(GalleryappActivity.this, "Unable to rename this file");
            }
        });
        create.show();
    }

    public void rotateMedia(int i) {
        this.cardMenuList.setVisibility(View.GONE);
        this.rlMenuList.setVisibility(View.GONE);
        Fragment registeredFragment = this.pAdapter.getRegisteredFragment(this.gallery_pager.getCurrentItem());
        if (registeredFragment instanceof SingleMediaFragment) {
            ((SingleMediaFragment) registeredFragment).rotateImage(i);
        }
    }

    public void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.image_confirmation_dialog, (ViewGroup) null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        ((TextView) inflate.findViewById(R.id.tvTitle)).setText("Are you sure you want to delete this media?");
        ((Button) inflate.findViewById(R.id.btnNo)).setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                create.dismiss();
            }
        });
        ((Button) inflate.findViewById(R.id.btnYes)).setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                create.dismiss();
                // GalleryappActivity.this.setResult(-1);
                GalleryappActivity.this.deleteCurrentMedia();
            }
        });
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
    }

    public void deleteCurrentMedia() {
        File file1 = this.mediaItems.get(this.gallery_pager.getCurrentItem()).getFile();
        if (file1.exists()) {
            file1.delete();
            mediaItems.remove(this.gallery_pager.getCurrentItem());
            pAdapter.addData(mediaItems);
            if(mediaItems.size() == 0){
                displayAlbums();
            }
        }

    /*
        Uri uri = U_DeleteFile.getUriFromPathImages(GalleryappActivity.this, this.mediaItems.get(this.gallery_pager.getCurrentItem()).getFile());
        U_DeleteFile.deleteMedia(GalleryappActivity.this, uri, 101, () -> {
            viewAdapter.notifyDataSetChanged();
            Toast.makeText(GalleryappActivity.this,"delete Completed", Toast.LENGTH_SHORT).show();
        });*/

//        disposeLater(MediaHelper.deleteMedia(GalleryappActivity.this, this.mediaItems.get(this.gallery_pager.getCurrentItem())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(media -> {
//            GalleryappActivity.this.mediaItems.remove((Media) media);
//            GalleryappActivity.this.setResult(-1);
//            if (GalleryappActivity.this.mediaItems.size() == 0) {
//                GalleryappActivity.this.displayAlbums();
//            }
//        }));
    }

    public void displayAlbums() {
        setResult(-1);
        finish();
    }

    public void disposeLater(Disposable disposable) {
        this.disposables.add(disposable);
    }

    private void startSlideShow() {
        this.rlSlideShow.setVisibility(View.VISIBLE);
        this.viewAdapter = new ViewPagerAdapter(this, this.mediaItems);
        this.vpSlideShow.setAdapter(this.viewAdapter);
        this.vpSlideShow.setPageTransformer(true, new ViewPager.PageTransformer() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.29
            @Override // androidx.viewpager.widget.ViewPager.PageTransformer
            public void transformPage(View view, float f) {
                view.setTranslationX(view.getWidth() * (-f));
                if (f <= -1.0f || f >= 1.0f) {
                    view.setAlpha(0.0f);
                } else if (f == 0.0f) {
                    view.setAlpha(1.0f);
                } else {
                    view.setAlpha(1.0f - Math.abs(f));
                }
            }
        });

        vpSlideShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                itemPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        animate();
    }

    public void transformPage(View view, float f) {
        view.setTranslationX(view.getWidth() * (-f));
        if (f <= -1.0f || f >= 1.0f) {
            view.setAlpha(0.0f);
        } else if (f == 0.0f) {
            view.setAlpha(1.0f);
        } else {
            view.setAlpha(1.0f - Math.abs(f));
        }
    }

    public void animate() {
        if (this.vpSlideShow.getCurrentItem() != this.mediaItems.size() - 1) {
            new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.GalleryappActivity.30
                @Override // java.lang.Runnable
                public void run() {
                    GalleryappActivity.this.vpSlideShow.setCurrentItem(GalleryappActivity.this.vpSlideShow.getCurrentItem() + 1);
                    GalleryappActivity.this.animate();
                }
            }, 3500L);
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.rlMenuList.getVisibility() == View.VISIBLE) {
            this.cardMenuList.setVisibility(View.GONE);
            this.rlMenuList.setVisibility(View.GONE);
        } else if (this.rlSlideShow.getVisibility() == View.VISIBLE) {
            this.rlSlideShow.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
