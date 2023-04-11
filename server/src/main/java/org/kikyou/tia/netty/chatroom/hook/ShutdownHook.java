package org.kikyou.tia.netty.chatroom.hook;

import com.aliyun.oss.common.utils.StringUtils;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.cluster.Keeping;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.StoreService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.kikyou.tia.netty.chatroom.constant.Common.USER_KEY;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShutdownHook {

    private final SocketIOServer socketIOServer;

    private final StoreService storeService;

    private final StringRedisTemplate stringRedisTemplate;

    //在停机时,需要清理本机的token
    @PreDestroy
    public void preDestroy() {
        log.info("shutdown hook, del token");
        socketIOServer.getAllClients()
                .stream().parallel()
                .forEach(socketIOClient -> {
                    User user = socketIOClient.get(USER_KEY);
                    socketIOClient.del(USER_KEY);
                    if (!Objects.isNull(user)) {
                        storeService.delIdKeyV(user.getId(), user.getNameSpace());
                    }
                });

        if (!StringUtils.isNullOrEmpty(Keeping.HOST)) {
            log.info("shutdown hook, del host info");
            stringRedisTemplate.opsForHash().delete(Keeping.NAMESPACE_KEY, Keeping.HOST);
            stringRedisTemplate.opsForHash().delete(Keeping.MONITOR_KEY, Keeping.HOST);
        }

        log.info("shutdown hook, GG");
    }
}
