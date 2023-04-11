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


    @OnEvent(EventNam.LOGOUT)
    public void onData(SocketIOClient client, AckRequest ackSender)  {
        User user =  client.get(Common.USER_KEY);

        // 判断是否是已登录用户
        if (Objects.nonNull(user)) {
            log.debug("用户退出: {}", user.getName());
            client.del(Common.USER_KEY);
            if (!Objects.isNull(user)) {
                storeService.delIdKeyV(user.getId(),user.getNameSpace());
            }
            if (StrUtil.isNotBlank(user.getId())) {
                // 修改登录用户信息并通知所有在线用户
                // 通知namespace下的的用户(登录状态)加入 todo 集群状态下需要广播
                socketIOServer.getNamespace(user.getNameSpace()).getAllClients().stream().forEach(s -> {
                            User u = s.get(USER_KEY);

                            if (!Objects.isNull(u) ) {
                                log.info(u.getName());
                                s.sendEvent(EventNam.SYSTEM, user, SystemType.LOGOUT.getName());
                            }

                        }
                );
              //  socketIOServer.getNamespace(user.getNameSpace()).getBroadcastOperations().sendEvent(EventNam.SYSTEM, user, SystemType.LOGOUT.getName());
                //storeService.saveOrUpdateUser(user, StatusType.LOGOUT);
            }
        }
    }
}
