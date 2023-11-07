package plant.testtree.camerademo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import plant.testtree.camerademo.R;
import plant.testtree.camerademo.activity.gallary.GalleryappActivity;
import plant.testtree.camerademo.activity.selectlist.SelectImageListActivity;

public class Activitystart extends AppCompatActivity implements View.OnClickListener {

    public static String getPath(Context context, Uri uri) {
        String[] strArr = {"_data"};
        Cursor query = context.getContentResolver().query(uri, strArr, null, null, null);
        String r9 = null;
        if (query != null) {
            r9 = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow(strArr[0])) : null;
            query.close();
        }
        return r9 == null ? "Not found" : r9;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mainhome);
        ((ImageView) findViewById(R.id.btnSong)).setOnClickListener(view -> Activitystart.this.startActivity(new Intent(Activitystart.this, CameraActivity.class)));
        ((ImageView) findViewById(R.id.gallery2)).setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= 23) {
                Activitystart.this.checkPermission();
            }
        });

        ((ImageView) findViewById(R.id.btnrate2)).setOnClickListener(view -> {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + Activitystart.this.getPackageName()));
            Activitystart.this.startActivity(intent);
        });
        findViewById(R.id.moreapp).setOnClickListener(this);
        findViewById(R.id.cardShare).setOnClickListener(this);
       // loadintertialads.getInstance().nativeFbAd(this, (FrameLayout) findViewById(R.id.frameLayout), ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            File file = new File(getPath(this, intent.getData()));
            Intent intent2 = new Intent(this, GalleryappActivity.class);
            intent2.putExtra("path", file.getParentFile().getName());
            intent2.putExtra("dir", file.getParentFile().toString());
            intent2.putExtra("expired_event_name", file.getName());
            startActivity(intent2);
        }
    }

    public void checkPermission() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") + ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES")  + ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            startActivity(new Intent(this, SelectImageListActivity.class));
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.RECORD_AUDIO") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_MEDIA_IMAGES") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_COARSE_LOCATION")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Camera, Read External, and Write External Storage permissions are required to do the task.");
            builder.setTitle("Please grant those permissions");
                // android.content.DialogInterface.OnClickListener
                builder.setPositiveButton("OK", (dialogInterface, i) -> ActivityCompat.requestPermissions(Activitystart.this, new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_MEDIA_IMAGES", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123));
            builder.setNeutralButton("Cancel", null);
            builder.create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_MEDIA_IMAGES", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
        }
        }else {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") + ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES")  + ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") + ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                startActivity(new Intent(this, SelectImageListActivity.class));
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.RECORD_AUDIO") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_COARSE_LOCATION")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Camera, Read External, and Write External Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                // android.content.DialogInterface.OnClickListener
                builder.setPositiveButton("OK", (dialogInterface, i) ->
                {
                    ActivityCompat.requestPermissions(Activitystart.this, new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
                });
                builder.setNeutralButton("Cancel", null);
                builder.create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 123);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 123) {
            if (iArr.length <= 0 || iArr[0] + iArr[1] + iArr[2] != 0) {
                Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show();
            } else {
                Activitystart activitystart = Activitystart.this;
                activitystart.startActivity(new Intent(activitystart, SelectImageListActivity.class));
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.cardShare) {
            if (id != R.id.moreapp) {
                return;
            }
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Smart+Camera+Apps")));
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", "Watch and Download Latest Status sorry and many more using this app.\n\nTry now - https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.cameraediter.iphone11pro.Activitystart.7
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Activitystart.this.finishAffinity();
            }
        }).setNegativeButton("No", null).show();
    }
}
