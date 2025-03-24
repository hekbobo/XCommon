package com.yingjie.addressselector.api;

import android.content.Context;
//import android.support.annotation.ColorInt;

import com.yingjie.addressselector.inner.SelectorFactory;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public class CYJAdSelector {

    public  void showSelector(Context context, int mType, String province, String city, String area, OnSelectorListener onSelectorListener) {
        SelectorFactory.get()
                .setColor(mSelectColor)
                .setBottomLineColor(mBottomLineColor)
                .create()
                .showSelector(context, mType, province, city, area, onSelectorListener);
    }
    int mSelectColor;
    int mBottomLineColor;

    public CYJAdSelector setSelectColor(int color) {
        mSelectColor = color;
        return this;
    }

    public CYJAdSelector setBottomLineColor(int color) {
        mBottomLineColor = color;
        return this;
    }

    public CYJAdSelector build(){
        return this;
    }
}
