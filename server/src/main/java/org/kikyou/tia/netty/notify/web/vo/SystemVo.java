package org.kikyou.tia.netty.notify.web.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemVo implements Serializable {

    private String os;
    private String osArch;
    private String javaVersion;
    private String userDir;
    private int cpuCount;
    private String host;
    private String hostName;
    private String targetAdress;
}