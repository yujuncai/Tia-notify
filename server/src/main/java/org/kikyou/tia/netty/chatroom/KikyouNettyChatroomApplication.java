package org.kikyou.tia.netty.chatroom;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.config.AppConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author yujuncai
 */
@Slf4j
@AllArgsConstructor
@SpringBootApplication
//@EnableScheduling
public class KikyouNettyChatroomApplication implements CommandLineRunner {

    private final AppConfiguration appConfiguration;

    private final SocketIOServer socketIOServer;

    public static void main(String[] args) {
        SpringApplication.run(KikyouNettyChatroomApplication.class, args);
    }

    @Override
    public void run(String... args)  {

        socketIOServer.startAsync().addListener(future -> log.debug("server port start on {}", appConfiguration.getPort()));
    }
}
