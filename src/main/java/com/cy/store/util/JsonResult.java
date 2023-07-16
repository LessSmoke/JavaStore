package com.cy.store.util;

import java.io.Serializable;

/**
 * Json格式的数据响应
 */
public class JsonResult<E> implements Serializable {

    /**状态码**/
    private Integer state;
    /**描述信息**/
    private String message;
    /**返回数据(数据类型不确定，用泛型来表示)*/
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
