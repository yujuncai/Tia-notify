package org.kikyou.tia.netty.chatroom.handler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.StoreService;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 监听连接断开事件
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DisconnectHandler {

    private final StoreService storeService;

    private final SocketIOServer socketIOServer;

    /**
     * 此处经常会因`transport close`断开连接, 见
     * 已解决https://github.com/mrniko/netty-socketio/issues/866
     */

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {

        log.debug("用户断开链接: {}", client.getSessionId().toString());
        User user = client.get(Common.USER_KEY);
        client.del(Common.USER_KEY);
        if (!Objects.isNull(user)) {
            storeService.delIdKeyV(user.getId(),user.getNameSpace());
        }
    }
}
