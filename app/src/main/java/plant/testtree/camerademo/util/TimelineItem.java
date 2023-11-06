package plant.testtree.camerademo.util;

/* loaded from: classes.dex */
public interface TimelineItem {
    public static final int TYPE_HEADER = 101;
    public static final int TYPE_MEDIA = 102;

    /* loaded from: classes.dex */
    public @interface TimelineItemType {
    }

    int getTimelineType();
}
