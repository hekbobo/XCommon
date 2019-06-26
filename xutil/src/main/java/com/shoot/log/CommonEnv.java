package com.shoot.common;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class CommonEnv {

    private static boolean isDebug;
    private static Context g_context;

    public static void  setDebug(boolean v ){
        isDebug = v;
    }

    public static boolean isDebug(){
        return isDebug;
    }

    public static void attachApplication(Context v){
        g_context = v;
    }

    public static String getApplicationId(){
        if (g_context != null){
            return g_context.getApplicationInfo().packageName;
        }else {
         return "";
        }
    }

    public static String getExternalStorageDirectory() {
        String apkCacheDir;
        File fileDir =  g_context.getExternalFilesDir(null);
        if (fileDir == null){
            apkCacheDir = new File(Environment.getExternalStorageDirectory(),
                    getApplicationId()).getAbsolutePath();
        }else {
            apkCacheDir =  fileDir.getAbsolutePath();
        }
        apkCacheDir += File.separator;
        final File file = new File(apkCacheDir);
        if (!file.exists()) {
            boolean isOk = file.mkdirs();
        }
        return apkCacheDir;
    }
}
