package org.kikyou.tia.netty.notify.web.dto;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public final class ErrorDto {

    private final String timestamp = LocalDateTime.now().toString();


    private final String errMessage;


    private final String exceptionName;


    public ErrorDto(final Exception exception) {
        this.errMessage = exception.getMessage();
        this.exceptionName = exception.getClass().getName();
    }
}