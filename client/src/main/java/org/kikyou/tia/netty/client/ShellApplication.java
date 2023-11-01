package org.kikyou.tia.netty.client;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.redisson.spring.starter.RedissonAutoConfigurationV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;


@SpringBootApplication(exclude = {RedissonAutoConfigurationV2.class})
public class ShellApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShellApplication.class, args);
    }
}
