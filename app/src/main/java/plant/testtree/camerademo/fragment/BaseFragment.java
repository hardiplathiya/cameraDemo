package plant.testtree.camerademo.fragment;

import android.view.View;
import androidx.fragment.app.Fragment;

import plant.testtree.camerademo.helper.MyApp;


public abstract class BaseFragment extends Fragment {
    public abstract void initViews(View view);

    public abstract void setListeners();

    public abstract void updateViews();
}
