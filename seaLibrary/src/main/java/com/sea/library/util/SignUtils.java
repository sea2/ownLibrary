package com.sea.library.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * 服务器返回验签工具类
 *
 * @author xiaocaimi@xcm.com
 */
public class SignUtils {


    /**
     * 创建客户端（V4版本）参数的签名
     *
     * @param paramsTreeMap
     * @return
     * @author windy 2014-8-6 下午7:18:57
     */
    public static String createClientSignV4(
            TreeMap<String, Object> paramsTreeMap) {
        String sign = "";
        StringBuffer buffer = new StringBuffer();
        if (null != paramsTreeMap) {
            // 遍历添加所有传值
            Iterator<String> itKeys = paramsTreeMap.keySet().iterator();
            while (itKeys.hasNext()) {
                String key = itKeys.next();
                Object value = paramsTreeMap.get(key);
                if (null != value) {
                    if (value instanceof String) {
                        buffer.append(key).append("=");
                        buffer.append(value);
                    } else if (value instanceof Boolean) {
                        buffer.append(key).append("=");
                        buffer.append(value.toString());
                    } else if (value instanceof Integer) {
                        buffer.append(key).append("=");
                        buffer.append(String.valueOf(value));
                    } else if (value instanceof Double) {
                        buffer.append(key).append("=");
                        buffer.append(String.valueOf(value));
                    } else if (value instanceof Float) {
                        buffer.append(key).append("=");
                        buffer.append(String.valueOf(value));
                    } else if (value instanceof Long) {
                        buffer.append(key).append("=");
                        buffer.append(String.valueOf(value));
                    } else if (value instanceof Short) {
                        buffer.append(key).append("=");
                        buffer.append(String.valueOf(value));
                    }
                }
            }
            // 添加一级签名 keyxiaocaimi
            buffer.append("JIN_ZHU");
        }
        sign =ecodeByMD5(buffer.toString());
        // md5一级签名
        return sign;
    }



    private final static char hexDigits[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    /**
     * MD5加密摘要计算
     *
     * @param originstr
     * @return string
     */
    public static String ecodeByMD5(String originstr) {
        String result = null;
        if (originstr != null) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] source = originstr.getBytes("utf-8");
                md.update(source);
                byte[] tmp = md.digest();
                char[] str = new char[32];
                for (int i = 0, j = 0; i < 16; i++) {
                    byte b = tmp[i];
                    str[j++] = hexDigits[b >>> 4 & 0xf];
                    str[j++] = hexDigits[b & 0xf];
                }
                result = new String(str);// 结果转换成字符串用于返回
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }

        }
        return result;
    }


}
