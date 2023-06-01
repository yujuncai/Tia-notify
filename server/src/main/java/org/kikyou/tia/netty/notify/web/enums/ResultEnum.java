package org.kikyou.tia.netty.notify.web.enums;

import lombok.Getter;

/**
 * 后台返回结果集枚举
 */
@Getter
public enum ResultEnum  {

    /**
     * 通用状态
     */
    SUCCESS(200, "成功"),
    ERROR(400, "错误"),

    /**
     * 账户问题
     */
    USER_EXIST(401, "该用户名已经存在"),
    USER_PWD_NULL(402, "密码不能为空"),
    USER_INEQUALITY(403, "两次密码不一致"),
    USER_OLD_PWD_ERROR(404, "原来密码不正确"),
    USER_NAME_PWD_NULL(405, "用户名和密码不能为空"),
    USER_CAPTCHA_ERROR(406, "验证码错误"),


    /**
     * 非法操作
     */
    STATUS_ERROR(401, "非法操作：状态有误"),



    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
