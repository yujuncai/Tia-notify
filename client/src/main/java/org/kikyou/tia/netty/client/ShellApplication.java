package org.kikyou.tia.netty.client;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {RedissonAutoConfiguration.class})
public class ShellApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShellApplication.class, args);
    }
}
