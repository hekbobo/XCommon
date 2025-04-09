package com.b.xcommon;

import static com.shoot.common.CommonEnv.getExternalStorageDirectory;

import android.content.Context;

import com.shoot.common.log.ILogHelper;

/**
 * 日志配置
 *
 */
public class KLockerLogHelper implements ILogHelper {
    private static KLockerLogHelper mInstance;

    private String mNormalDir = null;
    private String mDebugDir = null;
    private Context mContext;

    private KLockerLogHelper(Context context) {
        mContext =context;
    }

    public static synchronized KLockerLogHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new KLockerLogHelper(context);
        }
        return mInstance;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public String[] getExtendedLogClass() {
        return new String[] { "KLogWrap.java"};
    }

    @Override
    public String getNormalOutputPath() {
        if (mNormalDir == null) {
            mNormalDir = getExternalStorageDirectory() + "/logs";
        }
        return mNormalDir;
    }


    @Override
    public String getLogFileName() {
        return "other_log";
    }

}
