package com.yingjie.addressselector.inner;

import android.content.Context;

import androidx.annotation.ColorInt;

import com.yingjie.addressselector.api.OnSelectorListener;
import com.yingjie.addressselector.core.PopupU;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public class SelectorImp implements ISelector {

    @ColorInt
    int mSelectColor;

    @ColorInt
    int mBottomLineColor;
    int mSelectLevel;
    int mBackgroundColor;
    int mNormalTextColor;
    int mTitleTextColor;
    int mDividerColor;

    @Override
    public void showSelector(Context context, int mType, String province, String city, String area, OnSelectorListener onRegionListener) {
        PopupU.showRegionView(context, mType, province, city, area, onRegionListener,
                mSelectColor, mBottomLineColor, mSelectLevel, mBackgroundColor,
                mNormalTextColor, mTitleTextColor, mDividerColor);
    }

    @Override
    public void setColor(int color) {
        mSelectColor = color;
    }

    @Override
    public void setBottomLineColor(int color) {
        mBottomLineColor = color;
    }

    @Override
    public void setSelectLevel(int level) {
        mSelectLevel = level;
    }

    @Override
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    @Override
    public void setNormalTextColor(int color) {
        mNormalTextColor = color;
    }

    @Override
    public void setTitleTextColor(int color) {
        mTitleTextColor = color;
    }

    @Override
    public void setDividerColor(int color) {
        mDividerColor = color;
    }
}
