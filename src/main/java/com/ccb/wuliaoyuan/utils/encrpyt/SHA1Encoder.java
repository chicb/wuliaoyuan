package com.ccb.wuliaoyuan.utils.encrpyt;

import java.security.MessageDigest;

/**
 * Created by ccb on 2018/4/13.
 */
public class SHA1Encoder{
    private static final char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public SHA1Encoder(){
    }

    private static String getFormattedText(byte[] bytes){
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len*2);

        for(int j = 0; j<len; ++j){
            buf.append(HEX_DIGITS[bytes[j]>>4&15]);
            buf.append(HEX_DIGITS[bytes[j]&15]);
        }

        return buf.toString();
    }

    public static final String encode(String str){
        if(str == null){
            return null;
        }else{
            try{
                MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            }catch(Exception var2){
                throw new RuntimeException(var2);
            }
        }
    }
}
