package org.kikyou.tia.netty.chatroom.web.vo;

import lombok.Data;

@Data
public class DisksInfoVo {

    private String dirName;
    private String sysTypeName;
    private String typeName;


    private String  total;
    private String used;
    private String free;
    private String  usedrate;

}
