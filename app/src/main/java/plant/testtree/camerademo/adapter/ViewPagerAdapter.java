package plant.testtree.camerademo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

import plant.testtree.camerademo.R;
import plant.testtree.camerademo.util.Media;

/* loaded from: classes.dex */
public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Media> mediaItems;

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public ViewPagerAdapter(Context context, List<Media> list) {
        this.mediaItems = new ArrayList();
        this.context = context;
        this.mediaItems = list;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mediaItems.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_layout, (ViewGroup) null);
        Glide.with(this.context).load(this.mediaItems.get(i).getPath()).into((ImageView) inflate.findViewById(R.id.imageView));
        ((ViewPager) viewGroup).addView(inflate, 0);
        return inflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        ((ViewPager) viewGroup).removeView((View) obj);
    }
}
