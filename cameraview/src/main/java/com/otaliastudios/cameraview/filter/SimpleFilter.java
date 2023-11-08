package com.otaliastudios.cameraview.filter;

import androidx.annotation.NonNull;

public final class SimpleFilter extends BaseFilter {

    private final String fragmentShader;

    @SuppressWarnings("WeakerAccess")
    public SimpleFilter(@NonNull String fragmentShader) {
        this.fragmentShader = fragmentShader;
    }

    @NonNull
    @Override
    public String getFragmentShader() {
        return fragmentShader;
    }

    @NonNull
    @Override
    protected BaseFilter onCopy() {
        return new SimpleFilter(fragmentShader);
    }
}
