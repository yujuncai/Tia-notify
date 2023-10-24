package org.kikyou.tia.netty.notify.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yujuncai
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "manager")
public class AdminConfiguration {

    private String username;

    private String password;


}
