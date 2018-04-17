package com.ccb.wuliaoyuan.utils;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/17.
 */
public class URLUtils {
    public static String makeURL(String uri, Map<String, String> params){
        if(null == params || params.isEmpty())
            return uri;
        String [] keys = params.keySet().toArray(new String[0]);
        for(int i = 0; i < keys.length; i ++){
            String s = i == 0 ? "?" : "&";
            uri += s + keys[i] + "=" + params.get(keys[i]);
        }
        return uri;
    }
}
