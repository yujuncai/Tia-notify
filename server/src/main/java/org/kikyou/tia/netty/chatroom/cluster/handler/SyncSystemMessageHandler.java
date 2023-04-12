package org.kikyou.tia.netty.chatroom.cluster.handler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.ClusterMessageType;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.Message;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.DBStoreService;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.kikyou.tia.netty.chatroom.constant.Common.USER_KEY;

@Component
@Slf4j
@RequiredArgsConstructor
public class SyncSystemMessageHandler implements  BaseHandler{

    private final SocketIOServer socketIOServer;
    @Override
    public void doHandler(Object m) {
        Map<String,Object> map= (Map<String, Object>) m;
         User user= (User) map.get("user");
        String  systemType = (String) map.get("systemType");

        socketIOServer.getNamespace(user.getNameSpace()).getAllClients().forEach(s -> {
                    User u = s.get(USER_KEY);
                    if (!Objects.isNull(u)) {
                        log.info(u.getName());
                        s.sendEvent(EventNam.SYSTEM, user, systemType);
                    }

                }
        );
    }

    @Override
    public String getProviderName() {
        return ClusterMessageType.SYNC_SYSTEM_MESSAGE.getName();
    }
}
