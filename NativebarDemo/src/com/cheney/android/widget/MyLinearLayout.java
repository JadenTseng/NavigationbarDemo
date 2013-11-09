package com.cheney.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.cheney.android.common.ViewSizeAndPosition;
import com.cheney.android.widget.impl.CommonViewQuality;

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
