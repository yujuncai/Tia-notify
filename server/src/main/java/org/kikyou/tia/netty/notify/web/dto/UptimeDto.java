package org.kikyou.tia.netty.notify.web.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UptimeDto {

    private String days;


    private String hours;


    private String minutes;

    private String seconds;
}