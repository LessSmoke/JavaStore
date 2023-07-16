package com.cy.store.service.exception;

/** 业务层异常的基类
 *              throws new ServiceException*/
public class ServiceException extends RuntimeException {

    //定义此类中的异常的构造方法，重写父接口的5个方法

    public ServiceException() {
        super();
    }

    // 业务层产生了未知的异常，只抛出异常信息
    public ServiceException(String message) {
        super(message);
    }

    // 抛出异常信息并且导致异常的原因也抛出
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    // 只抛出异常原因
    public ServiceException(Throwable cause) {
        super(cause);
    }

    // 导致异常的所有信息都被抛出
    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
