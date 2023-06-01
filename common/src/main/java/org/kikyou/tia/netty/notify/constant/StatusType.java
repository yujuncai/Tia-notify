package org.kikyou.tia.netty.notify.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author yujuncai
 */
@Getter
@AllArgsConstructor
public enum StatusType {

    LOGIN("login"),LOGOUT("logout"),REGISTER("register");

    private final String name;
}
