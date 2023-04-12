package org.kikyou.tia.netty.chatroom.handler;

import cn.hutool.core.map.MapUtil;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.cluster.TiaCluster;
import org.kikyou.tia.netty.chatroom.constant.ClusterMessageType;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.constant.SystemType;
import org.kikyou.tia.netty.chatroom.models.Message;
import org.kikyou.tia.netty.chatroom.models.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

import static org.kikyou.tia.netty.chatroom.constant.Common.USER_KEY;

/**
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemMessageHandler {



    private final TiaCluster tiaCluster;
    @Async("asyncExecutor")
    public  void bocastSystemMessage(User user, SocketIOServer socketIOServer, SystemType systemType) {
        log.info("开始广播事件 {} ",systemType.getName());
        socketIOServer.getNamespace(user.getNameSpace()).getAllClients().forEach(s -> {
                    User u = s.get(USER_KEY);
                    if (!Objects.isNull(u)) {
                        log.info(u.getName());
                        s.sendEvent(EventNam.SYSTEM, user, systemType.getName());
                    }

                }
        );

        if (tiaCluster.isCluster()) {
            Map<String, Object> map = MapUtil.newHashMap();
            map.put("user", user);
            map.put("systemType", systemType.getName());
            tiaCluster.SyncSystemssage(ClusterMessageType.SYNC_SYSTEM_MESSAGE.getName(),map );
        }


    }




    public  void sendMessageToCluster(Message m){
        if(tiaCluster.isCluster()){
            tiaCluster.SyncUserMessage(ClusterMessageType.SYNC_USER_MESSAGE.getName(),m);
        }
    }


}
