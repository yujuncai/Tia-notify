package org.kikyou.tia.netty.chatroom.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author yujuncai
 */
@Getter
@AllArgsConstructor
public enum ClusterMessageType {

    SYNC_NAMESPACE("sync_namespace"),
    SYNC_USER_MESSAGE("sync_user_message")
    ;

    private final String name;
}
