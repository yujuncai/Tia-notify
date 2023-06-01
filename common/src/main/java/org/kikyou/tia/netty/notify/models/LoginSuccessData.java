package org.kikyou.tia.netty.notify.models;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * login success dto
 * @author yujuncai
 */
@Data
public class LoginSuccessData implements Serializable {

    @Serial
    private static final long serialVersionUID = -5080937508871176980L;
    private User user;

    private String token;
}
