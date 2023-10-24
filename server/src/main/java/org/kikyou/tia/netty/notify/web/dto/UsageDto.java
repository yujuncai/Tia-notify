package org.kikyou.tia.netty.notify.web.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsageDto {

    private int processor;


    private int ram;


    private int storage;
}