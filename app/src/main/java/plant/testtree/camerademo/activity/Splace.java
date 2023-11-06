package plant.testtree.camerademo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import plant.testtree.camerademo.R;

/* loaded from: classes.dex */
public class Splace extends AppCompatActivity {
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splace);
        goNext();
    }

    public void goNext() {
        new Handler().postDelayed(new Runnable() { // from class: com.cameraediter.iphone11pro.Splace.1
            @Override // java.lang.Runnable
            public void run() {
                Splace.this.startActivity(new Intent(Splace.this, Activitystart.class));
            }
        }, 3000L);
    }
}
