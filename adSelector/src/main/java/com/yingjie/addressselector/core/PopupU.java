package com.yingjie.addressselector.core;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

import com.yingjie.addressselector.R;
import com.yingjie.addressselector.api.OnSelectorListener;

/**
 * Created by chen.yingjie on 2019/3/23
 */
public class PopupU {

    public static Dialog showRegionView(Context context,
                                        int mType,
                                        final String province,
                                        final String city,
                                        final String area,
                                        final OnSelectorListener onSelectorListener,
                                        @ColorInt int selectColor,
                                        @ColorInt int bottomLineColor,
                                        int level,
                                        @ColorInt int backgroundColor,
                                        @ColorInt int normalTextColor,
                                        @ColorInt int titleTextColor,
                                        @ColorInt int dividerColor) {
        final Dialog dialog = new Dialog(context, R.style.DialogCommonStyle);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(R.layout.layout_region);
        RegionPopupWindow regionPopupWindow = window.findViewById(R.id.regionPpw);
        regionPopupWindow.setHistory(mType, province, city, area);
        regionPopupWindow.setColor(selectColor);
        regionPopupWindow.setSelectLevel(level);
        regionPopupWindow.setBottomLineColor(bottomLineColor);
        regionPopupWindow.setBackgroundColor(backgroundColor);
        regionPopupWindow.setNormalTextColor(normalTextColor);
        regionPopupWindow.setTitleTextColor(titleTextColor);
        regionPopupWindow.setDividerColor(dividerColor);

        regionPopupWindow.setOnForkClickListener(new RegionPopupWindow.OnForkClickListener() {
            @Override
            public void onForkClick() {
                dialog.dismiss();
            }
        });
        regionPopupWindow.setOnRpwItemClickListener(new RegionPopupWindow.OnRpwItemClickListener() {
            @Override
            public void onRpwItemClick(String selectedProvince, String selectedCity, String selectedArea) {
                onSelectorListener.onSelector(selectedProvince, selectedCity, selectedArea);
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = PhoneU.getScreenPix(context).widthPixels;
        attributes.height = PhoneU.getScreenPix(context).heightPixels * 4 / 5;
        window.setBackgroundDrawable(new ColorDrawable(backgroundColor));
        window.setAttributes(attributes);
        window.setWindowAnimations(R.style.AnimBottom);
        dialog.show();
        return dialog;
    }
}
