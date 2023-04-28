package org.kikyou.tia.netty.chatroom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author yujuncai
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "monitor")
public class MonitorKeyConfiguration {

    private String appid;

    private String key;


}
