package com.ccb.wuliaoyuan.utils.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by ccb on 2018/2/7.
 */
@Component
@ConfigurationProperties(prefix = "wx.mp.api")
@PropertySource("classpath:wx_api.properties")
@Data
public class WxCommonUrl{
    private String accessToken;
    private String connectOauth2Authorize;
    private String userInfo;
    private String snsOauth2Accesstoken;
    private String ticketGetticketJsapi;

}
