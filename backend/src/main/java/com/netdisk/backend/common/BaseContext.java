package com.netdisk.backend.common;

/**
 * 基于ThreadLocal 封装的工具类 用于保存和获取当前登陆用户id --- 将session 的 id保存到一个局部变量
 * 各个线程之间独立
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
