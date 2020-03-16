package com.shoot;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class XDensityUtil {

	public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context){
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return displayMetrics.widthPixels;
	}

	public static int getScreenHeight(Context context){
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return displayMetrics.heightPixels;
	}

	//获取屏幕的实际高度包括虚拟按键
	public static int getScreenRealHeight(Context context){
		WindowManager windowManager =
				(WindowManager) context.getSystemService(Context.
						WINDOW_SERVICE);
		final Display display = windowManager.getDefaultDisplay();
		Point outPoint = new Point();
		if (Build.VERSION.SDK_INT >= 19) {
			// 可能有虚拟按键的情况
			display.getRealSize(outPoint);
		} else {
			// 不可能有虚拟按键
			display.getSize(outPoint);
		}
		return outPoint.y;
	}

}
