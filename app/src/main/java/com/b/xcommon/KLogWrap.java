package com.b.xcommon;

import android.text.TextUtils;

import com.shoot.common.log.ILogHelper;
import com.shoot.common.log.KLogOutput;
import com.shoot.common.log.KLogWriter;

public final class KLogWrap {

	public static void init(ILogHelper logHelper) {
	    KLogWriter.enable(logHelper);
	}

	public static void disable() {
	    KLogWriter.disable();
	}

	public static String getLogFileDir(ILogHelper logHelper) {
		return KLogOutput.Output.NormalFileOutput.getInstance(logHelper).getFileDir();
	}

    /**
     * 打印异常信息
     *
     * @param e
     */
    public static void printStackTrace(String tag, Throwable e) {
        KLogWriter.printStackTrace(tag, e);
    }
    
    // 网络异常添加上错误码
    public static void printStackTrace(String tag, Throwable e, int errorCode) {
        KLogWriter.printStackTrace(tag, e, errorCode);
    }

	/**
	 *  debug模式才会打印
	 * @param tag 日志功能标识
	 * @param text  日志内容
	 */
	public static void debug(String tag, String text) {
			KLogWriter.debug(tag, text);
	}
	
	/**
	 * 
	 * @param tag 日志功能标识
	 * @param text  日志内容
	 */
	public static void error(String tag, String text) {
		if (TextUtils.isEmpty(text)){
			return;
		}
//		Log.e(tag, text);
//		if (BuildConfig.DEBUG){ 先放开日志
			KLogWriter.error(tag, text);
//		}
	}
}
