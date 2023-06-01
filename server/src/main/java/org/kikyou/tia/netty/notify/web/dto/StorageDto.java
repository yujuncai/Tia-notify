package org.kikyou.tia.netty.notify.web.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StorageDto
{

    private String mainStorage;


    private String total;


    private String diskCount;


    private String swapAmount;
}