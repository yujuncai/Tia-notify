package org.kikyou.tia.netty.chatroom.utils.monitor;


import lombok.Data;

/**
 * 网卡信息包装类
 *
 */
@Data
public class NetworkInfo {

    /**
     * ipv4地址
     */
    private String ipv4Address;

    /**
     * ipv6地址
     */
    private String ipv6Address;

    /**
     * mac地址
     */
    private String macAddress;

    /**
     * 网卡名称
     */
    private String networkName;

    /**
     * 发送数据量
     */
    private long send;

    /**
     * 接受数据量
     */
    private long accept;

    /**
     * 网卡时间戳
     */
    private long timeStamp;

}

