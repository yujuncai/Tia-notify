package org.kikyou.tia.netty.notify.cluster.handler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.constant.ClusterMessageType;
import org.kikyou.tia.netty.notify.constant.EventNam;
import org.kikyou.tia.netty.notify.models.Message;
import org.kikyou.tia.netty.notify.service.DBStoreService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class SyncUserMessageHandler implements  BaseHandler{

    private final DBStoreService dbstoreService;
    private final SocketIOServer socketIOServer;
    @Override
    public void doHandler(Object m) {

        Message storeMsg= (Message) m;

        SocketIOClient receiverClient = socketIOServer.getNamespace( storeMsg.getFrom().getNameSpace()).getClient(UUID.fromString(storeMsg.getTo().getCurrId()));

        if (receiverClient != null && receiverClient.isChannelOpen()) {//本机在线
            receiverClient.sendEvent(EventNam.MESSAGE,
                    storeMsg.getFrom(),
                    storeMsg.getTo(),
                    storeMsg.getContent(),
                    storeMsg.getType());
            dbstoreService.updateMessage(storeMsg);
        }
    }

    @Override
    public String getProviderName() {
        return ClusterMessageType.SYNC_USER_MESSAGE.getName();
    }
}
