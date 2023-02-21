package org.kikyou.tia.netty.chatroom.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.Message;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.LoginService;
import org.kikyou.tia.netty.chatroom.service.StoreService;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.kikyou.tia.netty.chatroom.constant.Common.TOKEN;

/**
 * 监听登录数据
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HistoryHandler {

    private final StoreService storeService;

    @OnEvent(EventNam.HISTORY)
    public void onData(SocketIOClient client, User user, AckRequest ackSender) {
        log.info("history----"+user.getId());
        // 群group1消息
        List<Message> messages = storeService.getGroupMessages();
        client.sendEvent(EventNam.HISTORY_MESSAGE, Common.GROUP_001_CHANNEL, messages);

    }
}
