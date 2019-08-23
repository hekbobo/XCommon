package com.shoot;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 创建时间： 2018/12/7
 * 编写人： qiangxu
 * 功能描述：键盘工具类
 **/

public class InputMethodUtils {

    //隐藏键盘
    public static void hideInputSoft(Context context,View view){
        if(context != null && view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //显示软键盘
    public static void showInputSoft(Context context, EditText editText){
        if(context != null && editText != null) {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null)
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    //软键盘是否在显示
    public static boolean isSoftShowing(Activity activity) {
        if(activity == null){
            return false;
        }
        int screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
        int threshold = screenHeight / 3;
        int rootViewBottom = activity.getWindow().getDecorView().getBottom();
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int visibleBottom=rect.bottom;
        int heightDiff = rootViewBottom - visibleBottom;
        return heightDiff > threshold;
    }


}
