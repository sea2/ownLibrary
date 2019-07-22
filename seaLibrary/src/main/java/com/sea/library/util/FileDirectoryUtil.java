package com.sea.library.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/2/22.
 */

public class FileDirectoryUtil {

    private static final String my_file_path = "JZClient";

    public static File getOwnFileDirectory(Context context, String fileDir) {

        File appFileDir = null;

        if ("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appFileDir = new File(Environment.getExternalStorageDirectory(), fileDir);
            if (!appFileDir.exists()) {
                appFileDir.mkdirs();
            }
        }

        if (appFileDir == null || !appFileDir.exists()) {
            if (Build.VERSION.SDK_INT >= 19) {
                appFileDir = new File(context.getExternalFilesDir(null), fileDir);
                if (!appFileDir.exists()) {
                    appFileDir.mkdirs();
                }
            } else {
                if (hasExternalStoragePermission(context)) {
                    appFileDir = new File(context.getExternalFilesDir(null), fileDir);
                    if (!appFileDir.exists()) {
                        appFileDir.mkdirs();
                    }
                }
            }
        }

        if (appFileDir == null || !appFileDir.exists()) {
            appFileDir = context.getFilesDir();
            if (!appFileDir.exists()) {
                appFileDir.mkdirs();
            }
        }

        return appFileDir;
    }

    public static File getOwnFileDirectory(Context context) {
        return getOwnFileDirectory(context, my_file_path);
    }


    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (Build.VERSION.SDK_INT >= 19) {
            appCacheDir = new File(context.getExternalCacheDir(), cacheDir);
            if (!appCacheDir.exists()) {
                appCacheDir.mkdirs();
            }
        } else {
            if (hasExternalStoragePermission(context)) {
                appCacheDir = new File(context.getExternalCacheDir(), cacheDir);
                if (!appCacheDir.exists()) {
                    appCacheDir.mkdirs();
                }
            }
        }

        if (appCacheDir == null || !appCacheDir.exists()) {
            appCacheDir = new File(context.getCacheDir(), cacheDir);
            if (!appCacheDir.exists()) {
                appCacheDir.mkdirs();
            }
        }
        return appCacheDir;
    }

    public static File getOwnCacheDirectory(Context context) {
        return getOwnCacheDirectory(context, my_file_path);
    }


    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }

}
