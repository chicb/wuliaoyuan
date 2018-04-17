package com.ccb.wuliaoyuan.exception;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created by Administrator on 2018/4/17.
 */
@Data
public class WeiXinException extends Exception {
    public static final int CODE_UNKNOWN = Integer.MIN_VALUE;
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;

    public WeiXinException() {
        this(CODE_UNKNOWN, null, null);
    }

    public WeiXinException(String message) {
        this(CODE_UNKNOWN, message, null);
    }

    public WeiXinException(Throwable cause) {
        super(cause);
        if (cause instanceof WeiXinException) {
            WeiXinException e = (WeiXinException) cause;
            this.code = e.getCode();
            this.msg = e.getMsg();
        } else {
            this.code = CODE_UNKNOWN;
            this.msg = cause.getLocalizedMessage();
        }
    }

    public WeiXinException(String message, Throwable cause) {
        this(CODE_UNKNOWN, message, cause);
    }

    public WeiXinException(int code, String message) {
        this(code, message, null);
    }

    private WeiXinException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
    }


    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", msg);
        return json.toString();
    }
}
