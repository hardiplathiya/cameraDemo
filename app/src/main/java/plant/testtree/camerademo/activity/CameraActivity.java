package plant.testtree.camerademo.activity;

import static com.otaliastudios.cameraview.CameraView.getFilter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.SwpieCallBack;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Grid;
import com.otaliastudios.cameraview.controls.Hdr;
import com.otaliastudios.cameraview.controls.Mode;
import com.otaliastudios.cameraview.controls.Preview;
import com.otaliastudios.cameraview.controls.WhiteBalance;
import com.otaliastudios.cameraview.filter.Filters;
import com.otaliastudios.cameraview.filter.OneParameterFilter;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;
import de.hdodenhof.circleimageview.CircleImageView;
import plant.testtree.camerademo.R;
import plant.testtree.camerademo.activity.gallary.GalleryappActivity;
import plant.testtree.camerademo.adapter.FilterAdapter;
import plant.testtree.camerademo.filter.BrightnessFilter;
import plant.testtree.camerademo.model.ListModel;
import plant.testtree.camerademo.util.Const;
import plant.testtree.camerademo.util.GPSTracker;
import plant.testtree.camerademo.util.WheelView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class CameraActivity extends AppCompatActivity implements WheelView.OnWheelItemSelectedListener {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;
    public String[] FilePathStrings;
    Timer T;
    AlertDialog alertDialog;
    AudioManager audioManager;
    IndicatorSeekBar brightness_seekbar;
    public CameraView camera;
    private ImageView camera_switcher;
    int count;
    public int counter;
    public Date date;
    File file;
    CircleImageView image_thumb;
    double latitude;
    public File[] listFile;
    LinearLayout llAllFeature;
    LinearLayout llBrightness;
    LinearLayout llFunction;
    LinearLayout llGrid;
    LinearLayout llSce;
    LinearLayout llTimer;
    LinearLayout llTimerClick;
    LinearLayout llWhiteBalance;
    double longitude;
    public WheelView mWheelview;
    TextView menu_3;
    TextView menu_4;
    ImageView menu_action;
    ImageView menu_auto;
    ImageView menu_aw;
    ImageView menu_brightness;
    ImageView menu_cloudy;
    ImageView menu_daylight;
    ImageView menu_filter;
    ImageView menu_flash;
    ImageView menu_fluorescent;
    ImageView menu_hdr;
    ImageView menu_image;
    ImageView menu_incandescent;
    ImageView menu_location;
    ImageView menu_night;
    TextView menu_off;
    TextView menu_phi;
    ImageView menu_sce;
    ImageView menu_scenone;
    ImageView menu_setting;
    ImageView menu_time;
    TextView menu_timer3s;
    TextView menu_timer5s;
    TextView menu_timer9s;
    TextView menu_timerclose;
    ImageView menu_volume;
    public MediaPlayer mp;
    RelativeLayout rlAw;
    RelativeLayout rlBright;
    RelativeLayout rlFlash;
    RelativeLayout rlHdr;
    RelativeLayout rlImage;
    RelativeLayout rlLocation;
    RelativeLayout rlSce;
    RelativeLayout rlSetting;
    RelativeLayout rlSwitcher;
    RelativeLayout rlTime;
    RelativeLayout rlVolume;
    RecyclerView rvFilterList;
    LinearLayout setting_feature;
    ImageView shutter_button;
    TextView timerCounter;
    TextView timerText;
    TextView tvFunctionName;
    TextView tvToast;
    int adLoad = 0;
    ArrayList<ListModel> arrayPhotoVideo = new ArrayList<>();
    int clickCount = 0;
    public Handler customHandler = new Handler();
    int fladhMode = 0;
    int flashPos = 0;
    int i = 0;
    boolean isFilterAd = false;
    boolean isHdr = false;
    boolean isLocation = true;
    boolean isOff = false;
    boolean isRecordvdo = false;
    boolean isSound = true;
    public final Filters[] mAllFilters = Filters.values();
    public int mCurrentFilter = 0;
    public int sce = 0;
    public long startTime = 0;
    long timeInMilliseconds = 0;
    long timeSwapBuff = 0;
    int timer = 0;
    private Runnable updateTimerThread = new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.1
        @Override // java.lang.Runnable
        public void run() {
            CameraActivity.this.timeInMilliseconds = SystemClock.uptimeMillis() - CameraActivity.this.startTime;
            CameraActivity cameraActivity = CameraActivity.this;
            cameraActivity.updatedTime = cameraActivity.timeSwapBuff + CameraActivity.this.timeInMilliseconds;
            int i = (int) (CameraActivity.this.updatedTime / 1000);
            int i2 = i / 60;
            int i3 = i2 / 60;
            TextView textView = CameraActivity.this.timerText;
            textView.setText("0" + i2 + ":" + String.format("%02d", Integer.valueOf(i % 60)));
            CameraActivity.this.customHandler.postDelayed(this, 0L);
        }
    };
    long updatedTime = 0;

    @Override // com.cameraediter.iphone11pro.WheelView.OnWheelItemSelectedListener
    public void onWheelItemChanged(WheelView wheelView, int i) {
    }

    public static String getMimeType(Bitmap.CompressFormat compressFormat) {
        int i = AnonymousClass44.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()];
        if (i != 1) {
            if (i != 2) {
            }
            return "png";
        }
        return "jpeg";
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_camera);
        this.mp = MediaPlayer.create(this, (int) R.raw.camera);
        this.audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        AudioManager audioManager = this.audioManager;
        audioManager.setStreamVolume(3, audioManager.getStreamVolume(3), 0);

        checkPermission();
        try {
            this.camera = (CameraView) findViewById(R.id.camera);
            this.camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.menu_flash = (ImageView) findViewById(R.id.menu_flash);
        this.menu_hdr = (ImageView) findViewById(R.id.menu_hdr);
        this.menu_time = (ImageView) findViewById(R.id.menu_time);
        this.menu_filter = (ImageView) findViewById(R.id.menu_filter);
        this.shutter_button = (ImageView) findViewById(R.id.shutter_button);
        this.image_thumb = (CircleImageView) findViewById(R.id.image_thumb);
        this.timerCounter = (TextView) findViewById(R.id.timerCounter);
        this.tvToast = (TextView) findViewById(R.id.tvToast);
        this.llFunction = (LinearLayout) findViewById(R.id.llFunction);
        this.llTimer = (LinearLayout) findViewById(R.id.llTimer);
        this.timerText = (TextView) findViewById(R.id.timerText);
        this.setting_feature = (LinearLayout) findViewById(R.id.setting_feature);
        this.llAllFeature = (LinearLayout) findViewById(R.id.llAllFeature);
        this.llWhiteBalance = (LinearLayout) findViewById(R.id.llWhiteBalance);
        this.llSce = (LinearLayout) findViewById(R.id.llSce);
        this.menu_setting = (ImageView) findViewById(R.id.menu_setting);
        this.menu_aw = (ImageView) findViewById(R.id.menu_aw);
        this.menu_incandescent = (ImageView) findViewById(R.id.menu_incandescent);
        this.menu_fluorescent = (ImageView) findViewById(R.id.menu_fluorescent);
        this.menu_auto = (ImageView) findViewById(R.id.menu_auto);
        this.menu_daylight = (ImageView) findViewById(R.id.menu_daylight);
        this.menu_cloudy = (ImageView) findViewById(R.id.menu_cloudy);
        this.menu_location = (ImageView) findViewById(R.id.menu_location);
        this.menu_sce = (ImageView) findViewById(R.id.menu_sce);
        this.menu_action = (ImageView) findViewById(R.id.menu_action);
        this.menu_night = (ImageView) findViewById(R.id.menu_night);
        this.menu_scenone = (ImageView) findViewById(R.id.menu_scenone);
        this.menu_volume = (ImageView) findViewById(R.id.menu_volume);
        this.tvFunctionName = (TextView) findViewById(R.id.tvFunctionName);
        this.menu_timerclose = (TextView) findViewById(R.id.menu_timerclose);
        this.menu_timer3s = (TextView) findViewById(R.id.menu_timer3s);
        this.menu_timer5s = (TextView) findViewById(R.id.menu_timer5s);
        this.menu_timer9s = (TextView) findViewById(R.id.menu_timer9s);
        this.menu_brightness = (ImageView) findViewById(R.id.menu_brightness);
        this.menu_image = (ImageView) findViewById(R.id.menu_image);
        this.menu_off = (TextView) findViewById(R.id.menu_off);
        this.menu_3 = (TextView) findViewById(R.id.menu_3);
        this.menu_4 = (TextView) findViewById(R.id.menu_4);
        this.menu_phi = (TextView) findViewById(R.id.menu_phi);
        this.llTimerClick = (LinearLayout) findViewById(R.id.llTimerClick);
        this.llGrid = (LinearLayout) findViewById(R.id.llGrid);
        this.llBrightness = (LinearLayout) findViewById(R.id.llBrightness);
        this.rlFlash = (RelativeLayout) findViewById(R.id.rlFlash);
        this.rlSetting = (RelativeLayout) findViewById(R.id.rlSetting);
        this.rlHdr = (RelativeLayout) findViewById(R.id.rlHdr);
        this.rlSwitcher = (RelativeLayout) findViewById(R.id.rlSwitcher);
        this.rlTime = (RelativeLayout) findViewById(R.id.rlTime);
        this.rlLocation = (RelativeLayout) findViewById(R.id.rlLocation);
        this.rlImage = (RelativeLayout) findViewById(R.id.rlImage);
        this.rlAw = (RelativeLayout) findViewById(R.id.rlAw);
        this.rlVolume = (RelativeLayout) findViewById(R.id.rlVolume);
        this.rlBright = (RelativeLayout) findViewById(R.id.rlBright);
        this.rlSce = (RelativeLayout) findViewById(R.id.rlSce);
        if (!Environment.getExternalStorageState().equals("mounted")) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
        } else {
             this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "iCamera");
            this.file.mkdirs();
        }
        try {
            if (this.file.isDirectory()) {
                this.listFile = this.file.listFiles();
                try {
                    Arrays.sort(this.listFile, new Comparator<File>() { // from class: com.cameraediter.iphone11pro.CameraActivity.2
                        @Override // java.util.Comparator
                        public int compare(File file, File file2) {
                            return Long.valueOf(file.lastModified()).compareTo(Long.valueOf(file2.lastModified()));
                        }
                    });
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.FilePathStrings = new String[this.listFile.length];
                if (this.listFile.length != 0) {
                    Glide.with((FragmentActivity) this).load(this.listFile[this.listFile.length - 1].getAbsolutePath()).into(this.image_thumb);
                    this.i += this.listFile.length;
                } else {
                    this.image_thumb.setImageResource(R.drawable.ic_launcher_background);
                }
                this.i = 0;
                while (this.i < this.FilePathStrings.length) {
                    this.arrayPhotoVideo.add(new ListModel(this.FilePathStrings[this.i]));
                    this.i++;
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        getcurrentlocationinfo();
        this.mWheelview = (WheelView) findViewById(R.id.wheelview);
        this.mWheelview.setItems(Arrays.asList(getResources().getStringArray(R.array.select_Item)));
        this.mWheelview.setOnWheelItemSelectedListener(this);
        this.mWheelview.setVisibility(View.VISIBLE);
        this.mWheelview.selectIndex(1);
        this.shutter_button.setImageResource(R.drawable.btn_photo_shutter);
        this.camera_switcher = (ImageView) findViewById(R.id.camera_switcher);
        this.camera_switcher.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CameraActivity.this.toggleCamera();
            }
        });
        this.menu_flash.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CameraActivity.this.toggleFlash();
            }
        });
        this.menu_hdr.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getHdr() == Hdr.OFF) {
                    CameraActivity.this.titleShow("HDR ON");
                    CameraActivity.this.camera.setHdr(Hdr.ON);
                    CameraActivity.this.menu_hdr.setImageResource(R.drawable.ic_launcher_background);
                } else if (CameraActivity.this.camera.getHdr() == Hdr.ON) {
                    CameraActivity.this.titleShow("HDR OFF");
                    CameraActivity.this.camera.setHdr(Hdr.OFF);
                    CameraActivity.this.menu_hdr.setImageResource(R.drawable.hdr_off);
                }
            }
        });
        this.shutter_button.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CameraActivity.this.adLoad++;
                if (CameraActivity.this.adLoad >= 2) {
                    CameraActivity.this.adLoad = 0;
                }
                if (CameraActivity.this.setting_feature.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.menu_setting.performClick();
                }
                if (CameraActivity.this.llTimerClick.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.menu_time.performClick();
                }
                CameraActivity.this.rvFilterList.setVisibility(View.GONE);
                CameraActivity.this.clickCount++;
                if (CameraActivity.this.camera.getMode() == Mode.VIDEO) {
                    CameraActivity.this.shutter_button.setEnabled(false);
                    new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.6.1
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraActivity.this.shutter_button.setEnabled(true);
                        }
                    }, 2000L);
                    CameraActivity.this.captureVideo();
                    return;
                }
                CameraActivity.this.shutter_button.setEnabled(false);
                CameraActivity.this.capturePhoto();
            }
        });
        this.image_thumb.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.setting_feature.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.menu_setting.performClick();
                }
                if (CameraActivity.this.llTimerClick.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.menu_time.performClick();
                }
                CameraActivity.this.rvFilterList.setVisibility(View.GONE);
                CameraActivity.this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + "iCamera");
                if (!CameraActivity.this.file.exists()) {
                    CameraActivity.this.file.mkdirs();
                }
                if (CameraActivity.this.file.isDirectory()) {
                    CameraActivity.this.arrayPhotoVideo.clear();
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.listFile = cameraActivity.file.listFiles();
                    Arrays.sort(CameraActivity.this.listFile, new Comparator<File>() { // from class: com.cameraediter.iphone11pro.CameraActivity.7.1
                        @Override // java.util.Comparator
                        public int compare(File file, File file2) {
                            return Long.valueOf(file.lastModified()).compareTo(Long.valueOf(file2.lastModified()));
                        }
                    });
                    CameraActivity cameraActivity2 = CameraActivity.this;
                    cameraActivity2.FilePathStrings = new String[cameraActivity2.listFile.length];
                    for (String str : CameraActivity.this.FilePathStrings) {
                        CameraActivity.this.arrayPhotoVideo.add(new ListModel(str));
                    }
                }
                try {
                    if (CameraActivity.this.listFile.length == 0) {
                        Toast.makeText(CameraActivity.this, "No Media Found", Toast.LENGTH_SHORT).show();
                    } else {
                        CameraActivity.this.startActivity(new Intent(CameraActivity.this, GalleryappActivity.class));
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        });
        this.menu_time.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.setting_feature.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.menu_setting.performClick();
                }
                if (CameraActivity.this.llTimerClick.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.llTimerClick.setVisibility(View.GONE);
                    CameraActivity.this.menu_time.setImageResource(R.drawable.timer);
                    CameraActivity.this.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"));
                    return;
                }
                CameraActivity.this.llTimerClick.setVisibility(View.VISIBLE);
                CameraActivity.this.menu_time.setImageResource(R.drawable.timer_on);
                CameraActivity.this.llAllFeature.setBackgroundColor(Color.parseColor("#66000000"));
            }
        });
        this.menu_filter.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - CameraActivity.this.date.getTime());
                if (CameraActivity.this.rvFilterList.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.rvFilterList.setVisibility(View.GONE);
                } else {
                    CameraActivity.this.date = new Date();
                    CameraActivity.this.rvFilterList.setVisibility(View.VISIBLE);
                }
            }
        });
        this.menu_setting.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.setting_feature.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.setting_feature.setVisibility(View.GONE);
                    CameraActivity.this.llWhiteBalance.setVisibility(View.GONE);
                    CameraActivity.this.menu_aw.setImageResource(R.drawable.aw);
                    CameraActivity.this.llSce.setVisibility(View.GONE);
                    CameraActivity.this.menu_sce.setImageResource(R.drawable.sce_white_off);
                    CameraActivity.this.llBrightness.setVisibility(View.GONE);
                    CameraActivity.this.menu_brightness.setImageResource(R.drawable.brigh);
                    CameraActivity.this.llGrid.setVisibility(View.GONE);
                    CameraActivity.this.menu_image.setImageResource(R.drawable.grid);
                    CameraActivity.this.menu_setting.setImageResource(R.drawable.setting_white);
                    CameraActivity.this.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"));
                    return;
                }
                CameraActivity.this.llTimerClick.setVisibility(View.GONE);
                CameraActivity.this.menu_time.setImageResource(R.drawable.timer);
                CameraActivity.this.setting_feature.setVisibility(View.VISIBLE);
                CameraActivity.this.menu_setting.setImageResource(R.drawable.setting);
                CameraActivity.this.llAllFeature.setBackgroundColor(Color.parseColor("#66000000"));
            }
        });
        this.menu_location.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.isLocation) {
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.isLocation = false;
                    cameraActivity.menu_location.setImageResource(R.drawable.location_off);
                    return;
                }
                CameraActivity cameraActivity2 = CameraActivity.this;
                cameraActivity2.isLocation = true;
                cameraActivity2.menu_location.setImageResource(R.drawable.location);
            }
        });
        this.menu_aw.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.llWhiteBalance.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.llWhiteBalance.setVisibility(View.GONE);
                    CameraActivity.this.menu_aw.setImageResource(R.drawable.aw);
                    return;
                }
                CameraActivity.this.llWhiteBalance.setVisibility(View.VISIBLE);
                CameraActivity.this.menu_aw.setImageResource(R.drawable.aw_on);
                CameraActivity.this.llSce.setVisibility(View.GONE);
                CameraActivity.this.menu_sce.setImageResource(R.drawable.sce_white_off);
                CameraActivity.this.llGrid.setVisibility(View.GONE);
                CameraActivity.this.menu_image.setImageResource(R.drawable.grid);
                CameraActivity.this.llBrightness.setVisibility(View.GONE);
                CameraActivity.this.menu_brightness.setImageResource(R.drawable.brigh);
            }
        });
        this.menu_sce.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.llSce.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.llSce.setVisibility(View.GONE);
                    CameraActivity.this.menu_sce.setImageResource(R.drawable.sce_white_off);
                    return;
                }
                CameraActivity.this.llSce.setVisibility(View.VISIBLE);
                CameraActivity.this.menu_sce.setImageResource(R.drawable.sce_white_on);
                CameraActivity.this.llWhiteBalance.setVisibility(View.GONE);
                CameraActivity.this.menu_aw.setImageResource(R.drawable.aw);
                CameraActivity.this.llGrid.setVisibility(View.GONE);
                CameraActivity.this.menu_image.setImageResource(R.drawable.grid);
                CameraActivity.this.llBrightness.setVisibility(View.GONE);
                CameraActivity.this.menu_brightness.setImageResource(R.drawable.brigh);
            }
        });
        this.menu_incandescent.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getWhiteBalance() != WhiteBalance.INCANDESCENT) {
                    CameraActivity.this.titleShow("WHITE BALANCE\nINCANDESCENT");
                    CameraActivity.this.menu_incandescent.setImageResource(R.drawable.light_on);
                    CameraActivity.this.menu_fluorescent.setImageResource(R.drawable.fluore);
                    CameraActivity.this.menu_auto.setImageResource(R.drawable.auto);
                    CameraActivity.this.menu_daylight.setImageResource(R.drawable.daylight);
                    CameraActivity.this.menu_cloudy.setImageResource(R.drawable.cloudy);
                    CameraActivity.this.camera.setWhiteBalance(WhiteBalance.INCANDESCENT);
                }
            }
        });
        this.menu_fluorescent.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getWhiteBalance() != WhiteBalance.FLUORESCENT) {
                    CameraActivity.this.titleShow("WHITE BALANCE\nFLUORESCENT");
                    CameraActivity.this.menu_incandescent.setImageResource(R.drawable.light);
                    CameraActivity.this.menu_fluorescent.setImageResource(R.drawable.fluore_on);
                    CameraActivity.this.menu_auto.setImageResource(R.drawable.auto);
                    CameraActivity.this.menu_daylight.setImageResource(R.drawable.daylight);
                    CameraActivity.this.menu_cloudy.setImageResource(R.drawable.cloudy);
                    CameraActivity.this.camera.setWhiteBalance(WhiteBalance.FLUORESCENT);
                }
            }
        });
        this.menu_auto.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getWhiteBalance() != WhiteBalance.AUTO) {
                    CameraActivity.this.titleShow("WHITE BALANCE\nAUTO");
                    CameraActivity.this.menu_incandescent.setImageResource(R.drawable.light);
                    CameraActivity.this.menu_fluorescent.setImageResource(R.drawable.fluore);
                    CameraActivity.this.menu_auto.setImageResource(R.drawable.auto_on);
                    CameraActivity.this.menu_daylight.setImageResource(R.drawable.daylight);
                    CameraActivity.this.menu_cloudy.setImageResource(R.drawable.cloudy);
                    CameraActivity.this.camera.setWhiteBalance(WhiteBalance.AUTO);
                }
            }
        });
        this.menu_daylight.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getWhiteBalance() != WhiteBalance.DAYLIGHT) {
                    CameraActivity.this.titleShow("WHITE BALANCE\nDAYLIGHT");
                    CameraActivity.this.menu_incandescent.setImageResource(R.drawable.light);
                    CameraActivity.this.menu_fluorescent.setImageResource(R.drawable.fluore);
                    CameraActivity.this.menu_auto.setImageResource(R.drawable.auto);
                    CameraActivity.this.menu_daylight.setImageResource(R.drawable.daylight_on);
                    CameraActivity.this.menu_cloudy.setImageResource(R.drawable.cloudy);
                    CameraActivity.this.camera.setWhiteBalance(WhiteBalance.DAYLIGHT);
                }
            }
        });
        this.menu_cloudy.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getWhiteBalance() != WhiteBalance.CLOUDY) {
                    CameraActivity.this.titleShow("WHITE BALANCE\nCLOUDY");
                    CameraActivity.this.menu_incandescent.setImageResource(R.drawable.light);
                    CameraActivity.this.menu_fluorescent.setImageResource(R.drawable.fluore);
                    CameraActivity.this.menu_auto.setImageResource(R.drawable.auto);
                    CameraActivity.this.menu_daylight.setImageResource(R.drawable.daylight);
                    CameraActivity.this.menu_cloudy.setImageResource(R.drawable.cloudy_on);
                    CameraActivity.this.camera.setWhiteBalance(WhiteBalance.CLOUDY);
                }
            }
        });
        this.menu_action.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.sce != 1) {
                    CameraActivity.this.titleShow("SCENE MODE\nACTION");
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.sce = 1;
                    cameraActivity.camera.setFilter(cameraActivity.mAllFilters[0].newInstance());
                    CameraActivity.this.menu_scenone.setImageResource(R.drawable.sce_off);
                    CameraActivity.this.menu_night.setImageResource(R.drawable.night);
                    CameraActivity.this.menu_action.setImageResource(R.drawable.action_on);
                }
            }
        });
        this.menu_night.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.sce != 2) {
                    CameraActivity.this.titleShow("SCENE MODE\nNIGHT");
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.sce = 2;
                    cameraActivity.camera.setFilter(cameraActivity.mAllFilters[21].newInstance());
                    CameraActivity.this.menu_scenone.setImageResource(R.drawable.sce_off);
                    CameraActivity.this.menu_night.setImageResource(R.drawable.night_on);
                    CameraActivity.this.menu_action.setImageResource(R.drawable.action);
                }
            }
        });
        this.menu_scenone.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.sce != 0) {
                    CameraActivity.this.titleShow("SCENE MODE\nNONE");
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.sce = 0;
                    cameraActivity.camera.setFilter(cameraActivity.mAllFilters[0].newInstance());
                    CameraActivity.this.menu_scenone.setImageResource(R.drawable.sce_on);
                    CameraActivity.this.menu_night.setImageResource(R.drawable.night);
                    CameraActivity.this.menu_action.setImageResource(R.drawable.action);
                }
            }
        });
        this.menu_volume.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.isSound) {
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.isSound = false;
                    cameraActivity.menu_volume.setImageResource(R.drawable.volume_mute);
                    return;
                }
                CameraActivity.this.menu_volume.setImageResource(R.drawable.volume);
                CameraActivity.this.isSound = true;
            }
        });
        this.menu_timerclose.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.23
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.timer != 0) {
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.timer = 0;
                    cameraActivity.menu_timerclose.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_back));
                    CameraActivity.this.menu_timer3s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer5s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer9s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                }
            }
        });
        this.menu_timer3s.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.timer != 3) {
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.timer = 3;
                    cameraActivity.menu_timerclose.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer3s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_back));
                    CameraActivity.this.menu_timer5s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer9s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                }
            }
        });
        this.menu_timer5s.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.25
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.timer != 5) {
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.timer = 5;
                    cameraActivity.menu_timerclose.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer3s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer5s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_back));
                    CameraActivity.this.menu_timer9s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                }
            }
        });
        this.menu_timer9s.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.timer != 9) {
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.timer = 9;
                    cameraActivity.menu_timerclose.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer3s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer5s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_timer9s.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_back));
                }
            }
        });
        this.menu_off.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.27
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getGrid() != Grid.OFF) {
                    CameraActivity.this.camera.setGrid(Grid.OFF);
                    CameraActivity.this.menu_off.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_back));
                    CameraActivity.this.menu_3.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_4.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_phi.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                }
            }
        });
        this.menu_3.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getGrid() != Grid.DRAW_3X3) {
                    CameraActivity.this.camera.setGrid(Grid.DRAW_3X3);
                    CameraActivity.this.menu_off.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_3.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_back));
                    CameraActivity.this.menu_4.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_phi.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                }
            }
        });
        this.menu_4.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.29
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getGrid() != Grid.DRAW_4X4) {
                    CameraActivity.this.camera.setGrid(Grid.DRAW_4X4);
                    CameraActivity.this.menu_off.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_3.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_4.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_back));
                    CameraActivity.this.menu_phi.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                }
            }
        });
        this.menu_phi.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.30
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.camera.getGrid() != Grid.DRAW_PHI) {
                    CameraActivity.this.camera.setGrid(Grid.DRAW_PHI);
                    CameraActivity.this.menu_off.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_3.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_4.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_white));
                    CameraActivity.this.menu_phi.setTextColor(ContextCompat.getColor(CameraActivity.this, R.color.color_back));
                }
            }
        });
        this.brightness_seekbar = (IndicatorSeekBar) findViewById(R.id.brightness_seekbar);
        this.brightness_seekbar.setOnSeekChangeListener(new OnSeekChangeListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.31
            @Override // com.warkiz.widget.OnSeekChangeListener
            public void onSeeking(SeekParams seekParams) {
            }

            @Override // com.warkiz.widget.OnSeekChangeListener
            public void onStartTrackingTouch(IndicatorSeekBar indicatorSeekBar) {
            }

            @Override // com.warkiz.widget.OnSeekChangeListener
            public void onStopTrackingTouch(IndicatorSeekBar indicatorSeekBar) {
                camera.setFilter(CameraActivity.this.mAllFilters[3].newInstance());
                if (indicatorSeekBar.getProgress() == -2) {
                    CameraActivity.this.titleShow("EXPOSURE\n-2");
                    com.otaliastudios.cameraview.filters.BrightnessFilter brightnessFilter = new com.otaliastudios.cameraview.filters.BrightnessFilter();
                    brightnessFilter.setBrightness(0.5F);
               //     CameraActivity.this.camera.setFilter(CameraActivity.this.mAllFilters[3].newInstance());
                } else if (indicatorSeekBar.getProgress() == -1) {
                    com.otaliastudios.cameraview.filters.BrightnessFilter brightnessFilter = new com.otaliastudios.cameraview.filters.BrightnessFilter();
                    brightnessFilter.setBrightness(1.0F);
                    CameraActivity.this.titleShow("EXPOSURE\n-1");
               //     CameraActivity.this.camera.setFilter(CameraActivity.this.mAllFilters[2].newInstance());
                } else if (indicatorSeekBar.getProgress() == 0) {
                    camera.setFilter(CameraActivity.this.mAllFilters[3].newInstance());
                    CameraActivity.this.titleShow("EXPOSURE\n0");
               //     CameraActivity.this.camera.setFilter(CameraActivity.this.mAllFilters[0].newInstance());
                } else if (indicatorSeekBar.getProgress() == 1) {
                    com.otaliastudios.cameraview.filters.BrightnessFilter brightnessFilter = new com.otaliastudios.cameraview.filters.BrightnessFilter();
                    brightnessFilter.setBrightness(1.5F);
                    CameraActivity.this.titleShow("EXPOSURE\n1");
                //    CameraActivity.this.camera.setFilter(CameraActivity.this.mAllFilters[1].newInstance());
                } else if (indicatorSeekBar.getProgress() == 2) {
                    com.otaliastudios.cameraview.filters.BrightnessFilter brightnessFilter = new com.otaliastudios.cameraview.filters.BrightnessFilter();
                    brightnessFilter.setBrightness(2F);
                    CameraActivity.this.titleShow("EXPOSURE\n2");
                   // CameraActivity.this.camera.setFilter(CameraActivity.this.mAllFilters[2].newInstance());
                }
            }
        });
        this.menu_brightness.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.32
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.llBrightness.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.llBrightness.setVisibility(View.GONE);
                    CameraActivity.this.menu_brightness.setImageResource(R.drawable.brigh);
                    return;
                }
                CameraActivity.this.llBrightness.setVisibility(View.VISIBLE);
                CameraActivity.this.menu_brightness.setImageResource(R.drawable.brigh_on);
                CameraActivity.this.llSce.setVisibility(View.GONE);
                CameraActivity.this.menu_sce.setImageResource(R.drawable.sce_white_off);
                CameraActivity.this.llWhiteBalance.setVisibility(View.GONE);
                CameraActivity.this.menu_aw.setImageResource(R.drawable.aw);
                CameraActivity.this.llGrid.setVisibility(View.GONE);
                CameraActivity.this.menu_image.setImageResource(R.drawable.grid);
            }
        });
        this.menu_image.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.33
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CameraActivity.this.llGrid.getVisibility() == View.VISIBLE) {
                    CameraActivity.this.llGrid.setVisibility(View.GONE);
                    CameraActivity.this.menu_image.setImageResource(R.drawable.grid);
                    return;
                }
                CameraActivity.this.llGrid.setVisibility(View.VISIBLE);
                CameraActivity.this.menu_image.setImageResource(R.drawable.grid_on);
                CameraActivity.this.llSce.setVisibility(View.GONE);
                CameraActivity.this.menu_sce.setImageResource(R.drawable.sce_white_off);
                CameraActivity.this.llWhiteBalance.setVisibility(View.GONE);
                CameraActivity.this.menu_aw.setImageResource(R.drawable.aw);
                CameraActivity.this.llBrightness.setVisibility(View.GONE);
                CameraActivity.this.menu_brightness.setImageResource(R.drawable.brigh);
            }
        });
        this.rvFilterList = (RecyclerView) findViewById(R.id.rvFilterList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        this.rvFilterList.setLayoutManager(linearLayoutManager);
        FilterAdapter filterAdapter = new FilterAdapter(this, this.mAllFilters);
        this.rvFilterList.setAdapter(filterAdapter);
        filterAdapter.setOnFilterChangeListener(new FilterAdapter.OnFilterChangeListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.34
            @Override // com.cameraediter.iphone11pro.adapter.FilterAdapter.OnFilterChangeListener
            public void onFilterChanged(int i) {
                if (CameraActivity.this.camera.getPreview() == Preview.GL_SURFACE) {
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.mCurrentFilter = i;
                    Filters filters = cameraActivity.mAllFilters[CameraActivity.this.mCurrentFilter];
                    TextView textView = CameraActivity.this.tvToast;
                    textView.setText(filters.toString() + "");
                    CameraActivity.this.tvToast.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.34.1
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraActivity.this.tvToast.startAnimation(AnimationUtils.loadAnimation(CameraActivity.this, R.anim.toast));
                        }
                    }, 300L);
                    handler.postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.34.2
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraActivity.this.tvToast.setVisibility(View.GONE);
                        }
                    }, 1300L);
                    CameraActivity.this.camera.setFilter(filters.newInstance());
                }
            }
        });

