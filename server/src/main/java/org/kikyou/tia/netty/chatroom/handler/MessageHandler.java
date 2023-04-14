package org.kikyou.tia.netty.chatroom.handler;

import cn.hutool.core.util.IdUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.cluster.TiaCluster;
import org.kikyou.tia.netty.chatroom.constant.*;
import org.kikyou.tia.netty.chatroom.models.Message;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.DBStoreService;
import org.kikyou.tia.netty.chatroom.service.StoreService;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 监听接收消息
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final StoreService storeService;

    private final DBStoreService dbstoreService;
    private final SocketIOServer socketIOServer;
    private final SystemMessageHandler systemMessageHandler;
    @OnEvent(EventNam.MESSAGE)
    public void onData(SocketIOClient client, User from, User to, String content, String type, AckRequest ackSender) throws Exception {
        // 判断是指定发送方发送消息,还是群发
        log.debug("form:{} to:{} content:{} type:{}", from,to,content,type);

        User user =  client.get(Common.USER_KEY);
        from.setId(user.getId()); //替换from id
        Message storeMsg = new Message();
        storeMsg.setFrom(from);
        storeMsg.setTo(to);
        storeMsg.setType(type);
        storeMsg.setTime(System.currentTimeMillis());
        storeMsg.setContent(content);
        storeMsg.setId(IdUtil.fastSimpleUUID());

        if (UserType.USER.getName().equals(to.getType())) {
            // 向所属用户发消息
            SocketIOClient receiverClient = socketIOServer.getNamespace(user.getNameSpace()).getClient(UUID.fromString(to.getCurrId()));
            if (receiverClient != null && receiverClient.isChannelOpen()) {//本机在线
                receiverClient.sendEvent(EventNam.MESSAGE,
                        user,
                        to,
                        content,
                        type);
            }else {//离线或者不在本机,发送到集群
                systemMessageHandler.sendMessageToCluster(storeMsg);
            }
            //存到数据库
            dbstoreService.saveMessage(storeMsg);
        }
        if (UserType.GROUP.getName().equals(to.getType())) {
            CompletableFuture<Message> future= storeService.saveGroupMessage(from, to, content, MessageType.getTypeByName(type));//群消息到redis,异步,有IO操作
            // 群发
            socketIOServer.getNamespace(user.getNameSpace()).getBroadcastOperations().sendEvent(EventNam.MESSAGE,
                    /*排除自己*/
                    client,
                    user,
                    to,
                    content,
                    type);

            //并发送到集群
            future.thenAccept (systemMessageHandler::sendMessageToCluster);

        }
    }






}
