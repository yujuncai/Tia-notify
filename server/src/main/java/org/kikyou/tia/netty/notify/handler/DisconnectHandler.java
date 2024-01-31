package org.kikyou.tia.netty.notify.handler;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.constant.Common;
import org.kikyou.tia.netty.notify.constant.SystemType;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.service.StoreService;
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

    private final SystemMessageHandler systemMessageHandler;

    /**
     * 此处经常会因`transport close`断开连接, 见
     * 已解决https://github.com/mrniko/netty-socketio/issues/866
     */

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {

        log.info("用户断开链接: {}", client.getSessionId().toString());
        User user = client.get(Common.USER_KEY);

        if (!Objects.isNull(user)) {
            //清除redis的数据
            client.del(Common.USER_KEY);
            storeService.delIdKeyV(user.getId(), user.getNameSpace());
            if (StrUtil.isNotBlank(user.getId())) {
                systemMessageHandler.bocastSystemMessage(user, socketIOServer, SystemType.LOGOUT);
            }
        }
    }


}
