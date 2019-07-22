package com.sea.library.util;

import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEquals(String s1, String s2) {
        if (null == s1 && null == s2) {
            return true;
        }
        if (null != s1 && null != s2) {
            return s1.trim().equals(s2.trim());
        }
        return false;
    }


    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    /**
     * 判断字符串是不是空的方法，
     *
     */
    public static boolean isBlank(String param) {
        if (param == null || param.trim().equals("") || param.trim().equals("null"))
            return true;
        else
            return false;
    }

    public static boolean isNull(String param) {
        if (param == null || param.trim().equals(""))
            return true;
        else
            return false;
    }

    public static boolean isJson(String param) {
        boolean b = isBlank(param);
        if (!b) {
            if (param.startsWith("{") || param.startsWith("[")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public static String filterHtml(String content) {
        return content.replaceAll("</?[^<]+>", "").replaceAll("\\s*|\t|\r|\n", "");
    }

    public static boolean isBoolean(String param) {
        boolean b = isBlank(param);
        if (!b) {
            if (param.trim().equals("true") || param.trim().equals("false"))
                b = true;
            else
                b = false;
        }
        return b;
    }

    public static boolean checkMobile(String value) {
        // return value.matches("^[1][3,4,5,8]+\\d{9}");
        return checkNum(value) && value.length() == 11;
    }

    public static boolean checkNum(String value) {
        return value != null && value.matches("^[0-9]*$");
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input) || "[]".equals(input) || "{}".equals(input) || "NULL".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        if (isEmpty(input)) return "";
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 截取需要长度的String
     *
     * @return
     */
    public static String toGetStr(String inputStr, int intSize) {
        String returnStr = "";
        if (inputStr != null) {
            if (!isEmpty(inputStr)) {
                int strSize = inputStr.length();
                if (strSize > intSize) {
                    returnStr = inputStr.substring(0, intSize) + "..";
                } else
                    returnStr = inputStr;
            }

        }
        return returnStr;
    }

    public static boolean checkEmail(String email) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher("dffdfdf@qq.com");
        return matcher.matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    public static float toFloat(String str, float defValue) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    public static double toDouble(String str, int defValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        if (isEmpty(obj)) return 0;
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }


    //给TextView设置部分大小
    public static String getText(TextView tv) {
        if (null == tv) return "";
        else {
            if (null != tv.getText()) return tv.getText().toString();
            else return "";
        }
    }




    //前一个String是否包括后一个
    public static boolean isContains(String s1, String s2) {
        if (null == s1 && null == s2) {
            return true;
        }
        if (null != s1 && null != s2) {
            if (s1.trim().contains(s2.trim())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
