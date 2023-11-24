package com.iphonecamera.allinone.cameraediting.fragment;

import android.view.View;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {
    public abstract void initViews(View view);

    public abstract void setListeners();

    public abstract void updateViews();
}
