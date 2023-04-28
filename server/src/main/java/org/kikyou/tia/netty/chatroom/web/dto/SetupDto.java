package org.kikyou.tia.netty.chatroom.web.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class SetupDto
{

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 10)
    private String serverName;


    @NotNull
    @NotEmpty
    @Pattern(regexp = "light|dark")
    private String theme;


    @NotNull
    @NotEmpty
    @Min(value = 10)
    @Max(value = 65535)
    private String port;
}