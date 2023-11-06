package plant.testtree.camerademo.activity.selectlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;

import plant.testtree.camerademo.R;
import plant.testtree.camerademo.activity.gallary.GalleryappActivity;
import plant.testtree.camerademo.model.Image;

/* loaded from: classes.dex */
public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.MyViewHolder> {
    Context context;
    public ArrayList<Image> moviesList;

    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout cv_item;
        public ImageView iv_img;
        public RelativeLayout rl_check;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyViewHolder(View view) {
            super(view);
          //  SelectImageAdapter.this = r1;
            this.iv_img = (ImageView) view.findViewById(R.id.iv_img);
            this.rl_check = (RelativeLayout) view.findViewById(R.id.rl_check);
            this.cv_item = (RelativeLayout) view.findViewById(R.id.cv_item);
        }
    }

    public SelectImageAdapter(ArrayList<Image> arrayList, Context context) {
        this.moviesList = arrayList;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_list_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        Glide.with(this.context).load(this.moviesList.get(i).path).into(myViewHolder.iv_img);
        if (this.moviesList.get(i).isCheck) {
            myViewHolder.cv_item.setPadding(10, 10, 10, 10);
            myViewHolder.rl_check.setVisibility(View.VISIBLE);
            this.moviesList.get(i).isCheck = true;
        } else {
            myViewHolder.rl_check.setVisibility(View.GONE);
            myViewHolder.cv_item.setPadding(0, 0, 0, 0);
            this.moviesList.get(i).isCheck = false;
        }
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.adapter.SelectImageAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                File file = new File(SelectImageAdapter.this.moviesList.get(i).path);
                Intent intent = new Intent(SelectImageAdapter.this.context, GalleryappActivity.class);
                intent.putExtra("path", file.getParentFile().getName());
                intent.putExtra("dir", file.getParentFile().toString());
                intent.putExtra("name", file.getName());
                SelectImageAdapter.this.context.startActivity(intent);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.moviesList.size();
    }
}
