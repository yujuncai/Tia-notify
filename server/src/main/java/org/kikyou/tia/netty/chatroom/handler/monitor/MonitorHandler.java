package org.kikyou.tia.netty.chatroom.handler.monitor;

import cn.hutool.core.util.IdUtil;
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
import org.kikyou.tia.netty.chatroom.handler.SystemMessageHandler;
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
public class MonitorHandler {

    private final StoreService storeService;


    private final SocketIOServer socketIOServer;

    @OnEvent(EventNam.MONITOR)
    public void onData(SocketIOClient client,  AckRequest ackSender) throws Exception {



            log.info("MONITOR");




    }






}
