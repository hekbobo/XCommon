package com.yingjie.addressselector.inner;

import android.content.Context;

import com.yingjie.addressselector.api.CYJAdSelector;
import com.yingjie.addressselector.api.OnSelectorListener;

/**
 * create by chenyingjie on 2020/6/10
 * desc
 */
public interface ISelector {

    void showSelector(Context context, int mType, String province, String city, String area, OnSelectorListener onRegionListener);

    public void setColor(int color);

    public void setBottomLineColor(int color);
    public void setSelectLevel(int level);

}
