package com.example.demo.common;

import com.example.demo.dto.ResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    //业务异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServiceException.class})
    public ResultDto serviceExceptionHandler(ServiceException se){
        log.error(se.getMessage());
        return resultFormat(se);
    }

    //非业务异常
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler({Exception.class})
    public ResultDto exceptionHandler(Exception ex){
        log.error(ex.getMessage());
        return resultFormat(ex);
    }

    @ExceptionHandler({Throwable.class})
    public ResultDto throwableHandler(Throwable throwable){
        log.error(throwable.getMessage());
        return ResultDto.fail("系统错误");
    }

    public ResultDto resultFormat(Throwable throwable){
        String tips = "系统繁忙，请稍后重试";
        if (throwable instanceof ServiceException){
            return ResultDto.fail("业务异常," + tips);
        }
        if (throwable instanceof Exception){
            return ResultDto.fail("非业务异常," + tips);
        }
        return ResultDto.fail(tips);
    }
}
