package com.cheney.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.cheney.android.common.ViewSizeAndPosition;
import com.cheney.android.widget.impl.CommonViewQuality;

public class MyImageButton extends ImageButton implements CommonViewQuality {
    public MyImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageButton(Context context) {
        super(context);
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
