package com.iphonecamera.allinone.cameraediting.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.iphonecamera.allinone.cameraediting.model.Media;
import com.iphonecamera.allinone.cameraediting.R;


public class SingleMediaFragment extends BaseMediaFragment {
    private SubsamplingScaleImageView ivImage;
    int screenHeight;
    int screenWidth;

    @Override 
    public void setListeners() {
    }

    public static SingleMediaFragment newInstance(Media media) {
        return (SingleMediaFragment) newInstance(new SingleMediaFragment(), media);
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.frag_single_media, viewGroup, false);
    }

    @Override 
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initViews(view);
        updateViews();
        setListeners();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
    }

    @Override 
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override 
    public void onDestroy() {
        this.ivImage.recycle();
        super.onDestroy();
    }

    @Override 
    public void updateViews() {
        if (this.media != null) {
            this.ivImage.setOrientation(BitmapUtils.getOrientation(this.media.getUri(), getContext()));
            this.ivImage.setImage(ImageSource.uri(this.media.getUri()));
        }
    }

    public void rotateImage(int i) {
        rotate(this.media.getUri().getPath());
    }

    @Override 
    public void initViews(View view) {
        this.ivImage = (SubsamplingScaleImageView) view.findViewById(R.id.ivImage);
    }

    public void rotate(String str) {
        ExifInterface exifInterface;
        Bitmap decodeFile = BitmapFactory.decodeFile(str);
        try {
            exifInterface = new ExifInterface(new File(str).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            exifInterface = null;
        }
        if (exifInterface != null) {
            exifInterface.getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
        }
        Bitmap rotateBitmap = rotateBitmap(decodeFile, 90);
        rotateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            rotateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            this.ivImage.setImage(ImageSource.uri(this.media.getUri()));
            galleryAddPic(this.media.getUri().getPath());
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate(i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void galleryAddPic(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        getActivity().sendBroadcast(intent);
    }
}
