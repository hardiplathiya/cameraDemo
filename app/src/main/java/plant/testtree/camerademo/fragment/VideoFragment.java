package plant.testtree.camerademo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

import plant.testtree.camerademo.R;
import plant.testtree.camerademo.databinding.FragVideoBinding;
import plant.testtree.camerademo.helper.StorageHelper;
import plant.testtree.camerademo.model.Media;


public class VideoFragment extends BaseMediaFragment {
    FragVideoBinding binding;

    public static VideoFragment newInstance(Media media) {
        return (VideoFragment) BaseMediaFragment.newInstance(new VideoFragment(), media);
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        binding = FragVideoBinding.inflate(layoutInflater, viewGroup, false);
       return binding.getRoot();
    }

    @Override 
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        updateViews();
        setListeners();
    }

    @Override 
    public void updateViews() {
        Glide.with(requireActivity()).load(this.media.getUri()).apply((BaseRequestOptions<?>) new RequestOptions().signature(this.media.getSignature()).centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).into(binding.ivImage);
    }

    @Override
    public void setListeners() {
        binding.ivPlayIcon.setOnClickListener(view -> {
            Intent dataAndType = new Intent("android.intent.action.VIEW").setDataAndType(StorageHelper.getUriForFile(requireActivity(), media.getFile()), media.getMimeType());
            dataAndType.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(dataAndType);
        });
    }

    @Override
    public void initViews(View view) {
    }
}
