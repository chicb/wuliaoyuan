package com.ccb.wuliaoyuan.wx.support;

import com.ccb.wuliaoyuan.exception.WeiXinException;

/**
 * Created by Administrator on 2018/4/17.
 */
public interface XMLableContent {
    String asXml() throws WeiXinException;
}
