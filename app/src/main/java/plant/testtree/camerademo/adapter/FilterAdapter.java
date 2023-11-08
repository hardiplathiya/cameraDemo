package plant.testtree.camerademo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.otaliastudios.cameraview.filter.Filters;

import plant.testtree.camerademo.R;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder> {
    private Context context;
    private Filters[] filterTypeList;
    public OnFilterChangeListener onFilterChangeListener;
    int[] filteThumb = {R.drawable.a1, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19, R.drawable.a20, R.drawable.a21, R.drawable.a22};
    private int selected = 0;

   
    public interface OnFilterChangeListener {
        void onFilterChanged(int i);
    }

    public FilterAdapter(Context context, Filters[] filtersArr) {
        this.filterTypeList = filtersArr;
        this.context = context;
    }

    @Override
    public int getItemViewType(int i) {
        if (i == 1) {
            return -1;
        }
        return super.getItemViewType(i);
    }

    @Override 
    public FilterHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.effect_item_layout, viewGroup, false);
        FilterHolder filterHolder = new FilterHolder(inflate);
        filterHolder.thumbImage = (ImageView) inflate.findViewById(R.id.effect_thumb_image);
        filterHolder.filterRoot = (LinearLayout) inflate.findViewById(R.id.effect_root);
        filterHolder.filterImg = (FrameLayout) inflate.findViewById(R.id.effect_img_panel);
        return filterHolder;
    }

    @Override 
    public void onBindViewHolder(FilterHolder filterHolder, final int i) {
        Filters filters = this.filterTypeList[i];
        filterHolder.thumbImage.setImageResource(this.filteThumb[i]);
        filterHolder.filterRoot.setOnClickListener(view -> FilterAdapter.this.onFilterChangeListener.onFilterChanged(i));
    }

    @Override 
    public int getItemCount() {
        return this.filterTypeList == null ? 0 : 21;
    }

   
    public class FilterHolder extends RecyclerView.ViewHolder {
        FrameLayout filterImg;
        TextView filterName;
        LinearLayout filterRoot;
        ImageView thumbImage;

        public FilterHolder(View view) {
            super(view);
        }
    }

    public void setOnFilterChangeListener(OnFilterChangeListener onFilterChangeListener) {
        this.onFilterChangeListener = onFilterChangeListener;
    }
}
