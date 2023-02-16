package org.kikyou.tia.netty.chatroom.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.DataListener;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.LoginService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.kikyou.tia.netty.chatroom.constant.Common.TOKEN;

/**
 * 监听登录数据
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHandler  {

    private final LoginService loginService;

    @OnEvent(EventNam.LOGIN)
    public void onData(SocketIOClient client, User data, AckRequest ackSender)  {

        HttpHeaders httpHeaders = client.getHandshakeData().getHttpHeaders();
        String token = httpHeaders.get(TOKEN);

        log.info("客户端：{} 已连接, token: {}, Namespace: {} ,url: {}", client.getSessionId(), token,client.getNamespace().getName(),client.getHandshakeData().getUrl());

        log.debug("用户登录: {}", data.getName());
        loginService.login(data, client, false);
    }
}
