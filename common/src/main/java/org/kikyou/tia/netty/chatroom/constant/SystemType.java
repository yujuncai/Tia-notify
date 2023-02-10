package org.kikyou.tia.netty.chatroom.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author yujuncai
 */
@Getter
@AllArgsConstructor
public enum SystemType {
    JOIN("join"),LOGOUT("logout");

    private final String name;
}
