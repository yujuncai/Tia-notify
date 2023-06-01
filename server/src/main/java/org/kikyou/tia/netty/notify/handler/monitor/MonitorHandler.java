package org.kikyou.tia.netty.notify.handler.monitor;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.constant.EventNam;
import org.kikyou.tia.netty.notify.service.StoreService;
import org.springframework.stereotype.Component;

/**
 * 监听接收消息
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MonitorHandler {

    private final StoreService storeService;


    private final SocketIOServer socketIOServer;

    @OnEvent(EventNam.MONITOR)
    public void onData(SocketIOClient client,  AckRequest ackSender) throws Exception {



            log.info("MONITOR");




    }






}
