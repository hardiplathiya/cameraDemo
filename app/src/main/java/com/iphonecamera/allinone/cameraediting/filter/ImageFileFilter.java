package com.iphonecamera.allinone.cameraediting.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;


public class ImageFileFilter implements FilenameFilter {
    private Pattern pattern;

    public ImageFileFilter(boolean z) {
        Pattern compile;
        if (z) {
            compile = Pattern.compile(".(jpg|png|gif|jpe|jpeg|bmp|webp|mp4|mkv|webm|avi)$", 2);
        } else {
            compile = Pattern.compile(".(jpg|png|gif|jpe|jpeg|bmp|webp)$", 2);
        }
        this.pattern = compile;
    }

    @Override // java.io.FilenameFilter
    public boolean accept(File file, String str) {
        return new File(file, str).isFile() && this.pattern.matcher(str).find();
    }
}
