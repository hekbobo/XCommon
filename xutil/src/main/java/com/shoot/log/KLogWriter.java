package com.shoot.common;



import java.io.PrintWriter;
import java.io.StringWriter;


/**
 *
 * 日志写入
 * 
 * @author singun
 * 
 */
public abstract class KLogWriter {
	
	private static KLogBuffer buffer = new KLogBuffer();
	private static KLogInput input = new KLogInput(buffer);
	private static KLogOutput output = new KLogOutput(buffer);
	private static ILogHelper mLogHelper;
	
	private static boolean sEnable = false;
	private static String[] sExtendedLogClasses = null;

	public static synchronized void enable(ILogHelper logHelper) {
	    if (logHelper == null) {
	        return;
	    }
	        mLogHelper = logHelper;
	        String[] extendedLogClasses = mLogHelper.getExtendedLogClass();
	        if (extendedLogClasses != null) {
	            sExtendedLogClasses = extendedLogClasses;
	        }
			if (mLogHelper.getContext() == null) {
				sEnable = false;
				// appliation必须先初始化
				return ;
			}
			KLogOutput.Output.NormalFileOutput.getInstance(logHelper).setFileDir(mLogHelper.getNormalOutputPath());
			sEnable = true;
			buffer.enable();
			input.enable();
			output.enable(logHelper);
	}

	public synchronized static void disable() {
			input.disable();
			output.disable();
			buffer.disable();
			sEnable = false;
	        mLogHelper = null;
	        sExtendedLogClasses = null;
	}

	private static void restartKLog() {
	    ILogHelper logHelper = mLogHelper;
	    if (logHelper != null) {
	        disable();
	        enable(logHelper);
	    }
	}
	

	/**
	 * 
	 * @param featureName 日志功能标识
	 * @param text  日志内容
	 */
	public static void debug(String featureName, String text) {
//		String module = getClassNameAndMethodName();
//		String message = joinMessage(featureName, text);
        debugReal(featureName , text);
	}

	/**
	 * 打印debug级别的日记
	 *
	 * @param tag
	 *            标记
	 * @param text
	 *            日志内容
	 */
	private static void debugReal(String tag, String text) {
		try {
			input.debug(tag, text);
		} catch (Exception e) {
			e.printStackTrace();
			restartKLog();
		}
	}

	/**
	 * 打印异常信息
	 *
	 * @param e
	 */
	public static void printStackTrace(String tag, Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		try {
			input.info(tag, sw.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
			restartKLog();
		}
	}
	
    // 网络异常添加上错误码
    public static void printStackTrace(String tag, Throwable e, int errorCode) {
        StringWriter sw = new StringWriter();
        sw.append(" errorCode = " + errorCode + " ; ");
        sw.append(" Exception info = ");
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        try {
            input.info(tag, sw.toString());
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
	
	/**
	 * 打印info级别的日记
	 * @param featureName  日志功能标识
	 * @param text  日志内容
	 */
	public static void info(String featureName, String text) {
		//String module = getClassNameAndMethodName();
		String message = joinMessage(featureName, text);
		infoReal("" , message);
    }

	/**
	 * 打印info级别的日记
	 *
	 * @param tag
	 *            标记
	 * @param text
	 *            日志内容
	 */
	private static void infoReal(String tag, String text) {
		try {
			input.info(tag, text);
		} catch (Exception e) {
			e.printStackTrace();
			restartKLog();
		}
	}

	private static void errorReal(String tag, String text) {
		try {
			input.error(tag, text);
		} catch (Throwable e) {
			e.printStackTrace();
			restartKLog();
		}
	}
	
	/**
	 * 
	 * @param featureName 日志功能标识
	 * @param text  日志内容
	 */
	public static void error(String featureName, String text) {
		//String module = getClassNameAndMethodName();
		String message = joinMessage(featureName, text);
		errorReal("", message);
	}
	
	private static final String KLOG_FILE_NAME = "KLogWrap.java";
	
	/**
	 * 得到调用方法的类和方法
	 * @return
	 */
    private static String getClassNameAndMethodName() {
        //android.os.Debug.waitForDebugger();
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        StringBuffer sb = new StringBuffer();
        boolean findKLog = false;
        
        for(StackTraceElement ste : stack) {
        	String fname = ste.getFileName();
            if (KLOG_FILE_NAME.equals(fname)) {
            	findKLog = true;
            } else if(findKLog) {
            	if(isExtendedLogClassesFileMatched(fname)) {
            		//针对扩展日志输出，追加判断
            		findKLog = true;
            	} else{
            		sb.append(ste.getClassName());
            		sb.append(".");
            		sb.append(ste.getMethodName());
            		sb.append("(");
            		sb.append(ste.getFileName());
            		sb.append(":");
            		sb.append(ste.getLineNumber());
            		sb.append(")");
            		break;
            	}
            }
        }
        return sb.toString();
    }
    
    private static boolean isExtendedLogClassesFileMatched(String fileName) {
        if (sExtendedLogClasses == null || fileName == null) {
            return false;
        }
        for (String logFile : sExtendedLogClasses) {
            if (fileName.equals(logFile)) {
                return true;
            }
        }
        return false;
    }
    
    private static String joinMessage(String featureName, String text) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("[");
    	if(null == featureName) {
    		sb.append("default");
    	} else {
    		sb.append(featureName);
    	}
    	sb.append("] ");
    	if(null != text){
    		sb.append(text);
    	}
    	return sb.toString();
    }
}
