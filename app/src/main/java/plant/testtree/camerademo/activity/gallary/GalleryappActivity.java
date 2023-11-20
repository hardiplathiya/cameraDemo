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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import plant.testtree.camerademo.databinding.ActivityGalleryBinding;
import plant.testtree.camerademo.fragment.GifFragment;
import plant.testtree.camerademo.fragment.SingleMediaFragment;
import plant.testtree.camerademo.fragment.VideoFragment;
import plant.testtree.camerademo.helper.LegacyCompatFileProvider;
import plant.testtree.camerademo.helper.MediaHelper;
import plant.testtree.camerademo.helper.MyApp;
import plant.testtree.camerademo.model.Media;
import plant.testtree.camerademo.util.CPHelper;
import plant.testtree.camerademo.util.MetaDataItem;
import plant.testtree.camerademo.util.Prefs;
import plant.testtree.camerademo.util.StringUtils;

public class GalleryappActivity extends AppCompatActivity {
    Float Latitude;
    Float Longitude;
    private Album album;
    File file;
    CompositeDisposable disposables = new CompositeDisposable();
    String filename = "";
    String foldername = "iCamera";
    public boolean isImageEdited = false;
    public List<Media> mediaItems = new ArrayList();

    int itemPosition = -1;
    public SparseArray<Fragment> registeredFragments = new SparseArray<>();
    private MediaPagerAdapter pAdapter;
    private ViewPagerAdapter viewAdapter;

    public class MediaPagerAdapter extends FragmentStatePagerAdapter {
        @Override
        public int getItemPosition(Object obj) {
            return -2;
        }

        public MediaPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.instantiateItem(viewGroup, i);
            GalleryappActivity.this.registeredFragments.put(i, fragment);
            return fragment;
        }

