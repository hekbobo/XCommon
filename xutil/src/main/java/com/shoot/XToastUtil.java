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

    private static Toast mLongToast;
    private static Toast mLongUIToast;

    public static  void showShortToast(final String msg, final Context context){
        if (Looper.getMainLooper().getThread() != Thread.currentThread()){
            UiThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                   Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
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

    public static  void showLongToast(final String msg, final Context context){
        if (Looper.getMainLooper().getThread() != Thread.currentThread()){
            UiThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    public static  void showLongToast(final int id, final Context context ){
        if (Looper.getMainLooper().getThread() != Thread.currentThread()){
            UiThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, id, Toast.LENGTH_LONG).show();
                }
            });
        }else {
            Toast.makeText(context, id, Toast.LENGTH_LONG).show();
        }
    }

}
