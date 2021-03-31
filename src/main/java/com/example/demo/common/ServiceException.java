package com.example.demo.common;

/**
 * 异常服务类
 */
public class ServiceException extends RuntimeException {
    private static final Long serialVersionUid =1L;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServiceException(final String message,Throwable throwable){
        super(message,throwable);
        this.message = message;
    }

    public ServiceException(final String message){
        this.message = message;
    }

    public static void throwEx(String message){
        throw new ServiceException(message);
    }

}
