package com.worstEzreal.rabbitConsumer.common;

/**
 * Restful返回类型
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
public class Result {
    private String status;
    private String msg;
    private Object content;

    public Result(String status, String msg, Object content) {
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

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
