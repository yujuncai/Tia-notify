package org.kikyou.tia.netty.notify.config;

import cn.hutool.extra.spring.SpringUtil;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.models.MainBody;
import org.kikyou.tia.netty.notify.service.MainBodyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Order(9999)
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerRunner implements CommandLineRunner {


    private final MainBodyService mainBodyService;
    private final SocketIOServer socketIOServer;
    private final static String MONITORSPACE = "/monitor";

    @Override
    public void run(String... args) throws Exception {
        if (socketIOServer != null) {

            List<MainBody> allMainBody = mainBodyService.getAllMainBody();
            Optional.ofNullable(allMainBody).ifPresent(nss ->
                    nss.forEach(ns -> addNameSpaceHandler(ns.getNameSpace())));

            //监控事件
            addMonitorSpaceHandler();


        }
    }


    public void addMonitorSpaceHandler() {
        log.info("  {}  加入namespace--------", MONITORSPACE);
        SocketIONamespace socketIONamespace = socketIOServer.addNamespace(MONITORSPACE);
        List<String> classNames = List.of("monitorHandler");
        try {
            classNames.forEach(s -> {
                Object bean = SpringUtil.getBean(s);
                Optional.ofNullable(bean).ifPresent(socketIONamespace::addListeners);
            });
        } catch (Exception e) {
            //noinspection PlaceholderCountMatchesArgumentCount
            log.error("获取bean失败! {}", e);
        }
    }


    public void addNameSpaceHandler(String namesp) {

        log.info("  {}  加入namespace--------", namesp);
        SocketIONamespace socketIONamespace = socketIOServer.addNamespace(namesp);
        //获取期待的类名
        List<String> classNames = Arrays.asList("loginHandler", "logoutHandler", "messageHandler", "registerHandler", "historyHandler","protoBufHandler");
        try {
            classNames.forEach(s -> {
                Object bean = SpringUtil.getBean(s);
                Optional.ofNullable(bean).ifPresent(socketIONamespace::addListeners);
            });
        } catch (Exception e) {
            //noinspection PlaceholderCountMatchesArgumentCount
            log.error("获取bean失败! {}", e);
        }

    }

    public void RemoveNameSpaceHandler(String namesp) {
        log.info("  {}  移除namespace--------", namesp);
        socketIOServer.removeNamespace(namesp);
    }


}