        @Override
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
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            GalleryappActivity.this.registeredFragments.remove(i);
            super.destroyItem(viewGroup, i, obj);
        }

        public Fragment getRegisteredFragment(int i) {
            return GalleryappActivity.this.registeredFragments.get(i);
        }

        @Override
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


   public static ActivityGalleryBinding binding;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MyApp.getInstance().isFromPuzzle = false;
        try {
            Prefs.init(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getIntent().hasExtra("path")) {
            foldername = getIntent().getStringExtra("path");
            String file1 = getIntent().getStringExtra("dir");
            if (file1 != null)
                file = new File(file1);
            filename = getIntent().getStringExtra("name");
        } else {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + foldername);
        }
        album = new Album(file.getAbsolutePath(), foldername);
        pAdapter = new MediaPagerAdapter(getSupportFragmentManager());
        binding.galleryPager.setAdapter(pAdapter);
        // java.lang.Runnable
        new Handler().postDelayed(this::loadAlbum, 500L);
        new Handler().postDelayed(new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i < GalleryappActivity.this.mediaItems.size(); i++) {
                    if (GalleryappActivity.this.filename.contains(GalleryappActivity.this.mediaItems.get(i).getName())) {
                        binding.galleryPager.setCurrentItem(i);
                        return;
                    }
                }
            }
        }, 1000L);
        binding.galleryPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrolled(int i, float f, int i2) {
                Date date = new Date(System.currentTimeMillis() - 86400000);
                Date date2 = new Date(System.currentTimeMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM");
                String format = simpleDateFormat.format(date);
                if (simpleDateFormat.format(date2).equalsIgnoreCase(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString())) {
                    binding.tvTitleName.setText("Today");
                    binding.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString());
                } else if (format.equalsIgnoreCase(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString())) {
                    binding.tvTitleName.setText("Yesterday");
                    binding.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString());
                } else {
                    binding.tvTitleName.setText(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString());
                    binding.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(i).getDateModified().longValue())).toString());
                }
                if (GalleryappActivity.this.mediaItems.get(i).isVideo() || GalleryappActivity.this.mediaItems.get(i).isGif()) {
                    binding.llEdit.setVisibility(View.GONE);
                    binding.tvRotate.setVisibility(View.GONE);
                    binding.llDetail.setVisibility(View.VISIBLE);
                    binding.tvSetas.setVisibility(View.GONE);
                } else {
                    binding.llEdit.setVisibility(View.VISIBLE);
                    binding.tvRotate.setVisibility(View.VISIBLE);
                    binding.llDetail.setVisibility(View.VISIBLE);
                    binding.tvSetas.setVisibility(View.VISIBLE);
                }
                if (GalleryappActivity.this.mediaItems.get(i).getLatitude() == 0.0d || GalleryappActivity.this.mediaItems.get(i).getLongitude() == 0.0d) {
                    binding.tvShowOnMap.setVisibility(View.GONE);
                } else {
                    binding.tvShowOnMap.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.ivBack.setOnClickListener(view ->onBackPressed());

        binding.rlMenuList.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
        });

        binding.ivMenu.setOnClickListener(view -> {
            if (binding.cardMenuList.getVisibility() == View.VISIBLE) {
                binding.cardMenuList.setVisibility(View.GONE);
                binding.rlMenuList.setVisibility(View.GONE);
                return;
            }
            binding.cardMenuList.setVisibility(View.VISIBLE);
            binding.rlMenuList.setVisibility(View.VISIBLE);
        });

        binding.llShare.setOnClickListener(view -> {
            GalleryappActivity galleryappActivity = GalleryappActivity.this;
            Uri uriForFile = FileProvider.getUriForFile(galleryappActivity, GalleryappActivity.this.getPackageName() + ".provider", new File(GalleryappActivity.this.mediaItems.get(binding.galleryPager.getCurrentItem()).getPath()));
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", uriForFile);
            intent.setType("image/png");
            GalleryappActivity.this.startActivityForResult(Intent.createChooser(intent, "Share File"), 33);
        });

        binding.tvDetail.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
            GalleryappActivity.this.showDetailsDialog();
        });

        binding.tvRename.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
            showRenameDialog();
        });

        binding.tvSetas.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
            useMediaAs();
        });

        binding.tvRotate.setOnClickListener(view -> GalleryappActivity.this.rotateMedia(90));

        binding.llDelete.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
            GalleryappActivity.this.showDeleteConfirmationDialog();
        });
        binding.tvSlideshow.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
            startSlideShow();
        });

        binding.llEdit.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
            Intent intent = new Intent(GalleryappActivity.this, EditImageActivity.class);
            intent.putExtra("FilePath", GalleryappActivity.this.mediaItems.get(binding.galleryPager.getCurrentItem()).getPath());
            GalleryappActivity.this.startActivity(intent);
        });
        binding.tvShowOnMap.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
            GalleryappActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", Double.valueOf(GalleryappActivity.this.mediaItems.get(binding.galleryPager.getCurrentItem()).getLatitude()), Double.valueOf(GalleryappActivity.this.mediaItems.get(binding.galleryPager.getCurrentItem()).getLongitude())))));
            Log.d("lat_long", GalleryappActivity.this.mediaItems.get(binding.galleryPager.getCurrentItem()).getLatitude() + "  " + GalleryappActivity.this.mediaItems.get(binding.galleryPager.getCurrentItem()).getLongitude());
        });
        binding.llDetail.setOnClickListener(view -> {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
            GalleryappActivity.this.showDetailsDialog();
        });
        binding.rlHideShow.setOnClickListener(view -> {
            binding.rlSlideShow.setVisibility(View.GONE);
            binding.galleryPager.setCurrentItem(binding.vpSlideShow.getCurrentItem());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApp.getInstance().isFromEdit) {
            new Handler().postDelayed(() -> {
                MyApp.getInstance().isFromEdit = false;
                GalleryappActivity.this.loadAlbum();
            }, 500L);
        }
    }

    public String toString() {
        return String.valueOf(this.Latitude) + ", " + String.valueOf(this.Longitude);
    }

    @SuppressLint("CheckResult")
    public void loadAlbum() {
        binding.rlProgress.setVisibility(View.VISIBLE);
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
        CPHelper.getMedia(this, this.album).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(media -> {
            try {
                mediaItems.add(media);
                Collections.sort(mediaItems, (media2, media3) -> media3.getDateModified().compareTo(media2.getDateModified()));
                if (mediaItems.size() == 0) {
                    binding.tvError.setVisibility(View.VISIBLE);
                    binding.rlProgress.setVisibility(View.GONE);
                    return;
                }
                pAdapter.notifyDataSetChanged();
                binding.tvError.setVisibility(View.GONE);
                new Handler().postDelayed(() -> {
                    int i;
                    MyApp.getInstance().setSize(GalleryappActivity.this.mediaItems.size());
                    Date date = new Date(System.currentTimeMillis() - 86400000);
                    Date date2 = new Date(System.currentTimeMillis());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM");
                    String format = simpleDateFormat.format(date);
                    if (simpleDateFormat.format(date2).equalsIgnoreCase(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString())) {
                        binding.tvTitleName.setText("Today");
                        binding.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString());
                    } else if (format.equalsIgnoreCase(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString())) {
                        binding.tvTitleName.setText("Yesterday");
                        binding.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString());
                    } else {
                        binding.tvTitleName.setText(DateFormat.format("dd MMMM", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString());
                        binding.tvTitleTime.setText(DateFormat.format("hh:mm a", new Date(GalleryappActivity.this.mediaItems.get(0).getDateModified().longValue())).toString());
                    }
                    if (GalleryappActivity.this.isImageEdited) {
                        binding.galleryPager.setCurrentItem(binding.galleryPager.getCurrentItem());
                        i = binding.galleryPager.getCurrentItem();
                    } else {
                        i = 0;
                    }
                    if (GalleryappActivity.this.mediaItems.get(0).isVideo() || GalleryappActivity.this.mediaItems.get(0).isGif()) {
                        binding.llEdit.setVisibility(View.GONE);
                        binding.tvRotate.setVisibility(View.GONE);
                        binding.llDetail.setVisibility(View.VISIBLE);
                    } else {
                        binding.llEdit.setVisibility(View.VISIBLE);
                        binding.tvRotate.setVisibility(View.VISIBLE);
                        binding.llDetail.setVisibility(View.VISIBLE);
                    }
                    binding.rlProgress.setVisibility(View.GONE);
                    if (GalleryappActivity.this.mediaItems.get(0).getLatitude() == 0.0d || GalleryappActivity.this.mediaItems.get(i).getLongitude() == 0.0d) {
                        binding.tvShowOnMap.setVisibility(View.GONE);
                    } else {
                        binding.tvShowOnMap.setVisibility(View.VISIBLE);
                    }
                }, 500L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void useMediaAs() {
        Intent intent = new Intent("android.intent.action.ATTACH_DATA");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setDataAndType(LegacyCompatFileProvider.getUri(this, this.mediaItems.get(binding.galleryPager.getCurrentItem()).getFile()), this.mediaItems.get(binding.galleryPager.getCurrentItem()).getMimeType());
        intent.putExtra("mimeType", this.mediaItems.get(binding.galleryPager.getCurrentItem()).getMimeType());
        startActivity(Intent.createChooser(intent, "Use as"));
    }

    public void showDetailsDialog() {
        MetaDataItem metadata = MetaDataItem.getMetadata(this, this.mediaItems.get(binding.galleryPager.getCurrentItem()).getUri());
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
        AlertDialog create = builder.create();
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Media media = this.mediaItems.get(binding.galleryPager.getCurrentItem());
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
        editText.setText(StringUtils.getPhotoNameByPath(this.mediaItems.get(binding.galleryPager.getCurrentItem()).getPath()));
        ((Button) inflate.findViewById(R.id.btnCancel)).setOnClickListener(view -> create.dismiss());

        ((Button) inflate.findViewById(R.id.btnOK)).setOnClickListener(view -> {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                Toast.makeText(GalleryappActivity.this, "Enter file name!", Toast.LENGTH_SHORT).show();
                editText.requestFocus();
                return;
            }
            create.dismiss();
            GalleryappActivity galleryappActivity = GalleryappActivity.this;
            boolean renameMedia = MediaHelper.renameMedia(galleryappActivity, galleryappActivity.mediaItems.get(binding.galleryPager.getCurrentItem()), editText.getText().toString());
            GalleryappActivity.this.setResult(-1);
            if (renameMedia) {
                return;
            }
            StringUtils.showToast(GalleryappActivity.this, "Unable to rename this file");
        });
        create.show();
    }

    public void rotateMedia(int i) {
        binding.cardMenuList.setVisibility(View.GONE);
        binding.rlMenuList.setVisibility(View.GONE);
        Fragment registeredFragment = this.pAdapter.getRegisteredFragment(binding.galleryPager.getCurrentItem());
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
        ((Button) inflate.findViewById(R.id.btnNo)).setOnClickListener(view -> create.dismiss());

        ((Button) inflate.findViewById(R.id.btnYes)).setOnClickListener(view -> {
            create.dismiss();
            GalleryappActivity.this.deleteCurrentMedia();
        });
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
    }

    public void deleteCurrentMedia() {
        File file1 = this.mediaItems.get(binding.galleryPager.getCurrentItem()).getFile();
        if (file1.exists()) {
            file1.delete();
            mediaItems.remove(binding.galleryPager.getCurrentItem());
            pAdapter.addData(mediaItems);
            if (mediaItems.size() == 0) {
                displayAlbums();
            }
        }
    }

    public void displayAlbums() {
        setResult(-1);
        finish();
    }

    public void disposeLater(Disposable disposable) {
        this.disposables.add(disposable);
    }

    private void startSlideShow() {
        binding.rlSlideShow.setVisibility(View.VISIBLE);
        viewAdapter = new ViewPagerAdapter(this, this.mediaItems);
        binding.vpSlideShow.setAdapter(this.viewAdapter);
        // androidx.viewpager.widget.ViewPager.PageTransformer
        binding.vpSlideShow.setPageTransformer(true, (view, f) -> {
            view.setTranslationX(view.getWidth() * (-f));
            if (f <= -1.0f || f >= 1.0f) {
                view.setAlpha(0.0f);
            } else if (f == 0.0f) {
                view.setAlpha(1.0f);
            } else {
                view.setAlpha(1.0f - Math.abs(f));
            }
        });

        binding.vpSlideShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    public void animate() {
        if (binding.vpSlideShow.getCurrentItem() != this.mediaItems.size() - 1) {
            new Handler().postDelayed(() -> {
                GalleryappActivity.binding.vpSlideShow.setCurrentItem(GalleryappActivity.binding.vpSlideShow.getCurrentItem() + 1);
                GalleryappActivity.this.animate();
            }, 3500L);
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.rlMenuList.getVisibility() == View.VISIBLE) {
            binding.cardMenuList.setVisibility(View.GONE);
            binding.rlMenuList.setVisibility(View.GONE);
        } else if (binding.rlSlideShow.getVisibility() == View.VISIBLE) {
            binding.rlSlideShow.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
