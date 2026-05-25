package com.example.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密工具类
 * 用于密码加密、字符串摘要生成
 */
public class MD5Util {

    /**
     * MD5 加密（32位小写）
     * @param str 要加密的字符串
     * @return 32位小写MD5
     */
    public static String md5(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = str.getBytes();
            byte[] md5Bytes = md5.digest(byteArray);

            StringBuilder sb = new StringBuilder();
            for (byte b : md5Bytes) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * MD5 加密（32位大写）
     * @param str 要加密的字符串
     * @return 32位大写MD5
     */
    public static String md5UpperCase(String str) {
        return md5(str).toUpperCase();
    }
}