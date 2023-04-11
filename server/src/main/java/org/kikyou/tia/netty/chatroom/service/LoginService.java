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
 *
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

        // 判断是否重复登录
        User dbUser = userService.getUserByName(user.getName());
        userService.organizeUser(dbUser, user, client);
        if (!Objects.isNull(dbUser) && userService.exitActiveUser(dbUser)) {
            client.sendEvent(EventNam.LOGIN_FAIL, "重复登录,请先退出!");
            return;
        }
        // 是否需要重新登录
        if (!isReconnect) {
            // 判断用户是否存在
            if (Objects.isNull(dbUser)) {
                log.error("登录失败,账户'{}'不存在", user.getName());
                client.sendEvent(EventNam.LOGIN_FAIL, "登录失败,账户不存在!");
                return;
            } else if (!dbUser.getPassword().equals(DigestUtil.md5Hex(user.getPassword().concat(Common.SALT)))) {
                log.error("登录失败,账户'{}'密码不正确", user.getName());
                client.sendEvent(EventNam.LOGIN_FAIL, "登录失败,用户名/密码不正确!");
                return;
            }
            // saveOrUpdate user
            dbstoreService.saveOrUpdateUser(dbUser, user, StatusType.LOGIN);
        }

        loginSuccess(user, client);
    }

    private void loginSuccess(User user, SocketIOClient client) {
        user.setPassword(null);
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

        // 通知namespace下的的用户(登录状态)加入 todo 集群状态下需要广播
        socketIOServer.getNamespace(user.getNameSpace()).getAllClients().stream().forEach(s -> {
                    User u = s.get(USER_KEY);
                    if (!Objects.isNull(u) ) {
                        log.info(u.getName());
                        s.sendEvent(EventNam.SYSTEM,
                                //client,
                                user,
                                SystemType.JOIN.getName());
                    }

                }
        );
        //当前namespace下的用户
        List<User> onlineUsers = userService.getOnlineUsers(user.getNameSpace());
        // 为当前client赋值user
        client.set(USER_KEY, user);
        //id-user 关系
        storeService.setIdKeyV(user.getId(), user.getNameSpace(), user);
        // 发送login_success事件
        client.sendEvent(EventNam.LOGIN_SUCCESS, data, onlineUsers);






    }
}
