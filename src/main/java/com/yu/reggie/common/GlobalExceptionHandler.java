package com.yu.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
//通知
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @param exception
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.info(exception.getMessage());
        if (exception.getMessage().contains("Duplicate entry")){
            String[] split = exception.getMessage().split(" ");
            String msg = split[2]+"已存在";

        }
        return R.error("未知错误！");
    }
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception){
        return R.error(exception.getMessage());
    }
}
