package com.ccb.wuliaoyuan.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2018/4/17.
 */
@Slf4j
public class SpringUtil implements ApplicationContextAware, HandlerInterceptor {
    private static ApplicationContext applicationContext;
    private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<>();

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;

    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    private static ApplicationContext getApplicationContext() {
        if (null == applicationContext)
            log.error(">>>>>>>>>>>>>applicationContext is null in getApplicationContext");
        return applicationContext;
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApplicationContext().getBean(clazz);
        } catch (Exception e) {
            log.error("SpringUtils.getBean({}) error: {}", clazz, e.getMessage());
            throw e;
        }
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }


    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = requestLocal.get();
        if (null == request)
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (null == request)
            log.error(">>>>>>>>>>>>>>request is null in getRequest!!!");
        return request;
    }

    public static void setRequest(HttpServletRequest request) {
        if (null == request)
            log.error(">>>>>>>>>>>>>request is null in setRequest");
        requestLocal.set(request);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * @param request
     * @param response
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
    }
}
