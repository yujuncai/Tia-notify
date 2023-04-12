package org.kikyou.tia.netty.chatroom.handler;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.constant.SystemType;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.StoreService;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.kikyou.tia.netty.chatroom.constant.Common.USER_KEY;

/**
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogoutHandler {

    private final SocketIOServer socketIOServer;

    private final StoreService storeService;
    private final SystemMessageHandler systemMessageHandler;

    @OnEvent(EventNam.LOGOUT)
    public void onData(SocketIOClient client, AckRequest ackSender)  {
        User user =  client.get(Common.USER_KEY);

        // 判断是否是已登录用户
        if (Objects.nonNull(user)) {
            log.debug("用户退出: {}", user.getName());
            client.del(Common.USER_KEY);
            storeService.delIdKeyV(user.getId(),user.getNameSpace());
            if (StrUtil.isNotBlank(user.getId())) {
                // 修改登录用户信息并通知所有在线用户
                systemMessageHandler.bocastSystemMessage(user, socketIOServer,SystemType.LOGOUT);

            }
        }
    }
}
