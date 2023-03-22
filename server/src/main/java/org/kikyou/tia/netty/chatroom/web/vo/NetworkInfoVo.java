package org.kikyou.tia.netty.chatroom.web.vo;

import lombok.Data;

@Data
public class NetworkInfoVo {

    private String ipv4Address;
    private String macAddress;
    private String networkName;
    private String send;
    private String accept;
}
