package com.shoot.io;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class XFileUtils {
    private XFileUtils() {
    }

    /**
     * @return 程序系统文件目录
     */
    public static String getFileDir(Context context) {
        return String.valueOf(context.getFilesDir());
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 程序系统文件目录绝对路径
     */
    public static String getFileDir(Context context, String customPath) {
        String path = context.getFilesDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

//****系统缓存目录**********************************************************************************************

    /**
     * @return 程序系统缓存目录
     */
    public static String getCacheDir(Context context) {
        return String.valueOf(context.getCacheDir());
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 程序系统缓存目录
     */
    public static String getCacheDir(Context context, String customPath) {
        String path = context.getCacheDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

//****Sdcard文件目录**********************************************************************************************

    /**
     * @return 内存卡文件目录
     */
    public static String getExternalFileDir(Context context) {
        return String.valueOf(context.getExternalFilesDir(""));
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 内存卡文件目录
     */
    public static String getExternalFileDir(Context context, String customPath) {
        String path = context.getExternalFilesDir("") + formatPath(customPath);
        mkdir(path);
        return path;
    }

//****Sdcard缓存目录**********************************************************************************************

    /**
     * @return 内存卡缓存目录
     */
    public static String getExternalCacheDir(Context context) {
        return String.valueOf(context.getExternalCacheDir());
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 内存卡缓存目录
     */
    public static String getExternalCacheDir(Context context, String customPath) {
        String path = context.getExternalCacheDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

//****公共文件夹**********************************************************************************************

    /**
     * @return 公共下载文件夹
     */
    public static String getPublicDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

//****相关工具**********************************************************************************************

    /**
     * 创建文件夹
     *
     * @param DirPath 文件夹路径
     */
    public static void mkdir(String DirPath) {
        File file = new File(DirPath);
        if (!(file.exists() && file.isDirectory())) {
            file.mkdirs();
        }
    }

    /**
     * 格式化文件路径
     * 示例：  传入 "sloop" "/sloop" "sloop/" "/sloop/"
     * 返回 "/sloop"
     */
    private static String formatPath(String path) {
        if (!path.startsWith("/"))
            path = "/" + path;
        while (path.endsWith("/"))
            path = new String(path.toCharArray(), 0, path.length() - 1);
        return path;
    }

    /**
     * @return 存储卡是否挂载(存在)
     */
    public static boolean isMountSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    public static String addSlash(String path) {
        if (TextUtils.isEmpty(path)) {
            return File.separator;
        }

        if (path.charAt(path.length() - 1) != File.separatorChar) {
            return path + File.separatorChar;
        }

        return path;
    }

    public static boolean isExists(String path){
        if (TextUtils.isEmpty(path)){
            return false;
        }
        return new File(path).exists();
    }

    /**
     * 文件读取
     * filePath 文件路径
     */
    public static String readFile(File filePath) {

        StringBuilder strResult = new StringBuilder();
        BufferedReader bufferedReader = null;
        if (!filePath.exists() || filePath.isDirectory()) {
            return "";
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String tempFileStr = "";

            while ((tempFileStr = bufferedReader.readLine()) != null) {
                strResult.append(tempFileStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            XIoUtils.closeSilently(bufferedReader);
        }
        return strResult.toString();
    }


    public static File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    public static boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    private static boolean isSpace(String s) {
        if (s == null) {
            return true;
        } else {
            int i = 0;

            for(int len = s.length(); i < len; ++i) {
                if (!Character.isWhitespace(s.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }


    public static boolean deleteAllInDir(String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    public static boolean deleteAllInDir(File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(File dir, FileFilter filter) {
        if (dir == null) {
            return false;
        } else if (!dir.exists()) {
            return true;
        } else if (!dir.isDirectory()) {
            return false;
        } else {
            File[] files = dir.listFiles();
            if (files != null && files.length != 0) {
                File[] var3 = files;
                int var4 = files.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    File file = var3[var5];
                    if (filter.accept(file)) {
                        if (file.isFile()) {
                            if (!file.delete()) {
                                return false;
                            }
                        } else if (file.isDirectory() && !deleteDir(file)) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        } else if (!dir.exists()) {
            return true;
        } else if (!dir.isDirectory()) {
            return false;
        } else {
            File[] files = dir.listFiles();
            if (files != null && files.length != 0) {
                File[] var2 = files;
                int var3 = files.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    File file = var2[var4];
                    if (file.isFile()) {
                        if (!file.delete()) {
                            return false;
                        }
                    } else if (file.isDirectory() && !deleteDir(file)) {
                        return false;
                    }
                }
            }

            return dir.delete();
        }
    }



    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
    public static String getSaveFilePath(Context context) {
        return context.getFilesDir() + File.separator+"pic.jpg";
//        File file = new File(context.getFilesDir(), "pic.jpg");
//        return file.getAbsolutePath();
    }

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流
        FileInputStream fis = new FileInputStream(filePath);
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];
        // 用于保存实际读取的字节数
        int hasRead = 0;
        while ( (hasRead = fis.read(bbuf)) > 0 ) {
            sb.append(new String(bbuf, 0, hasRead));
        }
        fis.close();
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }
}
