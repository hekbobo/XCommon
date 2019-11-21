package com.shoot;


import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.shoot.thread.UiThreadUtils;

/**
 *
 */
public class XToastUtil {
    private static final String TAG = "XToastUtil";

    private static Toast mToast;
    private static Toast mUIToast;

    public static  void showShortToast(final String msg, final Context context){
        if (Looper.getMainLooper().getThread() != Thread.currentThread()){
            UiThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null){
                        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    }
                    mToast.setText(msg);
                    mToast .show();
                }
            });
        }else {
            if (mUIToast == null){
                mUIToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            }
            mUIToast.setText(msg);
            mUIToast.show();
        }
    }

    public static  void showShortToast(final int id, final Context context ){
        if (Looper.getMainLooper().getThread() != Thread.currentThread()){
            UiThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
        }
    }

}
