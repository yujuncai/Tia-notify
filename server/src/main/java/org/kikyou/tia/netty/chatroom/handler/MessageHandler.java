package org.kikyou.tia.netty.chatroom.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.constant.MessageType;
import org.kikyou.tia.netty.chatroom.constant.UserType;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.StoreService;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 监听接收消息
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final StoreService storeService;

    private final SocketIOServer socketIOServer;

    @OnEvent(EventNam.MESSAGE)
    public void onData(SocketIOClient client, User from, User to, String content, String type, AckRequest ackSender) throws Exception {
        // 判断是指定发送方发送消息,还是群发
        log.debug("form:{} to:{} content:{} type:{}", from,to,content,type);
        // form:User(id=cf54a807-b829-4cd6-a697-3ed11748b5b4, name=222, password=222, time=1676012342892, avatarUrl=./static/img/avatar/20180414165955.jpg, ip=127.0.0.1, deviceType=pc, roomId=cf54a807-b829-4cd6-a697-3ed11748b5b4, type=user)
        // to:User(id=7713bc0f-8525-446e-8875-740d75c8e591, name=111, password=111, time=1676012352755, avatarUrl=11, ip=127.0.0.1, deviceType=pc, roomId=7713bc0f-8525-446e-8875-740d75c8e591, type=user)
        // content:[微笑]
        //  type:text
        User user =  client.get(Common.USER_KEY);
        if (UserType.USER.getName().equals(to.getType())) {
            // 向所属用户发消息
            SocketIOClient receiverClient = socketIOServer.getClient(UUID.fromString(to.getCurrId()));
            if (receiverClient != null && receiverClient.isChannelOpen()) {//在线
                receiverClient.sendEvent(EventNam.MESSAGE,
                        user,
                        to,
                        content,
                        type);
            }else {//离线


            }
        }
        if (UserType.GROUP.getName().equals(to.getType())) {
            // 群发
            socketIOServer.getBroadcastOperations().sendEvent(EventNam.MESSAGE,
                    /*排除自己*/
                    client,
                    user,
                    to,
                    content,
                    type);
            storeService.saveGroupMessage(from, to, content, MessageType.getTypeByName(type));
           /* {"from":{"id":"e70e2bc0-1377-48e7-954d-6fb0e580604a","name":"222","password":"222",
                    "time":1676014544449,"avatarUrl":"./static/img/avatar/20180414165834.jpg",
                    "ip":"127.0.0.1","deviceType":"phone","roomId":"e70e2bc0-1377-48e7-954d-6fb0e580604a",
                    "type":"user"},
                "to":{"id":"group_001","name":"群聊天室","avatarUrl":"static/img/avatar/group-icon.png","type":"group"},
                "content":"1111","type":"text","time":1676014557280}*/
        }
    }
}
