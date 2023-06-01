package org.kikyou.tia.netty.notify.web.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Data
public class Menu implements Serializable {

    private Long id;
    private Long pid;
    private String title;
    private String url;
    private String icon;
    private Byte type;
    private Integer sort;
    private String remark;
    @Transient
    @JsonIgnore
    private Map<Long, Menu> children = new HashMap<>();
}
