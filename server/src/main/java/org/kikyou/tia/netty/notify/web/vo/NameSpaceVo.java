package org.kikyou.tia.netty.notify.web.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class NameSpaceVo implements Serializable {
    private String namespace;
    private String counts;
}
