package com.sea.library.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SPUtil {
    private static final String SHARED_FILE = "com.gm88.client";
    private static SPUtil instance;

    public static SPUtil getInstance() {
        if (null == instance) {
            instance = new SPUtil();
        }
        return instance;
    }

    public boolean save(Context context,String key, String value) {
        boolean succ = false;
        Editor editor = getEditor(context);
        if (editor != null&& StringUtils.isNotEmpty(value)) {
            editor.putString(key, value).commit();
            succ = true;
        }
        return succ;
    }

    public String getValue(Context context,String key) {
        String value = "";
        SharedPreferences sharedPreferences = getSharedPreferences(context);

        if (sharedPreferences != null) {
            value = sharedPreferences.getString(key, "");
        }

        return value;
    }

    public boolean delete(Context context,String key) {
        boolean succ = false;
        Editor editor = getEditor(context);
        if (editor != null) {
            editor.remove(key).commit();
            succ = true;
        }
        return succ;
    }

    public boolean clear(Context context) {
        boolean succ = false;
        Editor editor = getEditor(context);
        if (editor != null) {
            editor.clear().commit();
            succ = true;
        }

        return succ;
    }

    private Editor getEditor(Context context) {
        Editor editor = null;
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        if (sharedPreferences != null) {
            editor = sharedPreferences.edit();
        }
        return editor;
    }

    private SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = null;
        sharedPreferences =context.getSharedPreferences(SHARED_FILE, Activity.MODE_PRIVATE);
        return sharedPreferences;
    }

}
