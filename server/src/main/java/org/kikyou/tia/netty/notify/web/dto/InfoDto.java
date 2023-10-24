package org.kikyou.tia.netty.notify.web.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InfoDto {

    private ProcessorDto processor;


    private MachineDto machine;


    private StorageDto storage;
}