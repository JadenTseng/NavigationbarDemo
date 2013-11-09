package com.cheney.android.widget.navigation;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.cheney.android.common.T;
import com.cheney.android.common.ViewSizeAndPosition;
import com.cheney.android.widget.MyLinearLayout;
import com.cheney.android.widget.navigation.adapter.RollNavigationBarAdapter;

public class RollNavigationBar extends MyLinearLayout {
    private static final String TAG = RollNavigationBar.class.getSimpleName();
    private NavigationBarListener mListener;
    private RollNavigationBarAdapter mAdapter;
    private int mSelectedChildPosition = 0;

    private float mSelecterMoveContinueTime = 0.1F;

    private boolean mIsMove = false;
    private Rect mRect;
    private Paint mPaint;
    private BitmapDrawable mSelecter;
    private int mSelecterDrawableSource;
    Handler mUIHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RollNavigationBar.this.refreshView(RollNavigationBar.this.mAdapter);
        }
    };

    public int getSelectedChildPosition() {
        return this.mSelectedChildPosition;
    }

    public void setSelectedChildPosition(int selectedChildPosition) {
        this.mSelectedChildPosition = selectedChildPosition;
    }

    public float getSelecterMoveContinueTime() {
        return this.mSelecterMoveContinueTime;
    }

    public void setSelecterMoveContinueTime(float selecterMoveContinueTime) {
        if ((selecterMoveContinueTime >= 0.1D)
                && (selecterMoveContinueTime <= 1.0F))
            this.mSelecterMoveContinueTime = selecterMoveContinueTime;
    }

    public int getSelecterDrawableSource() {
        return this.mSelecterDrawableSource;
    }

    public void setSelecterDrawableSource(int selecterDrawableSource) {
        this.mSelecterDrawableSource = selecterDrawableSource;
    }

    public RollNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                case 0:
                    RollNavigationBar.this.mSelectedChildPosition = RollNavigationBar.this
                            .getChooseFieldPosition(v, event);
                    RollNavigationBar.this.moveSelecter();
                    break;
                case 2:
                case 1:
                }

                if (RollNavigationBar.this.mListener != null)
                    RollNavigationBar.this.mListener.onNavigationBarClick(
                            RollNavigationBar.this.mSelectedChildPosition,
                            RollNavigationBar.this, event);
                return true;
            }
        });
    }

    private int getChooseFieldPosition(View v, MotionEvent event) {
        ViewSizeAndPosition vsp = getViewSizeAndPosition();
        float singleChildViewWidth = vsp.getRight() / this.mAdapter.getCount();
        return T.getInt(event.getX() / singleChildViewWidth, T.ABANDON);
    }

    public void setAdapter(RollNavigationBarAdapter adapter) {
        this.mAdapter = adapter;
        LinearLayout linearLayout = null;
        for (int i = 0; i < this.mAdapter.getCount(); ++i) {
            linearLayout = new LinearLayout(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    -1, -1);
            params.weight = 1.0F;
            params.gravity = 17;
            Log.v(this.TAG, "paramsSize=" + params.width + "," + params.height);
            linearLayout.setLayoutParams(params);
            addView(linearLayout);
            this.mAdapter.getView(i, linearLayout, this);
        }
    }

    public void setNavigationBarListener(NavigationBarListener listener) {
        this.mListener = listener;
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void onDraw(Canvas canvas) {
        if (!(this.mIsMove)) {
            setSelecter();
        }

        super.onDraw(canvas);
        this.mSelecter.setBounds(this.mRect);
        this.mSelecter.draw(canvas);
    }

    private void setSelecter() {
        if ((this.mPaint == null) || (this.mRect == null)
                || (this.mSelecter == null)) {
            this.mPaint = new Paint();
            this.mRect = new Rect();
            this.mSelecter = new BitmapDrawable(getContext().getResources(),
                    BitmapFactory.decodeResource(getContext().getResources(),
                            getSelecterDrawableSource()));
        }

        ViewSizeAndPosition vsp = getViewSizeAndPosition();
        Log.v(this.TAG, "roll,ViewSizeAndPosition=" + vsp.getWidth() + ","
                + vsp.getHeight());
        int left = this.mSelectedChildPosition * vsp.getWidth()
                / this.mAdapter.getCount();
        int right = left + vsp.getWidth() / this.mAdapter.getCount();
        int top = 0;
        int bottom = vsp.getHeight();
        this.mRect.set(left, top, right, bottom);
    }

    private void moveSelecter()
    {
      new Thread()
      {
        public void run() {
          float s = 0.01F;
          RollNavigationBar.WillMoveInfo willMoveInfo = RollNavigationBar.this.getWillMoveInfo();
          int left = willMoveInfo.getStartViewSizeAndPosition().getLeft();
          int top = willMoveInfo.getStartViewSizeAndPosition().getTop();
          int right = willMoveInfo.getStartViewSizeAndPosition()
            .getRight();
          int bottom = willMoveInfo.getStartViewSizeAndPosition()
            .getBottom();
          float time = 0.0F;
          RollNavigationBar.this.mIsMove = true;
          while (RollNavigationBar.this.mIsMove) {
            time += s;
            left = (int)(left + willMoveInfo.getDv() * s);
            right = (int)(right + willMoveInfo.getDv() * s);
            RollNavigationBar.this.mRect.set(left, top, right, bottom);
            if (time >= RollNavigationBar.this.mSelecterMoveContinueTime) {
              RollNavigationBar.this.mIsMove = false;
              RollNavigationBar.this.mRect.set(willMoveInfo.getEndViewSizeAndPosition()
                .getLeft(), willMoveInfo
                .getEndViewSizeAndPosition().getTop(), 
                willMoveInfo.getEndViewSizeAndPosition()
                .getRight(), willMoveInfo
                .getEndViewSizeAndPosition()
                .getBottom());
              RollNavigationBar.this.mUIHandler.sendMessage(new Message());
            }
            RollNavigationBar.this.postInvalidate();
            try {
              Thread.sleep((long) (s * 1000.0F));
            } catch (InterruptedException e) {
              e.printStackTrace(); }
          }
        }
      }
      .start();
    }

    private WillMoveInfo getWillMoveInfo() {
        WillMoveInfo willMoveInfo = new WillMoveInfo();
        ViewSizeAndPosition startViewSizeAndPosition = new ViewSizeAndPosition();
        startViewSizeAndPosition.setLeft(this.mRect.left);
        startViewSizeAndPosition.setTop(this.mRect.top);
        startViewSizeAndPosition.setRight(this.mRect.right);
        startViewSizeAndPosition.setBottom(this.mRect.bottom);
        startViewSizeAndPosition.setWidth(this.mRect.right - this.mRect.left);
        startViewSizeAndPosition.setHeight(this.mRect.bottom - this.mRect.top);
        willMoveInfo.setStartViewSizeAndPosition(startViewSizeAndPosition);

        int endViewDistance = (this.mRect.right - this.mRect.left)
                * this.mSelectedChildPosition;

        ViewSizeAndPosition endViewSizeAndPosition = new ViewSizeAndPosition();
        endViewSizeAndPosition.setLeft(endViewDistance);
        endViewSizeAndPosition.setTop(this.mRect.top);
        endViewSizeAndPosition.setRight(this.mRect.right - this.mRect.left
                + endViewDistance);
        endViewSizeAndPosition.setBottom(this.mRect.bottom);
        endViewSizeAndPosition.setWidth(this.mRect.right - this.mRect.left);
        endViewSizeAndPosition.setHeight(this.mRect.bottom - this.mRect.top);
        willMoveInfo.setEndViewSizeAndPosition(endViewSizeAndPosition);

        int willMoveDistance = endViewDistance - this.mRect.left;
        int dv = (int) (willMoveDistance / getSelecterMoveContinueTime());
        willMoveInfo.setDv(dv);

        return willMoveInfo;
    }

    public void refreshView(RollNavigationBarAdapter adapter) {
        removeAllViews();
        setAdapter(adapter);
    }

    private ViewSizeAndPosition getChildViewSize() {
        ViewSizeAndPosition vsp = getViewSizeAndPosition();
        ViewSizeAndPosition childViewSize = new ViewSizeAndPosition();
        childViewSize.setWidth(vsp.getWidth() / this.mAdapter.getCount());
        childViewSize.setHeight(vsp.getHeight());
        return childViewSize;
    }

    public static abstract interface NavigationBarListener {
        public abstract void onNavigationBarClick(int paramInt, View paramView,
                MotionEvent paramMotionEvent);
    }

    class WillMoveInfo {
        private int dv;
        private ViewSizeAndPosition startViewSizeAndPosition;
        private ViewSizeAndPosition endViewSizeAndPosition;

        public int getDv() {
            return this.dv;
        }

        public void setDv(int dv) {
            this.dv = dv;
        }

        public ViewSizeAndPosition getStartViewSizeAndPosition() {
            return this.startViewSizeAndPosition;
        }

        public void setStartViewSizeAndPosition(
                ViewSizeAndPosition startViewSizeAndPosition) {
            this.startViewSizeAndPosition = startViewSizeAndPosition;
        }

        public ViewSizeAndPosition getEndViewSizeAndPosition() {
            return this.endViewSizeAndPosition;
        }

        public void setEndViewSizeAndPosition(
                ViewSizeAndPosition endViewSizeAndPosition) {
            this.endViewSizeAndPosition = endViewSizeAndPosition;
        }
    }
}