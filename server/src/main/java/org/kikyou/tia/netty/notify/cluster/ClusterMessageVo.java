package org.kikyou.tia.netty.notify.cluster;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClusterMessageVo<T> implements Serializable {

    private String msgType;
    private T data;
}
