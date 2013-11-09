package com.cheney.android.common;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

public class ComponentTool {

    public static Point getViewCenterPoint(View v) {
        int mL = v.getLeft();
        int mT = v.getTop();
        int mR = v.getRight();
        int mB = v.getBottom();
        int cX = mL + (mR - mL) / 2;
        int cY = mT + (mB - mT) / 2;
        Point cP = new Point(cX, cY);
        return cP;
    }

    public static int dipToPx(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

    public static int pxToDip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }
}
