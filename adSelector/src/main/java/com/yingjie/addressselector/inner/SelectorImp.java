package com.yingjie.addressselector.inner;

import android.content.Context;
import android.support.annotation.ColorInt;

import com.yingjie.addressselector.R;
import com.yingjie.addressselector.api.OnSelectorListener;
import com.yingjie.addressselector.core.PopupU;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public class SelectorImp implements ISelector{

    @ColorInt
    int mSelectColor;

    @ColorInt
    int mBottomLineColor;
    @Override
    public void showSelector(Context context, int mType, String province, String city, String area, OnSelectorListener onRegionListener) {
        PopupU.showRegionView(context, mType, province, city, area, onRegionListener, mSelectColor, mBottomLineColor);
    }

    @Override
    public void setColor(int color) {
        mSelectColor = color;
    }

    @Override
    public void setBottomLineColor(int color) {
        mBottomLineColor = color;
    }

}
