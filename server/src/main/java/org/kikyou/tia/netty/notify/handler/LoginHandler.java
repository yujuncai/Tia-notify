package org.kikyou.tia.netty.notify.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.annotation.RegisterToListener;
import org.kikyou.tia.netty.notify.constant.EventNam;
import org.kikyou.tia.netty.notify.constant.SystemType;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.service.LoginService;
import org.springframework.stereotype.Component;

import static org.kikyou.tia.netty.notify.constant.Common.TOKEN;

/**
 * 监听登录数据
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RegisterToListener
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
