package org.kikyou.tia.netty.chatroom.utils.monitor;


import lombok.Data;

import java.util.List;


/**
 * 系统运行时信息
 *
 */
@Data
public class OSRuntimeInfo {

    /**
     * 时刻
     */
    private String timestamp;

    /**
     * cpu使用率
     */
    private double cpuUsage;

    /**
     * cpu基准速度（GHz）
     */
    private String cpuMaxFreq;

    /**
     * cpu当前速度（GHz）
     */
    private String cpuCurrentFreq;

    /**
     * 总内存
     */
    private long totalMemory;

    /**
     * 使用内存
     */
    private long usedMemory;

    /**
     * 可用虚拟总内存
     */
    private long swapTotalMemory;

    /**
     * 已用虚拟内存
     */
    private long swapUsedMemory;

    /**
     * 磁盘信息
     */
    private List<DisksInfo> disksList;

    /**
     * 磁盘读取速率（Kb/s）
     */
    private Double diskReadRate;

    /**
     * 磁盘写入速率（Kb/s）
     */
    private Double diskWriteRate;

    /**
     * 网卡信息
     */
    private List<NetworkInfo> networkList;

}

