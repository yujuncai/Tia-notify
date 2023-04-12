package org.kikyou.tia.netty.chatroom.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.constant.SystemType;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.LoginService;
import org.springframework.stereotype.Component;


import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.kikyou.tia.netty.chatroom.constant.Common.TOKEN;
import static org.kikyou.tia.netty.chatroom.constant.Common.USER_KEY;

/**
 * 监听登录数据
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHandler {

    private final LoginService loginService;
    private final SocketIOServer socketIOServer;
    private final SystemMessageHandler systemMessageHandler;

    @OnEvent(EventNam.LOGIN)
    public void onData(SocketIOClient client, User data, AckRequest ackSender) {
        HttpHeaders httpHeaders = client.getHandshakeData().getHttpHeaders();
        String token = httpHeaders.get(TOKEN);
        log.info("客户端：{} 已连接, token: {}, Namespace: {} ,url: {}", client.getSessionId(), token, client.getNamespace().getName(), client.getHandshakeData().getUrl());

        User u = loginService.login(data, client, false);

        if (u != null) {
            // 通知namespace下的的用户(登录状态)加入
            systemMessageHandler.bocastSystemMessage(u, socketIOServer, SystemType.JOIN);

        }

    }


}
