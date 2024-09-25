package org.kikyou.tia.netty.notify.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import io.netty.channel.ChannelHandlerContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * SocketIo init
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SocketIOConfiguration {

    private final AppConfiguration appConfiguration;

    private final RedissonClient redissonClient;


    @Order(1)
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

        config.setTransports(Transport.WEBSOCKET);
        config.setStoreFactory(new RedissonStoreFactory(redissonClient));
        config.setAllowHeaders("*");
        config.setOrigin(appConfiguration.getOrigin());
        config.setBossThreads(appConfiguration.getBoss());
        config.setWorkerThreads(appConfiguration.getWorker());
        if (config.isUseLinuxNativeEpoll()) {//开启epoll
            config.setUseLinuxNativeEpoll(true);
        }
       /* 在给定的时间间隔（ pingInterval握手中发送的值），服务器发送一个 PING 数据包，客户端有几秒钟（该pingTimeout值）发送一个 PONG 数据包。
       如果服务器没有收到返回的 PONG 数据包，则认为连接已关闭。反之，如果客户端在 内没有收到 PING 包pingInterval + pingTimeout，则认为连接已关闭。
        */
        //验证namespace
        config.setAuthorizationListener(SpringUtil.getBean("authorizationHandler"));

        config.setAckMode(AckMode.AUTO);//自动ack
        SocketConfig sockConfig = new SocketConfig();
        // 服务端ChannelOption.SO_REUSEADDR, 地址重用, 应对address is in use
        sockConfig.setReuseAddress(true);
        sockConfig.setTcpKeepAlive(true);

        config.setSocketConfig(sockConfig);
        //异常处理
        config.setExceptionListener(new ExceptionListener() {
            @Override
            public void onPongException(Exception e, SocketIOClient client) {
                        log.error("onPongException {}",client.getSessionId());
            }
            @Override
            public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
                log.error("onEventException {}",client.getSessionId());
            }
            @Override
            public void onDisconnectException(Exception e, SocketIOClient client) {
                log.error("onDisconnectException {}",client.getSessionId());
            }
            @Override
            public void onConnectException(Exception e, SocketIOClient client) {
                log.error("onConnectException {}",client.getSessionId());
            }
            @Override
            public void onPingException(Exception e, SocketIOClient client) {
                log.error("onPingException {}",client.getSessionId());
            }
            @Override
            public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e)  {
                return false;
            }

            @Override
            public void onAuthException(Throwable throwable, SocketIOClient client) {
                log.error("onAuthException {}",client.getSessionId());
            }
        });
        return new SocketIOServer(config);
    }

    /**
     * 开启 SocketIOServer 注解支持
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
        return new SpringAnnotationScanner(socketIOServer);
    }

}
