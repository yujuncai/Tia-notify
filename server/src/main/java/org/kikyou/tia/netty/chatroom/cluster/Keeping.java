package org.kikyou.tia.netty.chatroom.cluster;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class Keeping {


    private final SocketIOServer socketIOServer;



    @Scheduled(fixedDelay = 10_000)
    public void keeping(){
        Collection<SocketIONamespace> allNamespaces = socketIOServer.getAllNamespaces();
        allNamespaces.stream().forEach((n) ->{

            SocketIONamespace namespace = socketIOServer.getNamespace(n.getName());
            long count = namespace.getAllClients().stream().count();

            log.info("namespace {}  -----  客户端数量 {} ", n.getName(),count);

        });

    }
}
