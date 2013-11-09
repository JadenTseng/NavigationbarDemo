package com.cheney.android.navigationbar.adapter;

import android.view.View;
import android.view.ViewGroup;

public abstract interface Adapter {

    public abstract int getCount();

    public abstract View getView(int paramInt, View paramView, ViewGroup paramViewGroup);
}
