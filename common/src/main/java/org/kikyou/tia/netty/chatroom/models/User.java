package org.kikyou.tia.netty.chatroom.models;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author yujuncai
 */
@Data
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 2594108297042636782L;
    private String id;

    private String name;

    private String password;

    /**
     * 登录时间戳
     */
    private Long time;

    private String avatarUrl;

    private String ip;

    private String deviceType;

    private String currId;

    private String type;

    private String mainBody;//属于某一个主体
}
