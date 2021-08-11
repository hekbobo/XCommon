package com.shoot.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


/**
 * Created by zyb on 2017/11/3.
 *
 */

public class XPackageUtil {

    public static PackageInfo getApkInfo(Context context, String path) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            return info;
        }
        return null;
    }

    public static String getVersionName(String pkg, Context context) {
        PackageInfo info = getPackageInfo(pkg, context);
        if (info != null) {
            return info.versionName;
        }
        return "0.0";
    }

    public static int getVersionCodeByPkg(String pkg, Context context) {
        PackageInfo info = getPackageInfo(pkg, context);
        if (info != null) {
            return info.versionCode;
        }
        return 0;
    }

    public static boolean isInstallApp(String pkg, Context context) {
        return getPackageInfo(pkg, context) != null;
    }

    private static PackageInfo getPackageInfo(String pkgName, Context context) {
        if (TextUtils.isEmpty(pkgName)) {
            return null;
        }

        try {
            return context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long getInstallTime(Context context, String pkg){
        try {
            PackageManager packageManager =context.getApplicationContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(pkg, 0);
            return packageInfo.firstInstallTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getMetaData(Context context, String pkg, String metaData){
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    pkg, PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(metaData);
        }catch (Throwable t){
            t.printStackTrace();
        }
        return "";

    }

}