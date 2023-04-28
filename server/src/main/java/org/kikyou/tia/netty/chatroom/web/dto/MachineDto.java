package org.kikyou.tia.netty.chatroom.web.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MachineDto
{

    private String operatingSystem;


    private String totalRam;


    private String ramTypeOrOSBitDepth;


    private String procCount;
}