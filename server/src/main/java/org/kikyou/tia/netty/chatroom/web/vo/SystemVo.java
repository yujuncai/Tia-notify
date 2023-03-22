package org.kikyou.tia.netty.chatroom.web.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SystemVo implements Serializable {

    private String os;
    private String osArch;
    private String javaVersion;
    private String userDir;
    private int cpuCount;
    private String host;
    private String hostName;
    private String runTime;
    private String   cpuUsage;
    private String cpuMaxFreq;
    private String  cpuCurrentFreq;
    private String  totalMemory;
    private String  usedMemory;
    private String  usage;
    private String  swapTotalMemory;
    private String  swapUsed;
    private String diskReadRate;
    private String diskWriteRate;
    private List<DisksInfoVo> disksInfos;
    private List<NetworkInfoVo> networkInfos;
}
