package org.kikyou.tia.netty.chatroom.models;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author yujuncai
 */
@Data
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = -6127718690044821421L;
    private User from;

    private User to;

    private String content;

    private String type;

    private Long time;

}
