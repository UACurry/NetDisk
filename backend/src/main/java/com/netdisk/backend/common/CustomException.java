package com.netdisk.backend.common;

/**
 * 自定义业务异常 在删除菜品类时候使用
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
