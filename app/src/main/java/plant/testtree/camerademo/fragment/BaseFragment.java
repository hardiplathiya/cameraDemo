package plant.testtree.camerademo.fragment;

import android.view.View;
import androidx.fragment.app.Fragment;

import plant.testtree.camerademo.helper.MyApp;

/* loaded from: classes.dex */
public abstract class BaseFragment extends Fragment {
    protected MyApp myApp = MyApp.getInstance();

    public abstract void initViews(View view);

    public abstract void setListeners();

    public abstract void updateViews();

    public void onBackPressed() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    public void setResult(int i) {
        if (getActivity() != null) {
            getActivity().setResult(i);
        }
    }
}
