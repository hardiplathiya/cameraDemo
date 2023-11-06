package plant.testtree.camerademo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

import plant.testtree.camerademo.R;
import plant.testtree.camerademo.util.Media;

/* loaded from: classes.dex */
public class GifFragment extends BaseMediaFragment {
    private ImageView ivImage;

    @Override // com.cameraediter.iphone11pro.common.BaseFragment
    public void setListeners() {
    }

    public static GifFragment newInstance(Media media) {
        return (GifFragment) BaseMediaFragment.newInstance(new GifFragment(), media);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.frag_gif, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initViews(view);
        updateViews();
        setListeners();
    }

    @Override // com.cameraediter.iphone11pro.common.BaseFragment
    public void updateViews() {
        Glide.with(getActivity()).load(this.media.getUri()).diskCacheStrategy(DiskCacheStrategy.ALL).apply((BaseRequestOptions<?>) new RequestOptions().signature(this.media.getSignature()).format(DecodeFormat.PREFER_RGB_565).centerCrop().placeholder(R.color.color_back).diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(this.ivImage);
    }

    @Override // com.cameraediter.iphone11pro.common.BaseFragment
    public void initViews(View view) {
        this.ivImage = (ImageView) view.findViewById(R.id.ivImage);
    }
}
