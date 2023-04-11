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

    SYNC_NAMESPACE_ADD("sync_namespace_add"),
    SYNC_NAMESPACE_REMOVE("sync_namespace_remove"),
    SYNC_NAMESPACE_UPDATE("sync_namespace_update"),


    SYNC_USER_MESSAGE("sync_user_message")
    ;

    private final String name;
}
