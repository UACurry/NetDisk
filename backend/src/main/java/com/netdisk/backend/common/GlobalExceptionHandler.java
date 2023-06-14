package com.netdisk.backend.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
//ControllerAdvice 指定拦截哪些controller
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理哪些异常
     * @param exception
     * @return
     */
//    ExceptionHandler 处理哪些异常
//    SQLIntegrityConstraintViolationException 处理 SQL 异常
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){

//        用户名已存在情况
        if(exception.getMessage().contains("Duplicate entry")){
            String[] spilt = exception.getMessage().split(" ");
            String msg = spilt[2] + " 已存在";
            return R.error(msg);
        }
//        处理Sql里面重复

        return R.error("未知错误");
    }

    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception){
        log.error(exception.getMessage());

        return R.error(exception.getMessage());
    }
}
