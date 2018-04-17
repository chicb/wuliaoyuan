package com.ccb.wuliaoyuan.utils;

import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/17.
 */
public class CommonUtils {
    private static final Charset charset = Charset.forName("UTF-8");

    public static boolean isNull(Object o){
        return null == o || "".equals(o.toString().trim());
    }

    public static boolean isNotNull(Object o){
        return !isNull(o);
    }

    /**
     * 返回string，null=>""
     * @param o
     * @return
     */
    public static String toString(Object o){
        return isNull(o) ? "" : o.toString();
    }

    /**
     * 生成UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * 生成GUID，不包含"-"
     * @return
     */
    public static String getGUID(){
        return getUUID().replace("-", "");
    }

    /**
     * n位随机数
     * @param n
     * @return
     */
    public static String randomNum(int n) {
        return String.valueOf(Math.random()).substring(2, 2 + n);
    }

    /**
     * 将java命名转换成数据库命名
     * 1、userNameDs   -> USER_NAME_DS
     * 2、USER_NAME_DS -> USER_NAME_DS
     * 3、NAME -> NAME
     * 4、name -> NAME
     * 5、NAMe -> NAME
     * @param name
     * @return
     */
    public static String makeDBName(String name) {
        if(isNull(name)){
            return null;
        }
        if (name.contains("_") || name.equals(name.toUpperCase()))
            return name.toUpperCase();
        char[] chars = name.toCharArray();
        if (chars[0] >= 'A' && chars[0] <='Z')
            return name.toUpperCase();
        String result = "";
        for (int i=0;i<chars.length;i++) {
            char c = chars[i];
            if(c>='A' && c<='Z'){
                result += "_"+c;
            }else{
                result += c;
            }
        }
        return result.toUpperCase();
    }

    public static boolean isEmpty(Object o) {
        return isNull(o);
    }
    /**
     * 将金额字符串 格式化 为相应2位小数的 字符串，不足2位补0
     *
     * */
    public static String formatCurrency(String  currency){
        return formatCurrency(currency,2);
    }
    /**
     * 将金额字符串 格式化 为相应precision位小数的 字符串，不足补0
     * 如果为null返回空白
     * */
    public static String formatCurrency(String currency,int precision){
        if(currency == null || "".equals(currency)){
            return "";
        }

        float f = 0.0f;
        if(currency!=null&&!currency.equals("")){
            f = Float.parseFloat(currency);
        }
        return String.format("%."+precision+"f",f);
    }
    public static String makeEOName(String key) {

        char[] chars = key.toLowerCase().toCharArray();
        String result = "";
        int i = 0;
        while (i < chars.length) {
            char c = chars[i];
            if (c == '_') {
                result += (chars[i + 1] + "").toUpperCase();
                i++;
            } else {
                result += c;
            }
            i++;
        }
        return result;
    }

    /**
     * 从链接地址得到参数集合
     *
     * @param url
     *            bds/project/prestoreproject.xhtml?projectType=01&isDetail=01
     * @return {projectType=01,isDetail=01}
     * @author xyg
     * @date 2013-2-20
     * @see
     */
    public static List<String> getParamFromUrl(String url) {
        if (StringUtils.isEmpty(url) || url.indexOf("?") < 0) {
            return null;
        }
        String[] str = url.split("\\?");
        if (str.length < 2) {
            return null;
        }
        String paramStr = str[1];
        // 得到参数字符串
        List<String> params = new ArrayList<String>();
        String[] paramArray = null;
        if (!StringUtils.isEmpty(paramStr) && paramStr.indexOf("&") > 0) {
            paramArray = paramStr.split("&");
            for (String param : paramArray) {
                if (param.indexOf("Id") < 0) {
                    // 排除动态的参数
                    params.add(param);
                }
            }
        } else {
            if (paramStr.indexOf("Id") < 0) {
                params.add(paramStr);
            }
        }
        return params;
    }

    /**
     * 从链接地址获得资源路径
     *
     * @param url
     *            bds/project/prestoreproject.xhtml?projectType=01
     * @return bds/project/prestoreproject.xhtml
     * @author xyg
     * @date 2013-2-20
     * @see
     */
    public static String getResouceAddrFromUrl(String url) {
        if (StringUtils.isEmpty(url) || url.indexOf("?") < 0) {
            return url;
        }
        return url.substring(0, url.indexOf("?"));
    }

