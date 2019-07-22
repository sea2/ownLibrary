package com.sea.library.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyBoardUtil {

    /*
     * 隐藏软键盘
     */
    public static Boolean hideInputMethod(Activity context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && null != context.getCurrentFocus()) {
            return imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

    /**
     * 显示软键盘
     *
     * @param context
     * @param editTv
     */
    public static void showInputMethod(Activity context, EditText editTv) {
        InputMethodManager inputManager = (InputMethodManager) editTv.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editTv, 0);
    }


    /**
     * 动态显示软键盘
     *
     * @param edit 输入框
     */
    public static void showSoftInput(EditText edit, Activity activity) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        edit.findFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(edit, 0);
    }

}
