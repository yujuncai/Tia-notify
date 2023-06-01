package org.kikyou.tia.netty.notify.constant;

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
