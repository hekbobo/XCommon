package com.yingjie.addressselector.api;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.yingjie.addressselector.R;
import com.yingjie.addressselector.inner.SelectorFactory;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public class CYJAdSelector {

    int mSelectColor = R.color.ff5000;
    int mBottomLineColor = R.color.ff5000;
    int mSelectLevel = 3;
    int mBackgroundColor = R.color.white;
    int mNormalTextColor = R.color.v666666;
    int mTitleTextColor = R.color.v333333;
    int mDividerColor = R.color.veeeeee;

    public void showSelector(Context context, int mType, String province, String city, String area, OnSelectorListener onSelectorListener) {
        int selectColor = resolveColor(context, mSelectColor);
        int bottomLineColor = resolveColor(context, mBottomLineColor);
        int backgroundColor = resolveColor(context, mBackgroundColor);
        int normalTextColor = resolveColor(context, mNormalTextColor);
        int titleTextColor = resolveColor(context, mTitleTextColor);
        int dividerColor = resolveColor(context, mDividerColor);

        SelectorFactory.get()
                .setColor(selectColor)
                .setBottomLineColor(bottomLineColor)
                .setSelectLevel(mSelectLevel)
                .setBackgroundColor(backgroundColor)
                .setNormalTextColor(normalTextColor)
                .setTitleTextColor(titleTextColor)
                .setDividerColor(dividerColor)
                .create()
                .showSelector(context, mType, province, city, area, onSelectorListener);
    }

    public CYJAdSelector setSelectColor(@ColorRes int color) {
        mSelectColor = color;
        return this;
    }

    public CYJAdSelector setBottomLineColor(@ColorRes int color) {
        mBottomLineColor = color;
        return this;
    }

    public CYJAdSelector setSelectLevel(int level) {
        mSelectLevel = level;
        return this;
    }

    /**
     * 设置背景颜色
     * @param color 颜色资源，例如 R.color.white
     * @return CYJAdSelector 实例
     */
    public CYJAdSelector setBackgroundColor(@ColorRes int color) {
        mBackgroundColor = color;
        return this;
    }

    /**
     * 设置普通文字颜色
     * @param color 颜色资源
     * @return CYJAdSelector 实例
     */
    public CYJAdSelector setNormalTextColor(@ColorRes int color) {
        mNormalTextColor = color;
        return this;
    }

    /**
     * 设置标题文字颜色
     */
    public CYJAdSelector setTitleTextColor(@ColorRes int color) {
        mTitleTextColor = color;
        return this;
    }

    /**
     * 设置分隔线颜色
     */
    public CYJAdSelector setDividerColor(@ColorRes int color) {
        mDividerColor = color;
        return this;
    }

    public CYJAdSelector build() {
        return this;
    }

    private static int resolveColor(Context context, int colorResId) {
        if (colorResId == 0) {
            return Color.TRANSPARENT;
        }
        return ContextCompat.getColor(context, colorResId);
    }
}
