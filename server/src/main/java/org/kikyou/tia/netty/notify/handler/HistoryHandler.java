package org.kikyou.tia.netty.notify.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.annotation.RegisterToListener;
import org.kikyou.tia.netty.notify.constant.Common;
import org.kikyou.tia.netty.notify.constant.EventNam;
import org.kikyou.tia.netty.notify.models.Message;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.service.StoreService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 监听登录数据
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RegisterToListener
public class HistoryHandler {

    private final StoreService storeService;

    @OnEvent(EventNam.HISTORY)
    public void onData(SocketIOClient client, User user, AckRequest ackSender) {
        log.info("history----{}" , user.getId());
        // 群group1消息
        List<Message> messages = storeService.getGroupMessages();

        if (ackSender.isAckRequested()) {
            ackSender.sendAckData(messages);
        } else {
            client.sendEvent(EventNam.HISTORY_MESSAGE, Common.GROUP_001_CHANNEL, messages);
        }
    }
}
