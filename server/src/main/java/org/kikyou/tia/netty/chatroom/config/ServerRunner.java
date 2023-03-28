package org.kikyou.tia.netty.chatroom.config;

import cn.hutool.extra.spring.SpringUtil;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.models.MainBody;
import org.kikyou.tia.netty.chatroom.service.MainBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Order(Integer.MAX_VALUE)
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerRunner implements CommandLineRunner {



    private final MainBodyService mainBodyService;
    private final SocketIOServer socketIOServer;
    @Override
    public void run(String... args) throws Exception {
        if (socketIOServer != null) {

            List<MainBody> allMainBody = mainBodyService.getAllMainBody();

            Optional.ofNullable(allMainBody).ifPresent(nss ->
                    nss.stream().forEach(ns -> {
                        mainBodyService.addNameSpaceHandler(ns.getNameSpace());
                    }));


        }
    }
}
