package org.kikyou.tia.netty.chatroom.web.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    /**
     * 配置自定义的CusDialect，用于整合thymeleaf模板
     */
    @Bean
    public TiaDialect getTiaDialect() {
        return new TiaDialect();
    }


}
