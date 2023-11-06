package plant.testtree.camerademo.filter.xiuxiuxiu;

import android.content.Context;
import android.util.Log;

/* loaded from: classes.dex */
public class XiuXiuXiuFilterWrapper extends XiuXiuXiuAbsFilter {
    public XiuXiuXiuFilterWrapper(Context context, String str) {
        super(context, "filter/fsh/xiuxiuxiu/" + str.toLowerCase() + ".glsl", "filter/textures/xiuxiuxiu/" + str.toLowerCase() + ".idx", "filter/textures/xiuxiuxiu/" + str.toLowerCase() + ".dat");
        StringBuilder sb = new StringBuilder();
        sb.append("XiuXiuXiuFilterWrapper: ");
        sb.append(str);
        Log.d("AbsFilter", sb.toString());
    }
}