package com.ccb.wuliaoyuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WuliaoyuanApplication extends SpringBootServletInitializer{

    public static void main(String[] args){
        SpringApplication.run(WuliaoyuanApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        // 注意的Application是启动类，就是main方法所属的类
        return builder.sources(WuliaoyuanApplication.class);
    }
}
