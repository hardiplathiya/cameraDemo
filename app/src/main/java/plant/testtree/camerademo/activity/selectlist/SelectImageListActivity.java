package plant.testtree.camerademo.activity.selectlist;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

import butterknife.ButterKnife;
import plant.testtree.camerademo.R;
import plant.testtree.camerademo.model.Image;

/* loaded from: classes.dex */
public class SelectImageListActivity extends AppCompatActivity {
    SelectImageAdapter adapter;
    ArrayList<Image> imgList = new ArrayList<>();
    ImageView ivBack;
    RecyclerView rv_listimage;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_select_image_list);
        ButterKnife.bind(this);
        ivBack = findViewById(R.id.ivBack);
        rv_listimage = findViewById(R.id.rv_listimage);
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageListActivity.this.onBackPressed();
            }
        });

        this.adapter = new SelectImageAdapter(this.imgList, this);
        this.rv_listimage.setLayoutManager(new GridLayoutManager(this, 3));
        this.rv_listimage.setItemAnimator(new DefaultItemAnimator());
        this.rv_listimage.setAdapter(this.adapter);
        addList();
    }

    public void addList() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getFilePaths());
        for (int i = 0; i < arrayList.size(); i++) {
            this.imgList.add(new Image((String) arrayList.get(i), false));
        }
    }

    public ArrayList<String> getFilePaths() {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_data"};
        TreeSet treeSet = new TreeSet();
        ArrayList<String> arrayList = new ArrayList<>();
        String[] strArr2 = null;
        Cursor managedQuery = uri != null ? managedQuery(uri, strArr, null, null, null) : null;
        if (managedQuery != null && managedQuery.moveToFirst()) {
            do {
                String string = managedQuery.getString(0);
                try {
                    treeSet.add(string.substring(0, string.lastIndexOf("/")));
                } catch (Exception unused) {
                }
            } while (managedQuery.moveToNext());
            strArr2 = new String[treeSet.size()];
            treeSet.toArray(strArr2);
        }
        for (int i = 0; i < treeSet.size(); i++) {
            File[] listFiles = new File(strArr2[i]).listFiles();
            if (listFiles != null) {
                int length = listFiles.length;
                int i2 = 0;
                while (i2 < length) {
                    File file = listFiles[i2];
                    try {
                        if (file.isDirectory()) {
                            file.listFiles();
                        }
                        if (file.getName().contains(".jpg") || file.getName().contains(".JPG") || file.getName().contains(".jpeg") || file.getName().contains(".JPEG") || file.getName().contains(".png") || file.getName().contains(".PNG") || file.getName().contains(".bmp") || file.getName().contains(".BMP")) {
                            arrayList.add(file.getAbsolutePath());
                        }
                        i2++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return arrayList;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }
}
