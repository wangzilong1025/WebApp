package com.sandi.web.utils.security;

/**
 * Created by shixing on 2015/12/1.
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5
 * @author HAOMENG
 */
public class MD5{
    /**
     * MD5 加密  需要指定编码
     * @param plainText
     * @return
     */
    public static String md5s(String plainText,String CharSet) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes(CharSet));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
            //System.out.println("result: " + buf.toString());//
            //System.out.println("result: " + buf.toString().substring(8, 24));// 16
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
    /**
     * MD5 加密  需要指定编码
     * @param plainText
     * @return
     */
    public static byte[] md5sbyte(byte[] plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText);
            byte b[] = md.digest();
            return b;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5加密
     * @param plainText
     * @return
     */
    public static String md5s(String plainText) {
        return md5s(plainText,"GBK");
    }
}