    /**
     * 验证是否是手机号码
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneNumber(String phone) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 转换为Long
     *
     * @param str
     * @return
     */
    public static Long toLong(String str) {
        if (isEmpty(str))
            return null;
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 按字典替换字符串
     *
     * @param src
     *            源字符串
     * @param dictionary
     *            替换字典
     * @return
     * @date 2015年3月25日
     */
    public static String replace(String src, Map<String, String> dictionary) {
        if (isEmpty(src) || dictionary == null) {
            return src;
        }
        String dst = src;
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            dst = dst.replace(entry.getKey(), entry.getValue());
        }
        return dst;
    }

    /**
     * 转换4字节字符
     *
     * @param src
     * @return
     * @date 2015年3月25日
     */
    public static String convert4ByteChar(String src) {
        if (src == null) {
            return src;
        }
        Map<String, String> dictionary = new HashMap<String, String>();
        byte[] bytes = src.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            if ((b & 0xf8) == 0xf0) {
                String repSrc = new String(new byte[] { b, bytes[++i],
                        bytes[++i], bytes[++i] }, charset);
                if (dictionary.get(repSrc) == null) {
                    int code = Character.toCodePoint(repSrc.charAt(0),
                            repSrc.charAt(1));
                    dictionary.put(repSrc, String.format("\\u{%X}", code));
                }
            } else if ((b & 0xf0) == 0xe0) {
                i += 2;
            } else if ((b & 0xe0) == 0xc0) {
                i += 1;
            }
        }
        return replace(src, dictionary);
    }

    /**
     * 取消转换4字节字符
     *
     * @param src
     * @return
     * @date 2015年3月25日
     */
    public static String unconvert4ByteChar(String src) {
        if (src == null) {
            return src;
        }
        Map<String, String> dictionary = new HashMap<String, String>();
        Pattern patt = Pattern.compile("\\\\u\\{([0-9A-Fa-f]+)\\}");
        Matcher matcher = patt.matcher(src);
        while (matcher.find()) {
            String repSrc = matcher.group(0);
            if (dictionary.get(repSrc) == null) {
                int code = Integer.valueOf(matcher.group(1), 16);
                dictionary.put(repSrc, new String(Character.toChars(code)));
            }
        }
        return replace(src, dictionary);
    }

    /**
     * 字符串转义<br>
     *
     * @param str
     *            字符串
     * @param escapeChar
     *            需要转义的字符
     * @param prefix
     *            转义符
     * @return 转义结果
     * @see #escape(String, char[], char)
     */
    public static String escape(String str, char escapeChar, char prefix) {
        return escape(str, new char[] { escapeChar }, prefix);
    }

    /**
     * 字符串转义<br>
     * 注：不管需要转义的字符中是否包含转义符，转义符都将被转义
     *
     * @param str
     *            字符串
     * @param escapeChars
     *            需要转义的字符
     * @param prefix
     *            转义符（转义前缀）
     * @return 转义结果
     */
    public static String escape(String str, char[] escapeChars, char prefix) {
        if (str == null)
            return null;
        StringBuilder result = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (isEscapeChar(ch, escapeChars, prefix)) {
                result.append(prefix);
            }
            result.append(ch);
        }
        return result.toString();
    }

    private static boolean isEscapeChar(char ch, char[] escapeChars, char prefix) {
        if (ch == prefix)
            return true;
        boolean found = false;
        for (int i = 0; i < escapeChars.length; i++)
            if (ch == escapeChars[i]) {
                found = true;
                break;
            }
        return found;
    }

    /**
     * 转义JSON属性值字符串
     *
     * @param str
     *            字符串
     * @return 转义结果
     */
    public static String escapeJsonValue(String str) {
        if (str == null)
            return null;
        StringBuilder result = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '\"') {
                result.append("\\\"");
            } else if (ch == '\\') {
                result.append("\\\\");
            } else if (ch == '\n') {
                result.append("\\n");
            } else if (ch == '\r') {
                result.append("\\r");
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
