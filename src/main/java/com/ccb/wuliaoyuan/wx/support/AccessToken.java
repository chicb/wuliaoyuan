package com.ccb.wuliaoyuan.wx.support;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ccb on 2018/4/13.
 */
@Data
public class AccessToken implements Serializable{
    private String appId;
    private String accessToken;
    private long touchTime;
    private int expiresIn;

    public boolean isExpired(){
        return System.currentTimeMillis()>(touchTime+expiresIn*1000);
    }
}
