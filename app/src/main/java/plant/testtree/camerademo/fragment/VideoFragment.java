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
import plant.testtree.camerademo.helper.StorageHelper;
import plant.testtree.camerademo.util.Media;

/* loaded from: classes.dex */
public class VideoFragment extends BaseMediaFragment {
    private ImageView ivImage;
    private ImageView ivPlayIcon;

    public static VideoFragment newInstance(Media media) {
        return (VideoFragment) BaseMediaFragment.newInstance(new VideoFragment(), media);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.frag_video, viewGroup, false);
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
        Glide.with(getContext()).load(this.media.getUri()).apply((BaseRequestOptions<?>) new RequestOptions().signature(this.media.getSignature()).centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).into(this.ivImage);
    }

    @Override // com.cameraediter.iphone11pro.common.BaseFragment
    public void setListeners() {
        this.ivPlayIcon.setOnClickListener(new View.OnClickListener() { // from class: com.cameraediter.iphone11pro.fragment.VideoFragment.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoFragment.lambda$setListeners$0(VideoFragment.this, view);
            }
        });
    }

    public static void lambda$setListeners$0(VideoFragment videoFragment, View view) {
        Intent dataAndType = new Intent("android.intent.action.VIEW").setDataAndType(StorageHelper.getUriForFile(videoFragment.getContext(), videoFragment.media.getFile()), videoFragment.media.getMimeType());
        dataAndType.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        videoFragment.startActivity(dataAndType);
    }

    @Override // com.cameraediter.iphone11pro.common.BaseFragment
    public void initViews(View view) {
        this.ivPlayIcon = (ImageView) view.findViewById(R.id.ivPlayIcon);
        this.ivImage = (ImageView) view.findViewById(R.id.ivImage);
    }
}
