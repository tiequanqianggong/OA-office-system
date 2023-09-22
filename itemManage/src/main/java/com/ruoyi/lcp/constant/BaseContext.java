package com.ruoyi.lcp.constant;

public class BaseContext {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCountNum(Long id) {
        threadLocal.set(id);
    }

    public static Long getCountNum() {
        return threadLocal.get();
    }

    public static void removeCountNum() {
        threadLocal.remove();
    }

}
