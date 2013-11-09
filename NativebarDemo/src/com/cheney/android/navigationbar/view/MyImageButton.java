package com.cheney.android.navigationbar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.cheney.android.navigationbar.common.CommonViewQuality;
import com.cheney.android.navigationbar.common.ViewSizeAndPosition;

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
