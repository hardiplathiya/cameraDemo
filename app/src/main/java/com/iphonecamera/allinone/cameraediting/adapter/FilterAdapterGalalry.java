package com.iphonecamera.allinone.cameraediting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.iphonecamera.allinone.cameraediting.activity.EditImageActivity;
import com.iphonecamera.allinone.cameraediting.util.FilterResourceHelper;
import com.iphonecamera.allinone.cameraediting.util.GLRootView;
import com.iphonecamera.allinone.cameraediting.R;


public class FilterAdapterGalalry extends RecyclerView.Adapter<FilterAdapterGalalry.FilterHolder> {
    private Context context;
    public List<FilterType> filterTypeList;
    String image;
    public OnFilterChangeListener onFilterChangeListener;
    public int selected = 0;

   
    public interface OnFilterChangeListener {
        void onFilterChanged(FilterType filterType);
    }

   
    public class FilterHolder extends RecyclerView.ViewHolder {
        GLRootView camera_view;
        FrameLayout filterImg;
        TextView filterName;
        LinearLayout filterRoot;
        ImageView thumbImage;

        public FilterHolder(View view) {
            super(view);
        }
    }

    public FilterAdapterGalalry(Context context, List<FilterType> list, String str) {
        this.filterTypeList = list;
        this.context = context;
        this.image = str;
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
        if (i == -1) {
            return new FilterHolder(LayoutInflater.from(this.context).inflate(R.layout.filter_division_layout, viewGroup, false));
        }
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.filter_item_layout1, viewGroup, false);
        FilterHolder filterHolder = new FilterHolder(inflate);
        filterHolder.thumbImage = (ImageView) inflate.findViewById(R.id.filter_thumb_image);
        filterHolder.filterName = (TextView) inflate.findViewById(R.id.filter_thumb_name);
        filterHolder.filterRoot = (LinearLayout) inflate.findViewById(R.id.filter_root);
        filterHolder.filterImg = (FrameLayout) inflate.findViewById(R.id.filter_img_panel);
        return filterHolder;
    }

    @Override 
    public void onBindViewHolder(FilterHolder filterHolder, final int i) {
        if (i != 1) {
            FilterType filterType = this.filterTypeList.get(i);
            filterHolder.thumbImage.setImageBitmap(FilterResourceHelper.getFilterThumbFromFile(this.context, filterType));
            filterHolder.filterName.setText(FilterResourceHelper.getSimpleName(filterType));
            if (i == this.selected) {
                filterHolder.filterImg.setBackgroundResource(R.drawable.effect_item_selected_bg);
                filterHolder.filterName.setTextColor(this.context.getResources().getColor(R.color.color_phone));
            } else {
                filterHolder.filterImg.setBackgroundResource(0);
                filterHolder.filterName.setTextColor(-1);
            }

            filterHolder.filterRoot.setOnClickListener(view -> {
                if (FilterAdapterGalalry.this.selected != i) {
                    int i2 = FilterAdapterGalalry.this.selected;
                    int i3 = i;
                    EditImageActivity.filterPosition = i3;
                    FilterAdapterGalalry filterAdapter = FilterAdapterGalalry.this;
                    filterAdapter.selected = i3;
                    filterAdapter.notifyItemChanged(i2);
                    FilterAdapterGalalry.this.notifyItemChanged(i);
                    if (FilterAdapterGalalry.this.onFilterChangeListener != null) {
                        FilterAdapterGalalry.this.onFilterChangeListener.onFilterChanged(FilterAdapterGalalry.this.filterTypeList.get(i));
                    }
                }
            });
        }
    }

    @Override 
    public int getItemCount() {
        List<FilterType> list = this.filterTypeList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void setOnFilterChangeListener(OnFilterChangeListener onFilterChangeListener) {
        this.onFilterChangeListener = onFilterChangeListener;
    }
}
