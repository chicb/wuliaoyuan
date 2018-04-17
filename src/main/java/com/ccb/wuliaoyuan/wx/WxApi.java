package com.ccb.wuliaoyuan.wx;

import com.ccb.wuliaoyuan.utils.HttpUtils;


/**
 * Created by Administrator on 2018/4/17.
 */
public class WxApi {
    public static WxApi instance() {
        return instance;
    }

    private static final HttpUtils http = HttpUtils.instance();

    private static final WxApi instance = new WxApi();


}
