package org.kikyou.tia.netty.chatroom.service;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.constant.StatusType;
import org.kikyou.tia.netty.chatroom.models.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 *
 * @author yujuncai
 */
@Slf4j
@Service
@AllArgsConstructor
public class RegisterService {

    private final UserService userService;

    private final DBStoreService dbstoreService;

    public void register(User user, SocketIOClient client) {
        // 组织user properties
        userService.organizeUser(user,client);
        // 判断用户是否已经存在
        User dbUser = userService.getUserByName(user.getName());
        if (Objects.isNull(dbUser)) {
            // register user
            dbstoreService.saveOrUpdateUser(null,user, StatusType.REGISTER);
            client.sendEvent(EventNam.REGISTER_SUCCESS,"注册成功,请登录!");
        } else {
            log.warn("注册失败,昵称'{}'已存在", user.getName());
            client.sendEvent(EventNam.REGISTER_FAIL,"注册失败,昵称已存在!");
        }
    }
}