//        this.camera.setCall(new SwpieCallBack() { // from class: com.cameraediter.iphone11pro.CameraActivity.35
//            @Override // com.otaliastudios.cameraview.SwpieCallBack
//            public void onSwipeValue(int i) {
//                if (i != 0) {
//                    if (i == 1) {
//                        CameraActivity.this.shutter_button.setEnabled(true);
//                        if (CameraActivity.this.mWheelview.getSelectedPosition() == 2) {
//                            if (CameraActivity.this.camera.getFacing().equals(Facing.FRONT)) {
//                                CameraActivity.this.frontPhoto();
//                            } else {
//                                CameraActivity.this.backPhoto();
//                            }
//                            CameraActivity.this.mWheelview.selectIndex(1);
//                            CameraActivity cameraActivity = CameraActivity.this;
//                            cameraActivity.startTime = 0L;
//                            cameraActivity.timeInMilliseconds = 0L;
//                            cameraActivity.timeSwapBuff = 0L;
//                            cameraActivity.updatedTime = 0L;
//                            cameraActivity.camera.setMode(Mode.PICTURE);
//                            CameraActivity.this.llTimer.setVisibility(View.GONE);
//                            ViewGroup.LayoutParams layoutParams = CameraActivity.this.camera.getLayoutParams();
//                            layoutParams.width = -1;
//                            layoutParams.height = -1;
//                            CameraActivity cameraActivity2 = CameraActivity.this;
//                            cameraActivity2.set(cameraActivity2.camera, -1);
//                            CameraActivity.this.camera.setLayoutParams(layoutParams);
//                            CameraActivity.this.shutter_button.setImageResource(R.drawable.btn_photo_shutter);
//                            return;
//                        } else if (CameraActivity.this.mWheelview.getSelectedPosition() == 1) {
//                            if (CameraActivity.this.camera.getFacing().equals(Facing.FRONT)) {
//                                CameraActivity.this.frontVideo();
//                            } else {
//                                CameraActivity.this.backVideo();
//                            }
//                            CameraActivity.this.mWheelview.selectIndex(0);
//                            CameraActivity.this.camera.setMode(Mode.VIDEO);
//                            CameraActivity.this.llTimer.setVisibility(View.VISIBLE);
//                            CameraActivity.this.rvFilterList.setVisibility(View.GONE);
//                            CameraActivity.this.camera.setFilter(CameraActivity.this.mAllFilters[0].newInstance());
//                            ViewGroup.LayoutParams layoutParams2 = CameraActivity.this.camera.getLayoutParams();
//                            layoutParams2.width = -1;
//                            layoutParams2.height = -1;
//                            CameraActivity.this.camera.setLayoutParams(layoutParams2);
//                            CameraActivity cameraActivity3 = CameraActivity.this;
//                            cameraActivity3.set(cameraActivity3.camera, -1);
//                            CameraActivity.this.shutter_button.setImageResource(R.drawable.btn_new_shutter);
//                            return;
//                        } else {
//                            return;
//                        }
//                    }
//                    return;
//                }
//                CameraActivity.this.shutter_button.setEnabled(true);
//                if (CameraActivity.this.mWheelview.getSelectedPosition() == 0) {
//                    if (CameraActivity.this.camera.getFacing().equals(Facing.FRONT)) {
//                        CameraActivity.this.frontPhoto();
//                    } else {
//                        CameraActivity.this.backPhoto();
//                    }
//                    CameraActivity.this.mWheelview.selectIndex(1);
//                    CameraActivity cameraActivity4 = CameraActivity.this;
//                    cameraActivity4.startTime = 0L;
//                    cameraActivity4.timeInMilliseconds = 0L;
//                    cameraActivity4.timeSwapBuff = 0L;
//                    cameraActivity4.updatedTime = 0L;
//                    cameraActivity4.camera.setMode(Mode.PICTURE);
//                    CameraActivity.this.llTimer.setVisibility(View.GONE);
//                    ViewGroup.LayoutParams layoutParams3 = CameraActivity.this.camera.getLayoutParams();
//                    layoutParams3.width = -1;
//                    layoutParams3.height = -1;
//                    CameraActivity cameraActivity5 = CameraActivity.this;
//                    cameraActivity5.set(cameraActivity5.camera, -1);
//                    CameraActivity.this.camera.setLayoutParams(layoutParams3);
//                    CameraActivity.this.shutter_button.setImageResource(R.drawable.btn_photo_shutter);
//                } else if (CameraActivity.this.mWheelview.getSelectedPosition() == 1) {
//                    if (CameraActivity.this.camera.getFacing().equals(Facing.FRONT)) {
//                        CameraActivity.this.frontPhoto();
//                    } else {
//                        CameraActivity.this.backPhoto();
//                    }
//                    CameraActivity.this.mWheelview.selectIndex(2);
//                    CameraActivity cameraActivity6 = CameraActivity.this;
//                    cameraActivity6.startTime = 0L;
//                    cameraActivity6.timeInMilliseconds = 0L;
//                    cameraActivity6.timeSwapBuff = 0L;
//                    cameraActivity6.updatedTime = 0L;
//                    cameraActivity6.camera.setMode(Mode.PICTURE);
//                    CameraActivity.this.llTimer.setVisibility(View.GONE);
//                    ViewGroup.LayoutParams layoutParams4 = CameraActivity.this.camera.getLayoutParams();
//                    int measuredWidth = CameraActivity.this.camera.getMeasuredWidth();
//                    layoutParams4.width = -1;
//                    layoutParams4.height = measuredWidth;
//                    CameraActivity.this.camera.setLayoutParams(layoutParams4);
//                    CameraActivity cameraActivity7 = CameraActivity.this;
//                    cameraActivity7.set(cameraActivity7.camera, Integer.valueOf(measuredWidth));
//                    CameraActivity.this.shutter_button.setImageResource(R.drawable.btn_photo_shutter);
//                }
//            }
//
//            public void onTapValue(int i) {
//                if (CameraActivity.this.setting_feature.getVisibility() == View.VISIBLE) {
//                    CameraActivity.this.menu_setting.performClick();
//                }
//                if (CameraActivity.this.llTimerClick.getVisibility() == View.VISIBLE) {
//                    CameraActivity.this.menu_time.performClick();
//                }
//                CameraActivity.this.rvFilterList.setVisibility(View.GONE);
//            }
//        });
        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.36
            @Override // java.lang.Runnable
            public void run() {
                CameraActivity.this.getcurrentlocationinfo();
            }
        }, 10000L);
        this.date = new Date();
    }

    /* JADX WARN: Type inference failed for: r0v16, types: [com.cameraediter.iphone11pro.CameraActivity$37] */
    public void capturePhoto() {
        System.currentTimeMillis();
        this.camera.addCameraListener(new Listener());
        int i = this.timer;
        if (i != 0) {
            this.count = (i * 1000) + 1000;
            this.counter = i;
            new CountDownTimer(this.count, 1000L) { // from class: com.cameraediter.iphone11pro.CameraActivity.37
                @Override // android.os.CountDownTimer
                public void onTick(long j) {
                    CameraActivity.this.timerCounter.startAnimation(AnimationUtils.loadAnimation(CameraActivity.this.getApplicationContext(), R.anim.fadeout));
                    CameraActivity.this.timerCounter.setText(String.valueOf(CameraActivity.this.counter));
                    CameraActivity cameraActivity = CameraActivity.this;
                    cameraActivity.counter--;
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    CameraActivity.this.timerCounter.setText("");
                    if (CameraActivity.this.camera.getFlash() == Flash.ON) {
                        CameraActivity cameraActivity = CameraActivity.this;
                        cameraActivity.flashPos = 2;
                        cameraActivity.camera.setFlash(Flash.TORCH);
                        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.37.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (CameraActivity.this.isSound) {
                                    CameraActivity.this.mp.start();
                                }
                                CameraActivity.this.camera.takePictureSnapshot();
                            }
                        }, 500L);
                    } else if (CameraActivity.this.camera.getFlash() == Flash.AUTO) {
                        CameraActivity cameraActivity2 = CameraActivity.this;
                        cameraActivity2.flashPos = 1;
                        cameraActivity2.camera.setFlash(Flash.TORCH);
                        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.37.2
                            @Override // java.lang.Runnable
                            public void run() {
                                if (CameraActivity.this.isSound) {
                                    CameraActivity.this.mp.start();
                                }
                                CameraActivity.this.camera.takePictureSnapshot();
                            }
                        }, 500L);
                    } else {
                        CameraActivity cameraActivity3 = CameraActivity.this;
                        cameraActivity3.flashPos = 0;
                        if (cameraActivity3.isSound) {
                            CameraActivity.this.mp.start();
                        }
                        CameraActivity.this.camera.takePictureSnapshot();
                    }
                }
            }.start();
        } else if (this.camera.getFlash() == Flash.ON) {
            this.flashPos = 2;
            this.camera.setFlash(Flash.TORCH);
            new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.38
                @Override // java.lang.Runnable
                public void run() {
                    if (CameraActivity.this.isSound) {
                        CameraActivity.this.mp.start();
                    }
                    CameraActivity.this.camera.takePictureSnapshot();
                }
            }, 500L);
        } else if (this.camera.getFlash() == Flash.AUTO) {
            this.flashPos = 1;
            this.camera.setFlash(Flash.TORCH);
            new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.39
                @Override // java.lang.Runnable
                public void run() {
                    if (CameraActivity.this.isSound) {
                        CameraActivity.this.mp.start();
                    }
                    CameraActivity.this.camera.takePictureSnapshot();
                }
            }, 500L);
        } else {
            this.flashPos = 0;
            if (this.isSound) {
                this.mp.start();
            }
            this.camera.takePictureSnapshot();
        }
    }

    public void getImage(Bitmap bitmap) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        simpleDateFormat.format(date);
        final File file = new File(Const.PATH + "/IMG" + this.i + "" + format + "." + getMimeType(Bitmap.CompressFormat.JPEG));
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        runOnUiThread(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.40
            @Override // java.lang.Runnable
            public void run() {
                Glide.with((FragmentActivity) CameraActivity.this).load(file.getAbsolutePath()).into(CameraActivity.this.image_thumb);
                CameraActivity.this.image_thumb.setImageURI(Uri.fromFile(file));
                CameraActivity.this.image_thumb.startAnimation(AnimationUtils.loadAnimation(CameraActivity.this.getApplicationContext(), R.anim.fade_out));
            }
        });
        try {
            if (this.isLocation) {
                getLocation(file);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.arrayPhotoVideo.add(new ListModel(file.getAbsolutePath()));
        galleryAddPic(file.getAbsolutePath());
        int i = this.flashPos;
        if (i == 2) {
            this.camera.setFlash(Flash.ON);
        } else if (i == 1) {
            this.camera.setFlash(Flash.AUTO);
        }
    }

    public void captureVideo() {
        if (this.isRecordvdo) {
            this.isRecordvdo = false;
            this.timeSwapBuff += this.timeInMilliseconds;
            this.customHandler.removeCallbacks(this.updateTimerThread);
            this.timerText.setText("00:00");
            this.shutter_button.setImageResource(R.drawable.btn_new_shutter);
            this.camera.stopVideo();
            this.startTime = 0L;
            this.timeInMilliseconds = 0L;
            this.timeSwapBuff = 0L;
            this.updatedTime = 0L;
            if (this.camera.getFlash() == Flash.TORCH) {
                this.camera.setFlash(Flash.OFF);
                this.isOff = true;
                return;
            }
            return;
        }
        if (this.camera.getFlash() == Flash.ON) {
            this.camera.setFlash(Flash.TORCH);
        }
        this.isRecordvdo = true;
        this.startTime = SystemClock.uptimeMillis();
        this.customHandler.postDelayed(this.updateTimerThread, 0L);
        this.camera.addCameraListener(new Listener());
        this.camera.takeVideo(new File(getFilesDir(), "video.mp4"));
        this.shutter_button.setImageResource(R.drawable.btn_new_shutter_stop_video);
    }

    public void set(CameraView cameraView, Integer num) {
        cameraView.getLayoutParams().height = num.intValue();
        cameraView.setLayoutParams(cameraView.getLayoutParams());
    }

    @Override // com.cameraediter.iphone11pro.WheelView.OnWheelItemSelectedListener
    public void onWheelItemSelected(WheelView wheelView, int i) {
        if (i == 0) {
            if (this.camera.getFacing().equals(Facing.FRONT)) {
                frontVideo();
            } else {
                backVideo();
            }
            this.camera.setMode(Mode.VIDEO);
            this.llTimer.setVisibility(View.VISIBLE);
            this.rvFilterList.setVisibility(View.GONE);
            this.camera.setFilter(this.mAllFilters[0].newInstance());
            ViewGroup.LayoutParams layoutParams = this.camera.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            this.camera.setLayoutParams(layoutParams);
            set(this.camera, -1);
            this.shutter_button.setImageResource(R.drawable.btn_new_shutter);
        } else if (i == 1) {
            if (this.camera.getFacing().equals(Facing.FRONT)) {
                frontPhoto();
            } else {
                backPhoto();
            }
            this.startTime = 0L;
            this.timeInMilliseconds = 0L;
            this.timeSwapBuff = 0L;
            this.updatedTime = 0L;
            this.camera.setMode(Mode.PICTURE);
            this.llTimer.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams2 = this.camera.getLayoutParams();
            layoutParams2.width = -1;
            layoutParams2.height = -1;
            set(this.camera, -1);
            this.camera.setLayoutParams(layoutParams2);
            this.shutter_button.setImageResource(R.drawable.btn_photo_shutter);
        } else if (i == 2) {
            if (this.camera.getFacing().equals(Facing.FRONT)) {
                frontPhoto();
            } else {
                backPhoto();
            }
            this.startTime = 0L;
            this.timeInMilliseconds = 0L;
            this.timeSwapBuff = 0L;
            this.updatedTime = 0L;
            this.camera.setMode(Mode.PICTURE);
            this.llTimer.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams3 = this.camera.getLayoutParams();
            int measuredWidth = this.camera.getMeasuredWidth();
            layoutParams3.width = -1;
            layoutParams3.height = measuredWidth;
            this.camera.setLayoutParams(layoutParams3);
            set(this.camera, Integer.valueOf(measuredWidth));
            this.shutter_button.setImageResource(R.drawable.btn_photo_shutter);
        }
    }

    public void toggleFlash() {
        if (this.camera.getFlash() == Flash.OFF) {
            titleShow("FLASH MODE\nFLASH ON");
            this.camera.setFlash(Flash.ON);
            this.menu_flash.setImageResource(R.drawable.flash);
        } else if (this.camera.getFlash() == Flash.ON) {
            titleShow("FLASH MODE\nFLASH AUTO");
            this.camera.setFlash(Flash.AUTO);
            this.menu_flash.setImageResource(R.drawable.flash_auto);
        } else if (this.camera.getFlash() == Flash.AUTO) {
            titleShow("FLASH MODE\nFLASH OFF");
            this.camera.setFlash(Flash.OFF);
            this.menu_flash.setImageResource(R.drawable.flash_off);
        }
    }

    /* renamed from: com.cameraediter.iphone11pro.CameraActivity$44 */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass44 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$CompressFormat;
        static final /* synthetic */ int[] $SwitchMap$com$otaliastudios$cameraview$controls$Facing = new int[Facing.values().length];

        static {
            try {
                $SwitchMap$com$otaliastudios$cameraview$controls$Facing[Facing.BACK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$otaliastudios$cameraview$controls$Facing[Facing.FRONT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $SwitchMap$android$graphics$Bitmap$CompressFormat = new int[Bitmap.CompressFormat.values().length];
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.JPEG.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.PNG.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void toggleCamera() {
        if (this.camera.isTakingPicture() || this.camera.isTakingVideo()) {
            return;
        }
        int i = AnonymousClass44.$SwitchMap$com$otaliastudios$cameraview$controls$Facing[this.camera.toggleFacing().ordinal()];
        if (i == 1) {
            if (this.mWheelview.getSelectedPosition() == 0) {
                backVideo();
            } else {
                backPhoto();
            }
        } else if (i != 2) {
        } else {
            if (this.mWheelview.getSelectedPosition() == 0) {
                frontVideo();
            } else {
                frontPhoto();
            }
        }
    }

    public void galleryAddPic(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        sendBroadcast(intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.camera.open();
        this.date = new Date();
        this.rvFilterList.setVisibility(View.GONE);
        this.isRecordvdo = false;
        try {
            if (this.file.isDirectory()) {
                this.listFile = this.file.listFiles();
                Arrays.sort(this.listFile, new Comparator<File>() { // from class: com.cameraediter.iphone11pro.CameraActivity.41
                    @Override // java.util.Comparator
                    public int compare(File file, File file2) {
                        return Long.valueOf(file.lastModified()).compareTo(Long.valueOf(file2.lastModified()));
                    }
                });
                this.arrayPhotoVideo.clear();
                this.FilePathStrings = new String[this.listFile.length];
                if (this.listFile.length != 0) {
                    Glide.with((FragmentActivity) this).load(this.listFile[this.listFile.length - 1].getAbsolutePath()).into(this.image_thumb);
                    this.i += this.listFile.length;
                } else {
                    this.image_thumb.setImageResource(R.drawable.ic_launcher_background);
                }
                this.i = 0;
                while (this.i < this.FilePathStrings.length) {
                    this.arrayPhotoVideo.add(new ListModel(this.FilePathStrings[this.i]));
                    this.i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.camera.open();
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        this.camera.close();
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return true;
        }
        onBackPressed();
        return true;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 24) {
            if (action == 0) {
                this.audioManager.adjustVolume(1, 4);
                this.audioManager.adjustStreamVolume(3, 1, 1);
            }
            return true;
        } else if (keyCode != 25) {
            return super.dispatchKeyEvent(keyEvent);
        } else {
            if (action == 0) {
                this.audioManager.adjustVolume(-1, 4);
                this.audioManager.adjustStreamVolume(3, -1, 1);
            }
            return true;
        }
    }

    public void titleShow(String str) {
        this.tvFunctionName.setText(str);
        this.tvFunctionName.setVisibility(View.VISIBLE);
        this.tvFunctionName.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadeani));
        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.42
            @Override // java.lang.Runnable
            public void run() {
                CameraActivity.this.tvFunctionName.setVisibility(View.GONE);
            }
        }, 1400L);
    }

    public void frontPhoto() {
        if (this.setting_feature.getVisibility() == View.VISIBLE) {
            this.menu_setting.performClick();
        }
        if (this.llTimerClick.getVisibility() == View.VISIBLE) {
            this.menu_time.performClick();
        }
        this.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"));
        this.setting_feature.setVisibility(View.GONE);
        this.menu_setting.setImageResource(R.drawable.setting_white);
        this.llTimerClick.setVisibility(View.GONE);
        this.menu_time.setImageResource(R.drawable.timer);
        this.rlHdr.setVisibility(View.GONE);
        this.rlFlash.setVisibility(View.GONE);
        this.rlSwitcher.setVisibility(View.VISIBLE);
        this.rlTime.setVisibility(View.VISIBLE);
        this.rlSetting.setVisibility(View.VISIBLE);
        this.menu_filter.setVisibility(View.VISIBLE);
        this.rlLocation.setVisibility(View.VISIBLE);
        this.rlVolume.setVisibility(View.VISIBLE);
        this.rlBright.setVisibility(View.VISIBLE);
        this.rlAw.setVisibility(View.VISIBLE);
        this.rlSce.setVisibility(View.VISIBLE);
        this.rlImage.setVisibility(View.VISIBLE);
        if (this.camera.getFlash() == Flash.ON) {
            this.fladhMode = 2;
        } else if (this.camera.getFlash() == Flash.AUTO) {
            this.fladhMode = 1;
        } else {
            this.fladhMode = 0;
        }
    }

    public void backPhoto() {
        if (this.setting_feature.getVisibility() == View.VISIBLE) {
            this.menu_setting.performClick();
        }
        if (this.llTimerClick.getVisibility() == View.VISIBLE) {
            this.menu_time.performClick();
        }
        this.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"));
        this.setting_feature.setVisibility(View.GONE);
        this.menu_setting.setImageResource(R.drawable.setting_white);
        this.llTimerClick.setVisibility(View.GONE);
        this.menu_time.setImageResource(R.drawable.timer);
        this.rlHdr.setVisibility(View.VISIBLE);
        this.rlFlash.setVisibility(View.VISIBLE);
        this.rlSwitcher.setVisibility(View.VISIBLE);
        this.rlTime.setVisibility(View.VISIBLE);
        this.rlSetting.setVisibility(View.VISIBLE);
        this.menu_filter.setVisibility(View.VISIBLE);
        this.rlLocation.setVisibility(View.VISIBLE);
        this.rlVolume.setVisibility(View.VISIBLE);
        this.rlBright.setVisibility(View.VISIBLE);
        this.rlAw.setVisibility(View.VISIBLE);
        this.rlSce.setVisibility(View.VISIBLE);
        this.rlImage.setVisibility(View.VISIBLE);
        int i = this.fladhMode;
        if (i == 2) {
            this.camera.setFlash(Flash.ON);
        } else if (i == 1) {
            this.camera.setFlash(Flash.AUTO);
        } else {
            this.camera.setFlash(Flash.OFF);
        }
    }

    public void frontVideo() {
        if (this.setting_feature.getVisibility() == View.VISIBLE) {
            this.menu_setting.performClick();
        }
        if (this.llTimerClick.getVisibility() == View.VISIBLE) {
            this.menu_time.performClick();
        }
        this.brightness_seekbar.setProgress(0.0f);
        this.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"));
        this.setting_feature.setVisibility(View.GONE);
        this.menu_setting.setImageResource(R.drawable.setting_white);
        this.llTimerClick.setVisibility(View.GONE);
        this.menu_time.setImageResource(R.drawable.timer);
        this.rlHdr.setVisibility(View.GONE);
        this.rlFlash.setVisibility(View.GONE);
        this.rlTime.setVisibility(View.GONE);
        this.rlSetting.setVisibility(View.VISIBLE);
        this.rlSwitcher.setVisibility(View.VISIBLE);
        this.menu_filter.setVisibility(View.GONE);
        this.rlLocation.setVisibility(View.VISIBLE);
        this.rlVolume.setVisibility(View.VISIBLE);
        this.rlBright.setVisibility(View.GONE);
        this.rlAw.setVisibility(View.VISIBLE);
        this.rlSce.setVisibility(View.GONE);
        this.rlImage.setVisibility(View.VISIBLE);
    }

    public void backVideo() {
        if (this.setting_feature.getVisibility() == View.VISIBLE) {
            this.menu_setting.performClick();
        }
        if (this.llTimerClick.getVisibility() == View.VISIBLE) {
            this.menu_time.performClick();
        }
        this.brightness_seekbar.setProgress(0.0f);
        this.llAllFeature.setBackgroundColor(Color.parseColor("#00000000"));
        this.setting_feature.setVisibility(View.GONE);
        this.menu_setting.setImageResource(R.drawable.setting_white);
        this.llTimerClick.setVisibility(View.GONE);
        this.menu_time.setImageResource(R.drawable.timer);
        this.rlHdr.setVisibility(View.GONE);
        this.rlFlash.setVisibility(View.VISIBLE);
        this.rlTime.setVisibility(View.GONE);
        this.rlSetting.setVisibility(View.VISIBLE);
        this.rlSwitcher.setVisibility(View.VISIBLE);
        this.menu_filter.setVisibility(View.GONE);
        this.rlLocation.setVisibility(View.VISIBLE);
        this.rlVolume.setVisibility(View.VISIBLE);
        this.rlBright.setVisibility(View.GONE);
        this.rlAw.setVisibility(View.VISIBLE);
        this.rlSce.setVisibility(View.GONE);
        this.rlImage.setVisibility(View.VISIBLE);
    }

    public void getLocation(File file) {
        String str;
        String str2;
        try {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            double d = this.latitude;
            String[] split = Location.convert(Math.abs(d), Location.FORMAT_SECONDS).split(":");
            String[] split2 = split[2].split("\\.");
            if (split2.length == 0) {
                str = split[2];
            } else {
                str = split2[0];
            }
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE, split[0] + "/1," + split[1] + "/1," + str + "/1");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, d > 0.0d ? "N" : ExifInterface.LATITUDE_SOUTH);
            double d2 = this.longitude;
            String[] split3 = Location.convert(Math.abs(d2), Location.FORMAT_SECONDS).split(":");
            String[] split4 = split3[2].split("\\.");
            if (split4.length == 0) {
                str2 = split3[2];
            } else {
                str2 = split4[0];
            }
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, split3[0] + "/1," + split3[1] + "/1," + str2 + "/1");
            exifInterface.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, d2 > 0.0d ? ExifInterface.LONGITUDE_EAST : ExifInterface.LONGITUDE_WEST);
            exifInterface.saveAttributes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getcurrentlocationinfo() {
        ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION");
        ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION");
        GPSTracker gPSTracker = new GPSTracker(this);
        if (gPSTracker.canGetLocation()) {
            gPSTracker.getLocation();
            this.latitude = gPSTracker.getLatitude();
            this.longitude = gPSTracker.getLongitude();
        }
    }

    public void checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") + ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") + ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                this.camera = (CameraView) findViewById(R.id.camera);
                this.camera.open();
                File file = new File(Const.PATH);
                if (!file.exists()) {
                    file.mkdir();
                    file.mkdirs();
                }
                if (!Environment.getExternalStorageState().equals("mounted")) {
                    Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
                } else {
                     this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "iCamera");
                    this.file.mkdirs();
                }
                getcurrentlocationinfo();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.RECORD_AUDIO") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_MEDIA_IMAGES") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_MEDIA_AUDIO") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_COARSE_LOCATION")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Camera, Read External, and Write External Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.43
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(CameraActivity.this, new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_MEDIA_IMAGES",  "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
                    }
                });
                builder.setNeutralButton("Cancel", (DialogInterface.OnClickListener) null);
                builder.create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_MEDIA_IMAGES","android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
            }
        }else {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") + ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") + ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                this.camera = (CameraView) findViewById(R.id.camera);
                this.camera.open();
                File file = new File(Const.PATH);
                if (!file.exists()) {
                    file.mkdir();
                    file.mkdirs();
                }
                if (!Environment.getExternalStorageState().equals("mounted")) {
                    Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
                } else {
                     this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "iCamera");
                    this.file.mkdirs();
                }
                getcurrentlocationinfo();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.RECORD_AUDIO") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_COARSE_LOCATION")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Camera, Read External, and Write External Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.cameraediter.iphone11pro.CameraActivity.43
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(CameraActivity.this, new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
                    }
                });
                builder.setNeutralButton("Cancel", (DialogInterface.OnClickListener) null);
                builder.create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
            }
        }

    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity, androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 123) {
            if (iArr.length <= 0 || iArr[0] + iArr[1] + iArr[2] != 0) {
                Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show();
                return;
            }
            this.camera = (CameraView) findViewById(R.id.camera);
            this.camera.open();
            File file = new File(Const.PATH);
            if (!file.exists()) {
                file.mkdir();
                file.mkdirs();
            }
            if (!Environment.getExternalStorageState().equals("mounted")) {
                Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
            } else {
                 this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "iCamera");
                this.file.mkdirs();
            }
            getcurrentlocationinfo();
        }
    }

    /* loaded from: classes.dex */
    public class Listener extends CameraListener {
        @Override // com.otaliastudios.cameraview.CameraListener
        public void onCameraOpened(CameraOptions cameraOptions) {
        }

        private Listener() {
          //  CameraActivity.this = r1;
        }

        @Override // com.otaliastudios.cameraview.CameraListener
        public void onSwiperDetect(int i) {
            super.onSwiperDetect(i);
            Log.d("aaaa", i + "");
        }

        @Override // com.otaliastudios.cameraview.CameraListener
        public void onCameraError(CameraException cameraException) {
            super.onCameraError(cameraException);
            Toast.makeText(CameraActivity.this, "iCamera failed", Toast.LENGTH_SHORT).show();
        }

        @Override // com.otaliastudios.cameraview.CameraListener
        public void onPictureTaken(PictureResult pictureResult) {
            super.onPictureTaken(pictureResult);
            if (CameraActivity.this.flashPos == 1) {
                CameraActivity.this.camera.setFlash(Flash.OFF);
            } else if (CameraActivity.this.flashPos == 2) {
                CameraActivity.this.camera.setFlash(Flash.OFF);
            } else {
                CameraActivity.this.camera.setFlash(Flash.OFF);
            }
            CameraActivity.this.shutter_button.setEnabled(true);
            CameraActivity.this.camera.removeCameraListener(this);
            if (CameraActivity.this.camera.isTakingVideo()) {
                CameraActivity cameraActivity = CameraActivity.this;
                Toast.makeText(cameraActivity, "Captured while taking video. Size=" + pictureResult.getSize(),  Toast.LENGTH_SHORT).show();
                return;
            }
            System.currentTimeMillis();
            CameraActivity.this.getImage(BitmapFactory.decodeByteArray(pictureResult.getData(), 0, pictureResult.getData().length));
        }

        @Override // com.otaliastudios.cameraview.CameraListener
        public void onVideoTaken(VideoResult videoResult) {
            super.onVideoTaken(videoResult);
            CameraActivity.this.camera.removeCameraListener(this);
            Uri fromFile = Uri.fromFile(videoResult.getFile());
            try {
                new SimpleDateFormat("yyyyMMdd_HHmmss");
                String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + "iCamera");
                try {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final File file2 = new File(file, "VDO" + CameraActivity.this.i + format + ".mp4");
                InputStream openInputStream = CameraActivity.this.getContentResolver().openInputStream(fromFile);
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = openInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.close();
                openInputStream.close();
                CameraActivity.this.galleryAddPic(file2.getAbsolutePath());
                CameraActivity.this.runOnUiThread(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.Listener.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            Glide.with((FragmentActivity) CameraActivity.this).load(file2.getAbsolutePath()).into(CameraActivity.this.image_thumb);
                            CameraActivity.this.image_thumb.setImageURI(Uri.fromFile(file2));
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            CameraActivity.this.image_thumb.setImageURI(Uri.fromFile(file2));
                        }
                        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.CameraActivity.Listener.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                CameraActivity.this.image_thumb.startAnimation(AnimationUtils.loadAnimation(CameraActivity.this.getApplicationContext(), R.anim.fade_out));
                            }
                        }, 500L);
                    }
                });
                CameraActivity.this.arrayPhotoVideo.add(new ListModel(file2.getAbsolutePath()));
                try {
                    if (CameraActivity.this.isLocation) {
                        CameraActivity.this.getLocation(file2);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (CameraActivity.this.isOff) {
                    CameraActivity.this.isOff = false;
                    CameraActivity.this.camera.setFlash(Flash.ON);
                }
            } catch (FileNotFoundException e3) {
                Log.e("Exception", "" + e3);
            } catch (IOException e4) {
                Log.e("Exception", "" + e4);
            }
        }
    }
}
