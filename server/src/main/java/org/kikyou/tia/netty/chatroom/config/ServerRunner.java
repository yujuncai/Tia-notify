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

    private final SocketIOServer socketIOServer;

    private final MainBodyService mainBodyService;

    @Override
    public void run(String... args) throws Exception {
        if (socketIOServer != null) {

            List<MainBody> allMainBody = mainBodyService.getAllMainBody();

            Optional.ofNullable(allMainBody).ifPresent(nss ->
                    nss.stream().forEach(ns -> {
                        String namesp = ns.getNameSpace();
                        if (!namesp.isEmpty() && namesp.startsWith("/") && namesp.length() < 50) {
                            log.info("{} 主体的-------   {}  加入namespace--------", ns.getName(), namesp);

                            SocketIONamespace socketIONamespace = socketIOServer.addNamespace(namesp);
                            //获取期待的类名
                            List<String> classNames =  Arrays.asList("loginHandler","logoutHandler","messageHandler","registerHandler","historyHandler");
                            try {
                                classNames.stream().forEach(s ->{
                                    Object bean = SpringUtil.getBean(s);
                                    Optional.ofNullable(bean).ifPresent(socketIONamespace::addListeners);
                                });
                            } catch (Exception e) {
                                        log.error("获取bean失败! {}",e);
                            }
                        }
                    }));
//            socketIOServer.getNamespace("/chat").addListeners(messageEventHandler);

        }
    }
}
