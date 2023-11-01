package org.kikyou.tia.netty.notify.hook;

import com.aliyun.oss.common.utils.StringUtils;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.cluster.Keeping;
import org.kikyou.tia.netty.notify.constant.Common;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.service.StoreService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.kikyou.tia.netty.notify.constant.Common.USER_KEY;

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

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {

            socketIOServer.getAllClients()
                    .stream().parallel()
                    .forEach(socketIOClient -> {
                        User user = socketIOClient.get(USER_KEY);
                        if (!Objects.isNull(user)) {
                            log.info("清理redis 中 本机的user关系 {}  {}" ,user.getNameSpace(),user.getId());
                            socketIOClient.del(USER_KEY);
                            storeService.delIdKeyV(user.getId(), user.getNameSpace());
                        }
                    });
        });


        if (!StringUtils.isNullOrEmpty(Keeping.HOST)) {
            log.info("shutdown hook, del host info");
            stringRedisTemplate.opsForHash().delete(Keeping.NAMESPACE_KEY, Keeping.HOST);
            stringRedisTemplate.opsForHash().delete(Keeping.MONITOR_KEY, Keeping.HOST);
        }


        try {
            voidCompletableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        socketIOServer.stop();
        log.info("shutdown hook, GG");
    }
}
