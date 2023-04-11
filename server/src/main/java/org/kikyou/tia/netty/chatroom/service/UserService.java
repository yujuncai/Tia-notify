package org.kikyou.tia.netty.chatroom.service;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.constant.UserType;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.utils.IpUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.kikyou.tia.netty.chatroom.constant.Common.USER_KEY;


/**
 * @author yujuncai
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final SocketIOServer socketIOServer;

    private final StoreService storeService;

    private final DBStoreService dbstoreService;

    List<User> initDefaultUser() {
        return new ArrayList<>() {
            {
                // 放置一个默认聊天室给新登录的账户
                User defaultU = new User();
                defaultU.setId(Common.GROUP_001_CHANNEL);
                defaultU.setName("群聊天室");
                defaultU.setAvatarUrl("img/group-icon.png");
                defaultU.setType(UserType.GROUP.getName());
                add(defaultU);
            }
        };
    }

    /**
     * 查询在线用户
     */
    public List<User> getOnlineUsers(String namespace) {
        List<User> users = initDefaultUser();
        // Returns the matching socket instances
        socketIOServer.getNamespace(namespace).getAllClients().forEach(socketIOClient -> {
            User user = socketIOClient.get(USER_KEY);
            if (Objects.nonNull(user)) {
                users.add(user);
            }
        });
        log.debug("OnlineUsers: {}", users);
        return users;
    }


    public User getUserByName(String name) {
        return dbstoreService.getUserByName(name);
    }


    public Boolean exitActiveUser(User sc) {
        User user = storeService.getIdKeyV(sc.getId(),sc.getNameSpace());
        if (Objects.nonNull(user)) {
            return user.getName().equals(sc.getName());
        }
        return false;
    }

    public void organizeUser(User dbUser, User user, SocketIOClient client) {
        String ip = StrUtil.replace(client.getHandshakeData().getAddress().getHostString(), "::ffff:", "");
        HttpHeaders httpHeaders = client.getHandshakeData().getHttpHeaders();
        String realIp = httpHeaders.get("x-forwarded-for");
        String userAgent = httpHeaders.get("user-agent").toLowerCase();
        ip = StrUtil.isNotBlank(realIp) ? realIp : ip;
        String deviceType = IpUtils.getDeviceType(userAgent);
        if (dbUser == null) {
            user.setId(client.getSessionId().toString());
        } else {
            user.setId(dbUser.getId());
        }

        user.setIp(ip);
        user.setDeviceType(deviceType);
        user.setCurrId(client.getSessionId().toString());
        user.setType(UserType.USER.getName());
        user.setTime(System.currentTimeMillis());
        user.setNameSpace(client.getNamespace().getName());
    }
}
