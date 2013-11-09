package com.cheney.android.navigationbar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.cheney.android.navigationbar.common.CommonViewQuality;
import com.cheney.android.navigationbar.common.ViewSizeAndPosition;

public abstract class MyLinearLayout extends LinearLayout implements CommonViewQuality {
    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewSizeAndPosition getViewSizeAndPosition() {
        int width = getRight() - getLeft();
        int height = getBottom() - getTop();
        ViewSizeAndPosition vsp = new ViewSizeAndPosition();
        vsp.setWidth(width);
        vsp.setHeight(height);
        vsp.setLeft(getLeft());
        vsp.setTop(getTop());
        vsp.setRight(getRight());
        vsp.setBottom(getBottom());
        return vsp;
    }
}
