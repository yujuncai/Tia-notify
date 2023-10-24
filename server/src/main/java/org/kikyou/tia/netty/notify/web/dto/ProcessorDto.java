package org.kikyou.tia.netty.notify.web.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProcessorDto {

    private String name;


    private String coreCount;


    private String clockSpeed;


    private String bitDepth;
}