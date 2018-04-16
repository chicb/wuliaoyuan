package com.ccb.wuliaoyuan.utils.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ccb on 2018/4/13.
 */
@Component
public class CommonValue{
    @Getter
    private static String token;

    @Value("${wly.token}")
    private void setToken(String token){
        CommonValue.token = token;
    }
}
