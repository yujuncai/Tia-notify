package org.kikyou.tia.netty.chatroom.config;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.PingListener;
import com.corundumstudio.socketio.store.MemoryStoreFactory;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import io.netty.channel.epoll.Epoll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * SocketIo init
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SocketIOConfiguration {

    private final AppConfiguration appConfiguration;

    private final RedissonClient redissonClient;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        if (StrUtil.isNotBlank(appConfiguration.getHost())) {
            // If not set then bind address will be 0.0.0.0 or ::0
            config.setHostname(appConfiguration.getHost());
        }
        config.setPort(appConfiguration.getPort());
        // 设置允许最大帧长度
        config.setMaxFramePayloadLength(1024 * 256);
        // 允许最大http content length
        config.setMaxHttpContentLength(1024 * 256);
        config.setAllowCustomRequests(true);
        config.setTransports(Transport.WEBSOCKET, Transport.POLLING);
        config.setStoreFactory(new RedissonStoreFactory(redissonClient));
        config.setAllowHeaders("*");
        config.setOrigin("*");
        config.setBossThreads(appConfiguration.getBoss());
        config.setWorkerThreads(appConfiguration.getWorker());
        if (Epoll.isAvailable()) {//开启epoll
            config.setUseLinuxNativeEpoll(true);
        }
       /* 在给定的时间间隔（ pingInterval握手中发送的值），服务器发送一个 PING 数据包，客户端有几秒钟（该pingTimeout值）发送一个 PONG 数据包。
       如果服务器没有收到返回的 PONG 数据包，则认为连接已关闭。反之，如果客户端在 内没有收到 PING 包pingInterval + pingTimeout，则认为连接已关闭。
        */
        config.setAckMode(AckMode.AUTO);//自动ack
        SocketConfig sockConfig = new SocketConfig();
        // 服务端ChannelOption.SO_REUSEADDR, 地址重用, 应对address is in use
        sockConfig.setReuseAddress(true);
        sockConfig.setTcpKeepAlive(true);

        config.setSocketConfig(sockConfig);
        SocketIOServer s= new SocketIOServer(config);
        s.addPingListener(client -> log.debug("ping--------"+client.getSessionId()));
        return s;
    }

    /**
     * 开启 SocketIOServer 注解支持
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
        return new SpringAnnotationScanner(socketIOServer);
    }

}
