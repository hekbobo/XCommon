package com.shoot.common;


import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.shoot.common.thread.UiThreadUtils;

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
                   Toast t= Toast.makeText(context, "", Toast.LENGTH_SHORT);
                   t.setText(msg);
                    t.show();
                }
            });
        }else {
            Toast t= Toast.makeText(context, "", Toast.LENGTH_SHORT);
            t.setText(msg);
            t.show();

        }
    }

    public static  void showShortToast(final int id, final Context context ){
        if (Looper.getMainLooper().getThread() != Thread.currentThread()){
            UiThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast t= Toast.makeText(context, "", Toast.LENGTH_SHORT);
                    t.setText(id);
                    t.show();

                }
            });
        }else {
            Toast t= Toast.makeText(context, id, Toast.LENGTH_SHORT);
            t.setText(id);
            t.show();


        }
    }

    public static  void showLongToast(final String msg, final Context context){
        if (Looper.getMainLooper().getThread() != Thread.currentThread()){
            UiThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast t=  Toast.makeText(context, "", Toast.LENGTH_LONG);
                    t.setText(msg);
                    t.show();

                }
            });
        }else {
            Toast t= Toast.makeText(context, "", Toast.LENGTH_LONG);
            t.setText(msg);
            t.show();

        }
    }

    public static  void showLongToast(final int id, final Context context ){
        if (Looper.getMainLooper().getThread() != Thread.currentThread()){
            UiThreadUtils.postOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast t=  Toast.makeText(context, "", Toast.LENGTH_LONG);
                    t.setText(id);
                    t.show();

                }
            });
        }else {
            Toast t= Toast.makeText(context, "", Toast.LENGTH_LONG);
            t.setText(id);
            t.show();

        }
    }

}
