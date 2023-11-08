package plant.testtree.camerademo.fragment;

import android.content.Context;
import android.os.Bundle;

import plant.testtree.camerademo.model.Media;


public abstract class BaseMediaFragment extends BaseFragment {
    private static final String ARGS_MEDIA = "args_media";
    protected Media media;

    public static <T extends BaseMediaFragment> T newInstance(T t, Media media) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_MEDIA, media);
        t.setArguments(bundle);
        return t;
    }

    @Override 
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void fetchArgs() {
        if (getArguments() != null) {
            this.media = (Media) getArguments().getParcelable(ARGS_MEDIA);
            return;
        }
        throw new RuntimeException("Must pass arguments to Media Fragments!");
    }

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        fetchArgs();
    }
}
