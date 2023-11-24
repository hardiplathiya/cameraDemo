package com.iphonecamera.allinone.cameraediting.util;


public interface TimelineItem {
    public static final int TYPE_HEADER = 101;
    public static final int TYPE_MEDIA = 102;
    public @interface TimelineItemType {
    }
    int getTimelineType();
}
