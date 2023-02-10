package org.kikyou.tia.netty.chatroom.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author yujuncai
 */
@Getter
@AllArgsConstructor
public enum UserType {
    USER("user"),GROUP("group");

    private final String name;
}
