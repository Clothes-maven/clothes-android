package com.cloth.kernel.service.http.model;

import java.io.Serializable;

/**
 * Created by changshaowei on 2017/8/6.
 */

public class BaseResponse<T> implements Serializable {
    public int code;
    public T content;
    public String msg;
    public String status;
    public String uid;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public BaseResponse() {
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                ", code=" + code +
                ", content=" + content +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
