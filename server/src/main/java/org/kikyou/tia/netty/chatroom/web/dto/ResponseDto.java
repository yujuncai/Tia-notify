package org.kikyou.tia.netty.chatroom.web.dto;

import lombok.Getter;


@Getter
public final class ResponseDto
{

    private final String message;


    public ResponseDto(final String message)
    {
        this.message = message;
    }
}