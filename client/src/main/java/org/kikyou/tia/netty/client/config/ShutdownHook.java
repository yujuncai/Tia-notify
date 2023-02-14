package org.kikyou.tia.netty.client.config;

import io.socket.client.Socket;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class ShutdownHook {
    @Resource
    Socket socket;

    @PreDestroy
    public void preDestroy() throws InterruptedException {
        log.info(" shutdown hook");
        socket.disconnect();
        socket.close();
        Thread.sleep(100L);
    }
}

