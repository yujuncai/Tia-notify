package org.kikyou.tia.netty.chatroom.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.LoginService;
import org.springframework.stereotype.Component;

/**
 * 监听登录数据
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHandler {

    private final LoginService loginService;

    @OnEvent(EventNam.LOGIN)
    public void onData(SocketIOClient client, User data, AckRequest ackSender)  {
        log.debug("用户登录: {}", data.getName());
        loginService.login(data, client, false);
    }
}
