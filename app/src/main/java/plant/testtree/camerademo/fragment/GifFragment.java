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
import plant.testtree.camerademo.databinding.FragGifBinding;
import plant.testtree.camerademo.model.Media;


public class GifFragment extends BaseMediaFragment {
    FragGifBinding binding;
    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        binding = FragGifBinding.inflate(layoutInflater,viewGroup,false);
       return binding.getRoot();
    }

    @Override 
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        updateViews();
        setListeners();
    }
    @Override
    public void setListeners() {
    }

    public static GifFragment newInstance(Media media) {
        return (GifFragment) BaseMediaFragment.newInstance(new GifFragment(), media);
    }
    @Override 
    public void updateViews() {
        Glide.with(requireActivity()).load(this.media.getUri()).diskCacheStrategy(DiskCacheStrategy.ALL).apply((BaseRequestOptions<?>) new RequestOptions().signature(this.media.getSignature()).format(DecodeFormat.PREFER_RGB_565).centerCrop().placeholder(R.color.color_back).diskCacheStrategy(DiskCacheStrategy.RESOURCE)).into(binding.ivImage);
    }

    @Override 
    public void initViews(View view) {
    }
}
