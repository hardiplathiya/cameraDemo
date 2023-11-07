package plant.testtree.camerademo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drew.metadata.exif.makernotes.FujifilmMakernoteDirectory;
import com.yalantis.ucrop.UCrop;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

import it.chengdazhi.styleimageview.StyleImageView;
import plant.testtree.camerademo.R;
import plant.testtree.camerademo.adapter.FilterAdapterGalalry;
import plant.testtree.camerademo.adapter.FilterType;
import plant.testtree.camerademo.helper.MyApp;
import plant.testtree.camerademo.util.BitmapUtils;
import plant.testtree.camerademo.util.Const;
import plant.testtree.camerademo.util.FileUtils;
import plant.testtree.camerademo.util.GLRootView;
import plant.testtree.camerademo.util.GLWrapper;

/* loaded from: classes.dex */
public class EditImageActivity extends AppCompatActivity {
    public static int filterPosition;
    RecyclerView filter_list;
    public GLRootView glRootView;
    public GLWrapper glWrapper;
    String image;
    Bitmap imageBitmap;
    Uri imageUri;
    boolean isColorEffect = false;
    boolean isCrop = false;
    boolean isFilter = false;
    ImageView ivBack;
    LinearLayout ivBrightness;
    ImageView ivColorClose;
    ImageView ivColorDone;
    ImageView ivColorEdit;
    LinearLayout ivContrast;
    ImageView ivCrop;
    StyleImageView ivEditImage;
    ImageView ivFilter;
    ImageView ivFilterClose;
    ImageView ivFilterDone;
    ImageView ivImage;
    ImageView ivMenu;
    LinearLayout ivsaturation;
    LinearLayout llAnimationFilter;
    LinearLayout llColorEdit;
    LinearLayout llCrop;
    LinearLayout llFilter;
    RelativeLayout rlColorFilter;
    RelativeLayout rlCropLayout;
    RelativeLayout rlFilterView;
    RelativeLayout rvProgress;
    DiscreteSeekBar seekbarContrast;
    DiscreteSeekBar seekbarSaturation;
    DiscreteSeekBar seekbar_brightness;
    TextView tvCancel;
    TextView tvDone;
    TextView tvFilterName;

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_edit_image);

        FileUtils.upZipFile(this, "filter/thumbs/thumbs.zip", getFilesDir().getAbsolutePath());
        this.ivImage = (ImageView) findViewById(R.id.ivImage);
        this.ivBack = (ImageView) findViewById(R.id.ivBack);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.onBackPressed();
            }
        });
        try {
            this.image = getIntent().getStringExtra("FilePath");
            this.imageUri = Uri.fromFile(new File(this.image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.ivImage.setImageURI(this.imageUri);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.rlColorFilter = (RelativeLayout) findViewById(R.id.rlColorFilter);
        this.ivBrightness = (LinearLayout) findViewById(R.id.ivBrightness);
        this.ivContrast = (LinearLayout) findViewById(R.id.ivContrast);
        this.ivsaturation = (LinearLayout) findViewById(R.id.ivsaturation);
        this.ivFilterClose = (ImageView) findViewById(R.id.ivFilterClose);
        this.ivColorClose = (ImageView) findViewById(R.id.ivColorClose);
        this.ivFilterDone = (ImageView) findViewById(R.id.ivFilterDone);
        this.ivColorDone = (ImageView) findViewById(R.id.ivColorDone);
        this.llCrop = (LinearLayout) findViewById(R.id.llCrop);
        this.llFilter = (LinearLayout) findViewById(R.id.llFilter);
        this.llColorEdit = (LinearLayout) findViewById(R.id.llColorEdit);
        this.ivCrop = (ImageView) findViewById(R.id.ivCrop);
        this.ivFilter = (ImageView) findViewById(R.id.ivFilter);
        this.ivColorEdit = (ImageView) findViewById(R.id.ivColorEdit);
        this.tvCancel = (TextView) findViewById(R.id.tvCancel);
        this.tvDone = (TextView) findViewById(R.id.tvDone);
        this.ivMenu = (ImageView) findViewById(R.id.ivMenu);
        this.tvFilterName = (TextView) findViewById(R.id.tvFilterName);
        this.llAnimationFilter = (LinearLayout) findViewById(R.id.llAnimationFilter);
        this.ivEditImage = (StyleImageView) findViewById(R.id.ivEditImage);
        this.rlCropLayout = (RelativeLayout) findViewById(R.id.rlCropLayout);
        this.rlFilterView = (RelativeLayout) findViewById(R.id.rlFilterView);
        this.filter_list = (RecyclerView) findViewById(R.id.filter_list);
        this.rvProgress = (RelativeLayout) findViewById(R.id.rvProgress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        this.filter_list.setLayoutManager(linearLayoutManager);
        this.tvDone.setEnabled(false);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < FilterType.values().length; i++) {
            arrayList.add(FilterType.values()[i]);
            if (i == 0) {
                arrayList.add(FilterType.NONE);
            }
        }
        FilterAdapterGalalry filterAdapter = new FilterAdapterGalalry(this, arrayList, this.image);
        this.filter_list.setAdapter(filterAdapter);
        filterAdapter.setOnFilterChangeListener(new FilterAdapterGalalry.OnFilterChangeListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.2
            @Override // com.cameraediter.iphone11pro.filter.FilterAdapterGalalry.OnFilterChangeListener
            public void onFilterChanged(FilterType filterType) {
                EditImageActivity.this.glWrapper.switchLastFilterOfCustomizedFilters(filterType);
            }
        });
        this.glRootView = (GLRootView) findViewById(R.id.camera_view);
        this.glWrapper = GLWrapper.newInstance().setGlImageView(this.glRootView).setContext(this).init();
        Bitmap loadBitmapFromFile = BitmapUtils.loadBitmapFromFile(this.image);
        this.glRootView.setAspectRatio(loadBitmapFromFile.getWidth(), loadBitmapFromFile.getHeight());
        loadBitmapFromFile.recycle();
        this.glWrapper.setFilePath(this.image);
        this.llCrop.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (EditImageActivity.this.rlColorFilter.getVisibility() == View.VISIBLE) {
                    EditImageActivity.this.llColorEdit.performClick();
                    EditImageActivity.this.rlColorFilter.setVisibility(View.GONE);
                    EditImageActivity editImageActivity = EditImageActivity.this;
                    editImageActivity.isCrop = true;
                    editImageActivity.rlCropLayout.setVisibility(View.VISIBLE);
                    EditImageActivity.this.ivCrop.setImageResource(R.drawable.crop_press);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color1);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect);
                    new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.EditImageActivity.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                UCrop.of(EditImageActivity.this.imageUri, Uri.fromFile(new File(EditImageActivity.this.getCacheDir(), "destination.jpg"))).start(EditImageActivity.this);
                                Log.d("imagepath", EditImageActivity.this.imageUri.getPath());
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                    }, 500L);
                } else if (EditImageActivity.this.rlFilterView.getVisibility() == View.VISIBLE) {
                    EditImageActivity.this.llFilter.performClick();
                    EditImageActivity.this.rlFilterView.setVisibility(View.GONE);
                    EditImageActivity editImageActivity2 = EditImageActivity.this;
                    editImageActivity2.isCrop = true;
                    editImageActivity2.rlCropLayout.setVisibility(View.VISIBLE);
                    EditImageActivity.this.ivCrop.setImageResource(R.drawable.crop_press);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color1);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect);
                    new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.EditImageActivity.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                UCrop.of(EditImageActivity.this.imageUri, Uri.fromFile(new File(EditImageActivity.this.getCacheDir(), "destination.jpg"))).start(EditImageActivity.this);
                                Log.d("imagepath", EditImageActivity.this.imageUri.getPath());
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                    }, 500L);
                } else if (EditImageActivity.this.isCrop) {
                    EditImageActivity editImageActivity3 = EditImageActivity.this;
                    editImageActivity3.isCrop = false;
                    editImageActivity3.rlCropLayout.setVisibility(View.GONE);
                    EditImageActivity.this.ivCrop.setImageResource(R.drawable.crop);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color1);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect);
                } else {
                    EditImageActivity editImageActivity4 = EditImageActivity.this;
                    editImageActivity4.isCrop = true;
                    editImageActivity4.rlCropLayout.setVisibility(View.VISIBLE);
                    EditImageActivity.this.ivCrop.setImageResource(R.drawable.crop_press);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color1);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect);
                    try {
                        UCrop.of(EditImageActivity.this.imageUri, Uri.fromFile(new File(EditImageActivity.this.getCacheDir(), "destination.jpg"))).start(EditImageActivity.this);
                        Log.d("imagepath", EditImageActivity.this.imageUri.getPath());
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
        });
        this.llFilter.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.tvDone.setEnabled(true);
                EditImageActivity.this.tvDone.setTextColor(ContextCompat.getColor(EditImageActivity.this, R.color.yellow));
                if (EditImageActivity.this.rlColorFilter.getVisibility() == View.VISIBLE) {
                    EditImageActivity.this.llColorEdit.performClick();
                    EditImageActivity.this.rlColorFilter.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.EditImageActivity.4.1
                        @Override // java.lang.Runnable
                        public void run() {
                            Bitmap bitmap;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(EditImageActivity.this.getContentResolver(), EditImageActivity.this.imageUri);
                            } catch (IOException e3) {
                                e3.printStackTrace();
                                bitmap = null;
                            }
                            EditImageActivity.this.glRootView.setAspectRatio(bitmap.getWidth(), bitmap.getHeight());
                            bitmap.recycle();
                            Log.d("imagepath", EditImageActivity.this.imageUri.getPath() + "");
                            EditImageActivity.this.glWrapper.setFilePath(EditImageActivity.this.imageUri.getPath());
                            EditImageActivity.this.isFilter = true;
                            EditImageActivity.this.rlFilterView.setVisibility(View.VISIBLE);
                            EditImageActivity.this.ivCrop.setImageResource(R.drawable.crop);
                            EditImageActivity.this.ivFilter.setImageResource(R.drawable.color_press);
                        }
                    }, 600L);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect);
                } else if (EditImageActivity.this.isFilter) {
                    EditImageActivity editImageActivity = EditImageActivity.this;
                    editImageActivity.isFilter = false;
                    editImageActivity.rlFilterView.setVisibility(View.GONE);
                    EditImageActivity.this.ivCrop.setImageResource(R.drawable.crop);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color1);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect);
                    EditImageActivity.this.takePhoto();
                } else {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(EditImageActivity.this.getContentResolver(), EditImageActivity.this.imageUri);
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                    EditImageActivity.this.glRootView.setAspectRatio(bitmap.getWidth(), bitmap.getHeight());
                    bitmap.recycle();
                    Log.d("imagepath", EditImageActivity.this.imageUri.getPath() + "");
                    EditImageActivity.this.glWrapper.setFilePath(EditImageActivity.this.imageUri.getPath());
                    EditImageActivity editImageActivity2 = EditImageActivity.this;
                    editImageActivity2.isFilter = true;
                    editImageActivity2.rlFilterView.setVisibility(View.VISIBLE);
                    EditImageActivity.this.ivCrop.setImageResource(R.drawable.crop);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color_press);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect);
                }
            }
        });
        this.llColorEdit.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.tvDone.setEnabled(true);
                EditImageActivity.this.tvDone.setTextColor(ContextCompat.getColor(EditImageActivity.this, R.color.yellow));
                if (EditImageActivity.this.rlFilterView.getVisibility() == View.VISIBLE) {
                    EditImageActivity.this.rlFilterView.setVisibility(View.GONE);
                    EditImageActivity.this.llFilter.performClick();
                    EditImageActivity editImageActivity = EditImageActivity.this;
                    editImageActivity.isColorEffect = true;
                    editImageActivity.ivCrop.setImageResource(R.drawable.crop);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color1);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect_press);
                    EditImageActivity.this.rlColorFilter.setVisibility(View.VISIBLE);
                    EditImageActivity.this.llAnimationFilter.setVisibility(View.GONE);
                    EditImageActivity.this.llAnimationFilter.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.EditImageActivity.5.1
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                EditImageActivity.this.ivEditImage.setImageBitmap(MediaStore.Images.Media.getBitmap(EditImageActivity.this.getContentResolver(), EditImageActivity.this.imageUri));
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    }, 500L);
                } else if (EditImageActivity.this.isColorEffect) {
                    EditImageActivity.this.ivCrop.setImageResource(R.drawable.crop);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color1);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect);
                    EditImageActivity.this.tvDone.setEnabled(true);
                    EditImageActivity.this.tvDone.setTextColor(ContextCompat.getColor(EditImageActivity.this, R.color.yellow));
                    try {
                        File file = new File(EditImageActivity.this.getFilesDir(), "Image.jpeg");
                        if (file.exists()) {
                            file.delete();
                        }
                        EditImageActivity.this.ivEditImage.setDrawingCacheEnabled(true);
                        Bitmap createBitmap = Bitmap.createBitmap(EditImageActivity.this.ivEditImage.getDrawingCache());
                        createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        EditImageActivity.this.imageBitmap = createBitmap;
                        EditImageActivity.this.ivImage.setImageBitmap(createBitmap);
                        EditImageActivity.this.imageUri = Uri.fromFile(file);
                        Log.d("imagepath", EditImageActivity.this.imageUri.getPath());
                        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.EditImageActivity.5.2
                            @Override // java.lang.Runnable
                            public void run() {
                                EditImageActivity.this.ivEditImage.setDrawingCacheEnabled(false);
                            }
                        }, 300L);
                    } catch (Exception e3) {
                        Log.e("Your Error Message", e3.getMessage());
                    }
                    EditImageActivity.this.rlColorFilter.setVisibility(View.GONE);
                    EditImageActivity.this.isColorEffect = false;
                } else {
                    EditImageActivity editImageActivity2 = EditImageActivity.this;
                    editImageActivity2.isColorEffect = true;
                    editImageActivity2.ivCrop.setImageResource(R.drawable.crop);
                    EditImageActivity.this.ivFilter.setImageResource(R.drawable.color1);
                    EditImageActivity.this.ivColorEdit.setImageResource(R.drawable.effect_press);
                    EditImageActivity.this.rlColorFilter.setVisibility(View.VISIBLE);
                    EditImageActivity.this.llAnimationFilter.setVisibility(View.GONE);
                    EditImageActivity.this.llAnimationFilter.setVisibility(View.VISIBLE);
                    new Handler();
                    try {
                        EditImageActivity.this.ivEditImage.setImageBitmap(MediaStore.Images.Media.getBitmap(EditImageActivity.this.getContentResolver(), EditImageActivity.this.imageUri));
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
            }
        });
        this.tvDone.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (EditImageActivity.this.rlFilterView.getVisibility() == View.VISIBLE) {
                    EditImageActivity.this.llFilter.performClick();
                    EditImageActivity.this.rlFilterView.setVisibility(View.GONE);
                } else if (EditImageActivity.this.rlColorFilter.getVisibility() == View.VISIBLE) {
                    EditImageActivity.this.llColorEdit.performClick();
                    EditImageActivity.this.rlColorFilter.setVisibility(View.GONE);
                } else {
                    MyApp.getInstance().isFromEdit = true;
                    EditImageActivity.this.rvProgress.setVisibility(View.VISIBLE);
                    Date date = new Date(System.currentTimeMillis());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    simpleDateFormat.format(date);
                    File file = new File(Const.PATH + "/" + ("IMG0" + format + "." + CameraActivity.getMimeType(Bitmap.CompressFormat.JPEG)));
                    if (file.exists()) {
                        file.delete();
                    }
                    try {
                        EditImageActivity.this.copyFile(new File(EditImageActivity.this.imageUri.getPath()), file);
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        EditImageActivity.this.rvProgress.setVisibility(View.GONE);
                    }
                    Log.d("aaaaaaa", EditImageActivity.this.imageUri.getPath() + "");
                }
            }
        });
        this.tvCancel.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.onBackPressed();
            }
        });
        this.ivFilterClose.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.llFilter.performClick();
            }
        });
        this.ivFilterDone.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.llFilter.performClick();
                EditImageActivity.this.takePhoto();
            }
        });
        this.ivColorClose.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.llColorEdit.performClick();
            }
        });
        this.ivColorDone.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.llColorEdit.performClick();
                EditImageActivity.this.tvDone.setEnabled(true);
                EditImageActivity.this.tvDone.setTextColor(ContextCompat.getColor(EditImageActivity.this, R.color.yellow));
                try {
                    File file = new File(EditImageActivity.this.getFilesDir(), "Image.jpeg");
                    if (file.exists()) {
                        file.delete();
                    }
                    EditImageActivity.this.ivEditImage.setDrawingCacheEnabled(true);
                    Bitmap createBitmap = Bitmap.createBitmap(EditImageActivity.this.ivEditImage.getDrawingCache());
                    createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    EditImageActivity.this.imageBitmap = createBitmap;
                    EditImageActivity.this.ivImage.setImageBitmap(createBitmap);
                    EditImageActivity.this.imageUri = Uri.fromFile(file);
                    Log.d("imagepath", EditImageActivity.this.imageUri.getPath());
                } catch (Exception e3) {
                    Log.e("Your Error Message", e3.getMessage());
                }
            }
        });
        this.seekbar_brightness = (DiscreteSeekBar) findViewById(R.id.seekbar_brightness);
        this.seekbarContrast = (DiscreteSeekBar) findViewById(R.id.seekbarContrast);
        this.seekbarSaturation = (DiscreteSeekBar) findViewById(R.id.seekbarSaturation);
        this.seekbar_brightness.setProgress(this.ivEditImage.getBrightness() + 280);
        this.seekbarSaturation.setProgress(115);
        this.seekbarContrast.setProgress((int) (this.ivEditImage.getContrast() * 130.0f));
        this.seekbar_brightness.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.12
            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            }

            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            }

            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i2, boolean z) {
                EditImageActivity.this.ivEditImage.setBrightness(i2 - 255).updateStyle();
            }
        });
        this.seekbarSaturation.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.13
            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            }

            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            }

            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i2, boolean z) {
                EditImageActivity.this.ivEditImage.setMode(0).updateStyle();
                EditImageActivity.this.ivEditImage.setSaturation(i2 / 100.0f).updateStyle();
            }
        });
        this.seekbarContrast.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.14
            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            }

            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
            }

            @Override // org.adw.library.widgets.discreteseekbar.DiscreteSeekBar.OnProgressChangeListener
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i2, boolean z) {
                EditImageActivity.this.ivEditImage.setContrast(i2 / 100.0f).updateStyle();
            }
        });
        this.ivBrightness.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.llAnimationFilter.setVisibility(View.GONE);
                EditImageActivity.this.seekbar_brightness.setVisibility(View.VISIBLE);
                EditImageActivity.this.seekbarContrast.setVisibility(View.GONE);
                EditImageActivity.this.seekbarSaturation.setVisibility(View.GONE);
                EditImageActivity.this.tvFilterName.setText("Brightness");
            }
        });
        this.ivContrast.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.llAnimationFilter.setVisibility(View.GONE);
                EditImageActivity.this.seekbar_brightness.setVisibility(View.GONE);
                EditImageActivity.this.seekbarContrast.setVisibility(View.VISIBLE);
                EditImageActivity.this.seekbarSaturation.setVisibility(View.GONE);
                EditImageActivity.this.tvFilterName.setText(ExifInterface.TAG_CONTRAST);
            }
        });
        this.ivsaturation.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.llAnimationFilter.setVisibility(View.GONE);
                EditImageActivity.this.seekbar_brightness.setVisibility(View.GONE);
                EditImageActivity.this.seekbarContrast.setVisibility(View.GONE);
                EditImageActivity.this.seekbarSaturation.setVisibility(View.VISIBLE);
                EditImageActivity.this.tvFilterName.setText(ExifInterface.TAG_SATURATION);
            }
        });
        this.ivMenu.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EditImageActivity.this.llAnimationFilter.setVisibility(View.VISIBLE);
            }
        });
    }

    public void copyFile(File file, File file2) throws IOException {
        if (file.exists()) {
            FileChannel channel = new FileInputStream(file).getChannel();
            FileChannel channel2 = new FileOutputStream(file2).getChannel();
            if (channel2 != null && channel != null) {
                channel2.transferFrom(channel, 0L, channel.size());
            }
            if (channel != null) {
                channel.close();
            }
            if (channel2 != null) {
                channel2.close();
            }
            this.rvProgress.setVisibility(View.GONE);
            finish();
        }
    }

    public void takePhoto() {
        try {
            final Bitmap createBitmap = Bitmap.createBitmap(this.glRootView.getWidth(), this.glRootView.getHeight(), Bitmap.Config.ARGB_8888);
            final HandlerThread handlerThread = new HandlerThread("PixelCopier");
            handlerThread.start();
            this.tvDone.setEnabled(true);
            this.tvDone.setTextColor(ContextCompat.getColor(this, R.color.yellow));
            if (Build.VERSION.SDK_INT >= 24) {
                PixelCopy.request(this.glRootView, createBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: com.cameraediter.iphone11pro.EditImageActivity.19
                    @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                    public void onPixelCopyFinished(int i) {
                        if (i == 0) {
                            EditImageActivity editImageActivity = EditImageActivity.this;
                            editImageActivity.imageBitmap = createBitmap;
                            editImageActivity.ivImage.setImageBitmap(createBitmap);
                            File file = new File(EditImageActivity.this.getFilesDir(), "Image.jpeg");
                            if (file.exists()) {
                                file.delete();
                            }
                            createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                createBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                            EditImageActivity.this.imageUri = Uri.fromFile(file);
                            Log.d("imagepath", file.getAbsolutePath());
                        } else {
                            EditImageActivity editImageActivity2 = EditImageActivity.this;
                            Toast.makeText(editImageActivity2, "Failed to copyPixels: " + i,  Toast.LENGTH_LONG).show();
                        }
                        handlerThread.quitSafely();
                    }
                }, new Handler(handlerThread.getLooper()));
            } else {
                this.glRootView.queueEvent(new Runnable() { // from class: com.cameraediter.iphone11pro.EditImageActivity.20
                    @Override // java.lang.Runnable
                    public void run() {
                        GL10 gl10 = (GL10) ((EGL10) EGLContext.getEGL()).eglGetCurrentContext().getGL();
                        EditImageActivity editImageActivity = EditImageActivity.this;
                        final Bitmap createBitmapFromGLSurface = editImageActivity.createBitmapFromGLSurface(0, 0, editImageActivity.glRootView.getWidth(), EditImageActivity.this.glRootView.getHeight(), gl10);
                        EditImageActivity.this.runOnUiThread(new Runnable() { // from class: com.cameraediter.iphone11pro.EditImageActivity.20.1
                            @Override // java.lang.Runnable
                            public void run() {
                                EditImageActivity.this.imageBitmap = createBitmapFromGLSurface;
                                EditImageActivity.this.ivImage.setImageBitmap(createBitmapFromGLSurface);
                                File file = new File(EditImageActivity.this.getFilesDir(), "Image.jpeg");
                                if (file.exists()) {
                                    file.delete();
                                }
                                createBitmapFromGLSurface.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                                try {
                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                    createBitmapFromGLSurface.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                                EditImageActivity.this.imageUri = Uri.fromFile(file);
                                Log.d("imagepath", file.getAbsolutePath());
                                if (createBitmap != null) {
                                    Log.e("#DEBUG", "   getHeight:  " + createBitmap.getHeight());
                                }
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap createBitmapFromGLSurface(int i, int i2, int i3, int i4, GL10 gl10) {
        int i5 = i3 * i4;
        int[] iArr = new int[i5];
        int[] iArr2 = new int[i5];
        IntBuffer wrap = IntBuffer.wrap(iArr);
        wrap.position(0);
        try {
            gl10.glReadPixels(i, i2, i3, i4, 6408, FujifilmMakernoteDirectory.TAG_FILM_MODE, wrap);
            for (int i6 = 0; i6 < i4; i6++) {
                int i7 = i6 * i3;
                int i8 = ((i4 - i6) - 1) * i3;
                for (int i9 = 0; i9 < i3; i9++) {
                    int i10 = iArr[i7 + i9];
                    iArr2[i8 + i9] = ((i10 >> 16) & 255) | ((-16711936) & i10) | ((i10 << 16) & 16711680);
                }
            }
            return Bitmap.createBitmap(iArr2, i3, i4, Bitmap.Config.ARGB_8888);
        } catch (GLException e) {
            Log.e("#DEBUG", "createBitmapFromGLSurface: " + e.getMessage(), e);
            return null;
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 69) {
            Uri output = UCrop.getOutput(intent);
            this.imageUri = output;
            try {
                this.ivImage.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), output));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.llCrop.performClick();
            this.tvDone.setEnabled(true);
            this.tvDone.setTextColor(ContextCompat.getColor(this, R.color.yellow));
            return;
        }
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), this.imageUri);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        this.ivImage.setImageBitmap(bitmap);
        this.llCrop.performClick();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (this.rlFilterView.getVisibility() == View.VISIBLE) {
            this.rlFilterView.setVisibility(View.GONE);
            this.ivFilter.setImageResource(R.drawable.color1);
            this.isFilter = false;
        } else if (this.rlColorFilter.getVisibility() == View.VISIBLE) {
            this.rlColorFilter.setVisibility(View.GONE);
            this.ivColorEdit.setImageResource(R.drawable.effect);
            this.isColorEffect = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }
}
