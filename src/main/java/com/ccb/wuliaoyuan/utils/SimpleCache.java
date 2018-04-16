package com.ccb.wuliaoyuan.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Created by ccb on 2018/4/13.
 */
public class SimpleCache<R> {
    private static Map cache = new ConcurrentHashMap();

    public SimpleCache() {
    }

    public R getDataFromCache(String key, Function<String, R> function) {
        R value = this.from(key);
        if(null == value) {
            value = function.apply(key);
            this.to(key, value);
        }
        return value;
    }

    public void clearCache() {
        cache = new ConcurrentHashMap();
    }

    public void clearCache(String key) {
        cache.remove(key);
    }

    public R from(String key) {
        return (R) cache.get(key);
    }

    public void to(String key, R value) {
        if(null != value) {
            cache.put(key, value);
        }

    }

    public int size() {
        return cache.size();
    }

    public Map getCache() {
        return cache;
    }
}
