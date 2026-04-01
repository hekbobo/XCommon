package com.yingjie.addressselector.inner;

import android.content.Context;

import com.yingjie.addressselector.api.OnSelectorListener;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public interface ISelector {

    void showSelector(Context context, int mType, String province, String city, String area, OnSelectorListener onRegionListener);

    void setColor(int color);

    void setBottomLineColor(int color);

    void setSelectLevel(int level);

    void setBackgroundColor(int color);

    void setNormalTextColor(int color);

    void setTitleTextColor(int color);

    void setDividerColor(int color);
}
