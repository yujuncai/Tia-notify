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
@ConfigurationProperties(prefix = "tia")
public class AppConfiguration {

    private String host;

    private Integer port;

    private String tokenKey;

    private Integer boss;

    private Integer worker;

}
