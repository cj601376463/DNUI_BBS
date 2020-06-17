package com.DNUI.utils;

import java.security.MessageDigest;

public class MD5Util {
    public static String MD5(String inStr){
        MessageDigest md5 = null;//为应用程序提供信息摘要算法的功能
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArrays = inStr.toCharArray();
        byte[] byteArrays = new byte[charArrays.length];

        for (int i = 0; i<charArrays.length; i++){
            byteArrays[i] = (byte)charArrays[i];
        }
        byte[] md5Bytes = md5.digest(byteArrays);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i<md5Bytes.length; i++){
            int val = ((int)md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    // 可逆的加密算法
    public static String KL(String inStr) {
        // String s = new String(inStr);
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        return new String(a);
    }

    // 加密后解密
    public static String JM(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        return new String(a);
    }
}
