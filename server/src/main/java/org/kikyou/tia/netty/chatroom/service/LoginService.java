package org.kikyou.tia.netty.chatroom.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.config.AppConfiguration;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.constant.StatusType;
import org.kikyou.tia.netty.chatroom.constant.SystemType;
import org.kikyou.tia.netty.chatroom.models.LoginSuccessData;
import org.kikyou.tia.netty.chatroom.models.Message;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.utils.MyBeanUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.kikyou.tia.netty.chatroom.constant.Common.USER_KEY;


/**
 * 登录操作
 * @author yujuncai
 */
@Service
@Slf4j
@AllArgsConstructor
public class LoginService {

    private final AppConfiguration appConfiguration;

    private final UserService userService;

    private final StoreService storeService;

    private final SocketIOServer socketIOServer;

    private final DBStoreService dbstoreService;

    public void login(User user, SocketIOClient client, boolean isReconnect) {
        userService.organizeUser(user,client);
        // 判断是否重复登录
        if (userService.exitActiveUser(user)) {
            client.sendEvent(EventNam.LOGIN_FAIL,"重复登录,请先退出!");
            return;
        }
        // 是否需要重新登录
        if (isReconnect) {
            loginSuccess(user, client);
        } else {
            // 判断用户是否存在
            User dbUser = userService.getUserByName(user.getName());
            if (Objects.isNull(dbUser)) {
                log.error("登录失败,账户'{}'不存在", user.getName());
                client.sendEvent(EventNam.LOGIN_FAIL,"登录失败,账户不存在!");
                return;
            }else if (!dbUser.getPassword().equals(DigestUtil.md5Hex(user.getPassword().concat(Common.SALT)))){
                log.error("登录失败,账户'{}'密码不正确", user.getName());
                client.sendEvent(EventNam.LOGIN_FAIL,"登录失败,用户名/密码不正确!");
                return;
            }

            loginSuccess(user, client);
            // saveOrUpdate user
            dbstoreService.saveOrUpdateUser(dbUser,user, StatusType.LOGIN);
        }
    }

    private void loginSuccess(User user, SocketIOClient client) {
        LoginSuccessData data = new LoginSuccessData();
        data.setUser(user);

        DateTime now = DateTime.now();
        DateTime overdue = now.offsetNew(DateField.DAY_OF_YEAR, 1);
        Map<String, Object> map = MyBeanUtils.beanToMapNotIgnoreNullValue(user);
        //签发时间
        map.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        map.put(JWTPayload.EXPIRES_AT, overdue);
        //生效时间
        map.put(JWTPayload.NOT_BEFORE, now);

        data.setToken(JWTUtil.createToken(map, appConfiguration.getTokenKey().getBytes(StandardCharsets.UTF_8)));

        // 通知有用户加入
        socketIOServer.getBroadcastOperations().sendEvent(EventNam.SYSTEM,
                /*排除自己*/
                client,
                user,
                SystemType.JOIN.getName());

        List<User> onlineUsers = userService.getOnlineUsers();
        // 为当前client赋值user
        client.set(USER_KEY, user);
        // 发送login_success事件
        client.sendEvent(EventNam.LOGIN_SUCCESS, data, onlineUsers);
        // 群group1消息
        List<Message> messages = storeService.getGroupMessages();
        client.sendEvent(EventNam.HISTORY_MESSAGE, Common.GROUP_001_CHANNEL, messages);
    }
}
