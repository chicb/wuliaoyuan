package com.ccb.wuliaoyuan.config;

import com.ccb.wuliaoyuan.utils.SimpleCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ccb on 2018/4/13.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
    @Bean
    public SimpleCache simpleCache(){
        return new SimpleCache();
    }
}
