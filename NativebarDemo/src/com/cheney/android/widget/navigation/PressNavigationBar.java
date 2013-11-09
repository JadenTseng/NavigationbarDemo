package com.cheney.android.widget.navigation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheney.android.common.T;
import com.cheney.android.common.ViewSizeAndPosition;
import com.cheney.android.widget.MyLinearLayout;

public class PressNavigationBar extends MyLinearLayout {
    private static final String TAG = PressNavigationBar.class.getSimpleName();

    private List<Map<String, Object>> mList = new LinkedList();

    private int mSelectedChildViewPosition = 0;
    private PressNavigationBarListener mPressNavigationBarListener;

    public int getSelectedChildViewPosition() {
        return this.mSelectedChildViewPosition;
    }

    public void setSelectedChildViewPosition(int selectedChildViewPosition) {
        this.mSelectedChildViewPosition = selectedChildViewPosition;
    }

    public PressNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                case 0:
                    PressNavigationBar.this.mSelectedChildViewPosition = PressNavigationBar.this
                            .getTouchPosition(event);
                    Log.v(PressNavigationBar.this.TAG,
                            "selectedChildViewPosition="
                                    + PressNavigationBar.this.mSelectedChildViewPosition);
                    PressNavigationBar.this.refreshView();
                    break;
                case 2:
                case 1:
                }

                if (PressNavigationBar.this.mPressNavigationBarListener != null)
                    PressNavigationBar.this.mPressNavigationBarListener
                            .onNavigationBarClick(
                                    PressNavigationBar.this.mSelectedChildViewPosition,
                                    PressNavigationBar.this, event);
                return true;
            }
        });
    }

    public void addChild(List<Map<String, Object>> list) {
        this.mList = list;
        for (int i = 0; i < list.size(); ++i) {
            FrameLayout frameLayout = new FrameLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    -1, -1);
            params.weight = 1.0F;
            frameLayout.setLayoutParams(params);
            addView(frameLayout);

            ImageView imageView = new ImageView(getContext());
            FrameLayout.LayoutParams imageViewParams = new FrameLayout.LayoutParams(
                    -1, -1);
            imageView.setLayoutParams(imageViewParams);
            frameLayout.addView(imageView);

            TextView textView = new TextView(getContext());
            FrameLayout.LayoutParams textViewParams = new FrameLayout.LayoutParams(
                    -1, -1);
            textView.setGravity(17);
            textView.setLayoutParams(textViewParams);
            frameLayout.addView(textView);

            Map map = (Map) list.get(i);
            String text = (String) map.get("text");
            int textSize = ((Integer) map.get("textSize")).intValue();
            int textColor = ((Integer) map.get("textColor")).intValue();
            int image = ((Integer) map.get("image")).intValue();
            int imageSelected = ((Integer) map.get("imageSelected")).intValue();

            textView.setText(text);
            textView.setTextSize(textSize);
            textView.setTextColor(textColor);
            if (this.mSelectedChildViewPosition == i)
                imageView.setBackgroundResource(imageSelected);
            else
                imageView.setBackgroundResource(image);
        }
    }

    public void refreshView() {
        removeAllViews();
        addChild(this.mList);
    }

    private ViewSizeAndPosition getSelectedChildViewSizeAndPosition() {
        ViewSizeAndPosition viewSizeAndPosition = getViewSizeAndPosition();
        int selectdChildViewWidth = viewSizeAndPosition.getWidth()
                / this.mList.size();
        int selectedChildViewHeight = viewSizeAndPosition.getHeight();

        ViewSizeAndPosition selectedChildViewSizeAndPosition = new ViewSizeAndPosition();
        selectedChildViewSizeAndPosition.setWidth(selectdChildViewWidth);
        selectedChildViewSizeAndPosition.setHeight(selectedChildViewHeight);
        selectedChildViewSizeAndPosition.setLeft(selectdChildViewWidth
                * getSelectedChildViewPosition());
        selectedChildViewSizeAndPosition.setTop(0);
        selectedChildViewSizeAndPosition.setRight(selectdChildViewWidth
                * getSelectedChildViewPosition() + selectdChildViewWidth);
        selectedChildViewSizeAndPosition.setBottom(selectedChildViewHeight);

        return selectedChildViewSizeAndPosition;
    }

    private int getTouchPosition(MotionEvent event) {
        ViewSizeAndPosition selectedChildViewSizeAndPosition = getSelectedChildViewSizeAndPosition();
        Log.v(this.TAG, "event.getX()=" + event.getX() + ",getWidth()="
                + selectedChildViewSizeAndPosition.getWidth());
        return T.getInt(
                event.getX() / selectedChildViewSizeAndPosition.getWidth(),
                T.ABANDON);
    }

    public void setPressNavigationBarListener(
            PressNavigationBarListener pressNavigationBarListener) {
        this.mPressNavigationBarListener = pressNavigationBarListener;
    }

    public static abstract interface PressNavigationBarListener {
        public abstract void onNavigationBarClick(int paramInt, View paramView,
                MotionEvent paramMotionEvent);
    }
}
