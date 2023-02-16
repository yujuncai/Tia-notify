package org.kikyou.tia.netty.chatroom.models;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MainBody implements Serializable {

    @Serial
    private static final long serialVersionUID = -7080937508871176980L;
    private String id;
    private String name;
    private String nameSpace;
    private String appSecret;
    private String mainStatus;
    private String appId;
}



