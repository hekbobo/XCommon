package com.shoot.common.log;

import android.text.TextUtils;
import android.util.Log;

import com.shoot.common.CommonEnv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class KLogOutput {
    private KLogBuffer mBuffer;
    private static final ArrayList<Output> sOutputs = new ArrayList<Output>();
    private OutputThread outputThread = null;
    /**
     * 日期格式
     */
    private static SimpleDateFormat dateFormat = null;

    static {
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.CHINESE);
        } catch (Exception e) {
            e.printStackTrace();
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());
        } catch (Error e) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());
        }
    }

    public KLogOutput(KLogBuffer buffer) {
        mBuffer = buffer;
    }

    public void enable(ILogHelper logHelper) {
        synchronized (sOutputs) {
            addOutput(Output.NormalFileOutput.getInstance(logHelper));

            if (CommonEnv.isDebug())
                addOutput(Output.Logcat.INSTANCE);

            if (outputThread == null) {
                outputThread = new OutputThread();
            }

            try {
                if (outputThread.isAlive() == false) {
                    outputThread.start();
                }
            } catch (IllegalThreadStateException e) {
            }
        }
    }

    public void disable() {
        synchronized (sOutputs) {
            for (Output out : sOutputs) {
                out.close();
            }
            sOutputs.clear();

            if (outputThread != null) {
                outputThread.interrupt();
                outputThread = null;
            }
        }
    }



    private final int   MAX_BUF = CommonEnv.isDebug()? 1: 5;
    private final KLogBean logBean[] = new KLogBean[MAX_BUF];
    private int i = 0;

    public class OutputThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    KLogBean bean = mBuffer.take();
                    if (bean != null) {
                        logBean[i++] = bean;
                        bean = null;
                        if (i == MAX_BUF) {
                            output(logBean, MAX_BUF);
                            i = 0;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO 需要重启KLog
                    return;
                }
            }
        }
    }

    private void output(KLogBean[] bean, int nCount) {
        synchronized (sOutputs) {
            for (int j = 0; j < nCount; j++) {
                int level = bean[j].level;
                String module = bean[j].module;
                String msg = bean[j].msg;
                for (Output out : sOutputs) {
                    if (out.open()) {
                        out.out(level, module, msg);
                    }
                    out.close();
                }
            }
        }
    }

    public static String timeFormat(long time) {
        Date dateNow;
        if (time > 0) {
            dateNow = new Date(time);
        } else {
            dateNow = new Date();
        }

        String dateNowStr = dateFormat.format(dateNow);
        return dateNowStr;
    }

    private static boolean addOutput(Output output) {
        synchronized (sOutputs) {
            return sOutputs.add(output);
        }
    }

    private static boolean removeOutput(Output output) {
        if (output == null) {
            return false;
        }
        synchronized (sOutputs) {
            return sOutputs.remove(output);
        }
    }

    public static abstract class Output {

        private int mLevel;

        // 接口
        public void setLevel(int level) {
            mLevel = level;
        }

        public int getLevel() {
            return mLevel;
        }

        public abstract boolean open();

        public abstract void close();

        public abstract void out(int level, String module, String msg);

        protected static void formatOutput(int level, String module,
                                           String msg, OutputStream os) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(" [");
                if (level == KLogDef.LEVEL_ERROR) {
                    sb.append("ERROR");
                } else if (level == KLogDef.LEVEL_INFO) {
                    sb.append("INFO");
                } else {
                    sb.append("DEBUG");
                }
                sb.append(" ] ");
                sb.append(msg);
//                sb.append(" [ ");
//                sb.append(module);
//                sb.append(" ] ");
                sb.append("\r");

                byte[] bytes = sb.toString().getBytes(Charset.forName("UTF-8"));
                os.write(bytes);
                os.flush();
            } catch (Exception e) {
            }

        }

        // 具体实现类
        public static abstract class FileOutput extends Output {

            protected OutputStream mOut = null;

            private static final int FILE_MAX_SIZE = 512 * 1024;
            private static final int MAX_FILE_COUNT = 9;
            /**
             * Log写入文件的文件名
             */
            protected final String mFileNamePrefix;
            protected String mDir = null;

            private FileOutput(ILogHelper logHelper) {
                setLevel(KLogDef.LEVEL_INFO);

                String fileName = logHelper.getLogFileName();
                if (fileName == null || fileName.length() == 0) {
                    fileName = "android_log";
                }
                mFileNamePrefix = fileName + "_";
            }

            public String getFileDir() {
                return mDir;
            }

            public void setFileDir(String path) {
                mDir = path;
            }

            private File openFile() {
                if (getFileDir() == null) {
                    return null;
                }
                File dir = new File(getFileDir());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                if (dir.exists()) {
                    File find = null;
                    int i = 0;
                    for (i = 1; i <= MAX_FILE_COUNT; ++i) {
                        File file = new File(getFileDir(), mFileNamePrefix + i);
                        if (!file.exists()
                                || (file.exists() && file.length() < FILE_MAX_SIZE)) {
                            find = file;
                            break;
                        }
                    }
                    // 超出限制，删除文件，重命名文件
                    if (i > MAX_FILE_COUNT) {
                        for (i = 1; i <= MAX_FILE_COUNT; ++i) {
                            File file = new File(getFileDir(), mFileNamePrefix
                                    + i);
                            if (i == 1) {
                                file.delete();
                            } else {
                                file.renameTo(new File(getFileDir(),
                                        mFileNamePrefix + (i - 1)));
                            }
                        }
                        find = new File(getFileDir(), mFileNamePrefix
                                + MAX_FILE_COUNT);
                    }
                    return find;
                } else {
//					Log.e(TAG, "[open] make dir failed"); // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                }
                return null;
            }

            @Override
            public boolean open() {
                try {
                    File file = openFile();
                    if (file != null) {
                        if (mOut == null) {
                            mOut = new FileOutputStream(file, true);
                        }
                        return true;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void close() {
                if (mOut != null) {
                    try {
                        mOut.close();
                        mOut = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void out(int level, String module, String msg) {
                try {
                    if (mOut != null) {
                        formatOutput(level, module, msg, mOut);
                        mOut.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        public static class NormalFileOutput extends FileOutput {
            private static NormalFileOutput INSTANCE = null;
            /**
             * 保存文件路径
             */
            private static final String FILE_DIR = "/logs";

            public static synchronized NormalFileOutput getInstance(ILogHelper logHelper) {
                if (INSTANCE == null) {
                    INSTANCE = new NormalFileOutput(logHelper);
                }
                return INSTANCE;
            }

            private NormalFileOutput(ILogHelper logHelper) {
                super(logHelper);
            }

            @Override
            public String getFileDir() {
                if (mDir == null) {
                    String path = null;
                    File fileDir = new File(CommonEnv.getExternalStorageDirectory());
                    if (fileDir != null) {
                        path = fileDir.getAbsolutePath();
                    }
                    if (TextUtils.isEmpty(path)) {
                        path = "/data/data/" + CommonEnv.getApplicationId() + "/files";
                    }
                    mDir = path + FILE_DIR;
                }
                return mDir;
            }
        }

        public static class Logcat extends Output {

            public static final Logcat INSTANCE = new Logcat();

            private Logcat() {
                setLevel(KLogDef.LEVEL_INFO);
            }

            @Override
            public boolean open() {
                return true;
            }

            @Override
            public void close() {
            }

            @Override
            public void out(int level, String module, String msg) {
//                if (level >= getLevel()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(msg);
                    sb.append(" [ ");
                    sb.append(module);
                    sb.append(" ] ");
                    if (level >= KLogDef.LEVEL_ERROR) {
                        Log.e(".ERROR", sb.toString());
                    } else if (level >= KLogDef.LEVEL_INFO) {
                        Log.i(".INFO", sb.toString());
                    } else if (level >= KLogDef.LEVEL_DEBUG) {
                        Log.d(".DEBUG", sb.toString());
                    }else {
                        Log.e(".DEBUG", sb.toString());
                    }
//                }
            }
        }
    }
}
