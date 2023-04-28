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
    private String targetAdress;
}