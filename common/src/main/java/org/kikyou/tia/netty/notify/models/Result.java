package org.kikyou.tia.netty.notify.models;


import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@SuppressWarnings("rawtypes")
@Data
@ProtobufClass
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 编码：0表示成功，其他值表示失败
     */
    private int code = 0;
    /**
     * 消息内容
     */
    private String msg = "success";
    /**
     * 响应数据
     */
    private T data;

    public Result<T> okData(T data) {
        this.setData(data);
        return this;
    }

    public Result<T> ok(String msg) {
        this.setMsg(msg);
        return this;
    }


    public boolean success(){
        return code == 0;
    }

    public Result<T> error() {
        this.code = 999;
        this.msg = "未知异常!";
        return this;
    }

    public Result<T> error(int code) {
        this.code = code;
        this.msg = "系统异常";
        return this;
    }

    public Result<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public Result<T> error(String msg) {
        this.code = 888;
        this.msg = msg;
        return this;
    }

    public static Result errorMsg(String msg) {
        return new Result().error(msg);
    }

    public static Result okMsg(String msg) {
        return new Result().ok(msg);
    }
}

