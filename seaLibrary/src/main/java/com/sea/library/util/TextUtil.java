package com.sea.library.util;

import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本工具类
 *
 */
public class TextUtil {


  /**
   * 判断手机号格式
   *
   * @param phoneNumber 手机号码
   * @return true(正确的手机号13 14 15 17 18);false 非
   */
  public static boolean checkPhoneNumber(String phoneNumber) {
    Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
    Matcher m = p.matcher(phoneNumber);
    return m.matches();
  }


  /**
   * 文本转换,主要用于“非富文本”的换行处理，解决中文换行混乱问题
   *
   * @param oriString
   * @return
   */
  public static String formatText(String oriString) {
    oriString = stringFilter(oriString);
    return toDBC(oriString);
  }


  /**
   * 文本转换,主要用于“非富文本”的换行处理，解决中文换行混乱问题
   *
   * @param tv
   * @param oriString
   */
  public static void setFormatText(TextView tv, String oriString) {
    if (tv != null) {
      tv.setText(formatText(oriString));
    }
  }


  /**
   * 半角转换为全角
   *
   * @param input
   * @return
   */
  public static String toDBC(String input) {
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
   * * 去除特殊字符或将所有中文标号替换为英文标号
   *
   * @param str
   * @return
   */
  public static String stringFilter(String str) {
    str = str.replaceAll("【", "[").replaceAll("】", "]")
            .replaceAll("！", "!").replaceAll("：", ":").replaceAll("，", ",").replaceAll("。", ".");// 替换中文标号
    String regEx = "[『』]"; // 清除掉特殊字符
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(str);
    return m.replaceAll("").trim();
  }

  public static String urlDecoderReplacePst(String s){
    try {
      return URLDecoder.decode( s.replaceAll("%(?![0-9a-fA-F]{2})", "%25") ,"UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static String formatAdd(String added_rate) {
    return added_rate;
  }
}
