package com.worstEzreal.rabbitConsumer.common;

/**
 * Restful返回类型
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
public class Result<T> {
    private String status;
    private String msg;
    private T content;

    public Result() {
    }

    public Result(String status, String msg, T content) {
        this.status = status;
        this.msg = msg;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
