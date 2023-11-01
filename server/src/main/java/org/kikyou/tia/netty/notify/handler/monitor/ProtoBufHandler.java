package org.kikyou.tia.netty.notify.handler.monitor;

import cn.hutool.json.JSONUtil;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.constant.EventNam;
import org.kikyou.tia.netty.notify.constant.SystemType;
import org.kikyou.tia.netty.notify.models.protobuf.ProtobufMessage;
import org.kikyou.tia.netty.notify.web.dto.InfoDto;
import org.kikyou.tia.netty.notify.web.dto.UptimeDto;
import org.kikyou.tia.netty.notify.web.dto.UsageDto;
import org.kikyou.tia.netty.notify.web.service.InfoService;
import org.kikyou.tia.netty.notify.web.service.UptimeService;
import org.kikyou.tia.netty.notify.web.service.UsageService;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 监听接收消息
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProtoBufHandler {



    @OnEvent(EventNam.PROTOBUF)
    public void onData(SocketIOClient client, byte[] data, AckRequest ackSender) throws IOException {


        log.info("PROTOBUF data {}", data);

        Codec<ProtobufMessage> simpleTypeCodec = ProtobufProxy.create(ProtobufMessage.class);
        ProtobufMessage decode = simpleTypeCodec.decode(data);
        log.info("PROTOBUF decode {}", decode.toString());


    }


}
