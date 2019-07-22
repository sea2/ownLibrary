package com.sea.library.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {

    private static String SDPATH;
    public static final String TAG = "FileUtils";

    public static String getSDPATH() {
        SDPATH = Environment.getExternalStorageDirectory().getPath() + "/";
        return SDPATH;
    }

    public static File creatSDDir(String strRootDir, String dir) {
        File dirFile = new File(strRootDir + dir + File.separator);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }

    public static boolean writeFileData(String fileName, String message) {
        try {
            FileUtils.delFile(fileName);
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getLocalFilePath(Context context, String subPathName, boolean isCache) {
        String result = "";
        try {
            result = FileDirectoryUtil.getOwnFileDirectory(context).getAbsolutePath();
            result = result + "/" + subPathName;
            File file = new File(result);
            if (!file.exists()) {
                file.mkdir();
            }
            result += "/";
        } catch (Exception ex) {
            result = "";
        }
        if (StringUtils.isEmpty(result)) result = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        Log.i(TAG, "文件路径:" + result);

        return result;
    }

    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        Runtime run = Runtime.getRuntime();
        BufferedInputStream in = null;
        BufferedReader inBr = null;
        String result = "";
        try {
            Process p = run.exec(cmd);
            in = new BufferedInputStream(p.getInputStream());
            inBr = new BufferedReader(new InputStreamReader(in));

            String lineStr;
            while ((lineStr = inBr.readLine()) != null) {
                Log.i(TAG, lineStr);
                if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray != null && strArray.length >= 5) {
                        result = strArray[1].replace("/.android_secure", "");
                        Log.d(TAG, "SD StorageDirectory:" + result);
                        break;
                    }
                }
                if (p.waitFor() != 0 && p.exitValue() == 1) {
                    // p.exitValue()==0 indicate exit normally, 1 indicate failed.
                    Log.e(TAG, "Command execute failed!");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            //return Environment.getExternalStorageDirectory().getPath();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inBr != null) {
                    inBr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String genRandFileName(String ext) {
        String filename = (new StringBuilder(String.valueOf(FileUtils.getTimeFileName(System.currentTimeMillis(), "yyMMddHHmmss")))).append(ext).toString();
        Log.d(TAG, "gen filename:" + filename);
        return filename;
    }

    public static String getTimeFileName(long time, String type) {
        String sDateTime;
        SimpleDateFormat sdf = new SimpleDateFormat(type, Locale.getDefault());
        Date dt = new Date(time);
        sDateTime = sdf.format(dt);
        return sDateTime;
    }

    public FileUtils() {
    }

    /**
     * @throws IOException
     */
    public static File creatSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    /**
     * @param dirName
     */
    public static File creatSDDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdirs();
        return dir;
    }

    /**
     */
    public static boolean fileIsExists(String filePath) {
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     */
    public static File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[4 * 1024];
            while ((input.read(buffer)) != -1) {
                output.write(buffer);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     */
    public static void delFile(String FilePath) {
        try {
            Log.i(TAG, "The TempFile(" + FilePath + ") was deleted.");
            File file = new File(FilePath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception ee) {

        }
    }

    /**
     */
    public static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        if (end.equals("m4a") || end.toLowerCase().trim().equals("mp3")
                || end.toLowerCase().equals("mid") || end.equals("xmf")
                || end.toLowerCase().equals("ogg") || end.equals("wav")) {
            type = "audio";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("gif")
                || end.equals("png") || end.equals("jpeg")
                || end.equals("bmp")) {
            type = "image";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
            type = "*";
        }

        if (end.equals("apk")) {
        } else {
            type += "/*";
        }
        return type;
    }


    public static void openFile(Context context, File file, String MimeType) {


        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if (MimeType == null || MimeType.length() == 0) {
            MimeType = FileUtils.getMIMEType(file);
        }
        intent.setDataAndType(Uri.fromFile(file), MimeType);
        context.startActivity(intent);
    }


    public static void openFile(Context context, String filePath, String MimeType) {

        if (context == null) {
            return;
        }

        File file = new File(filePath);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        if (MimeType == null || MimeType.length() == 0) {
            MimeType = FileUtils.getMIMEType(file);
        }
        intent.setDataAndType(Uri.fromFile(file), MimeType);
        context.startActivity(intent);
    }


    /**
     * @param path
     * @return
     */
    public static StatFs getStatFs(String path) {
        try {
            return new StatFs(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param stat
     * @return
     */

    @SuppressWarnings("deprecation")
    public static float calculateSize(StatFs stat) {
        if (stat != null)
            return stat.getAvailableBlocks()
                    * (stat.getBlockSize() / (1024f * 1024f));
        return 0.0f;
    }

    @SuppressWarnings("deprecation")
    public static float calculateSizeForG(StatFs stat) {
        if (stat != null)
            return stat.getAvailableBlocks()
                    * (stat.getBlockSize() / (1024f * 1024f * 1024f));
        return 0.0f;
    }


    @SuppressWarnings("deprecation")
    public static float allSizeForG(StatFs stat) {
        if (stat != null)
            return stat.getBlockCount()
                    * (stat.getBlockSize() / (1024f * 1024f * 1024f));
        return 0.0f;
    }

    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFileSha1(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[8192];
        int len;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            in = new FileInputStream(file);
            while ((len = in.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param file
     * @return
     */
    public static String getImgSuffix(File file) {
        String imgSuffix = "";
        if (file != null) {
            String fileName = file.getName();
            if (!StringUtils.isEmpty(fileName)) imgSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return imgSuffix;
    }

}