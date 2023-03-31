package org.kikyou.tia.netty.chatroom.cluster;

import lombok.Data;

@Data
public class ClusterMessageVo<T> {

    private  String  msgType;
    private  T  t;
}
