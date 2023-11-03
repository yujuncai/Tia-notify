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
import org.kikyou.tia.netty.notify.models.Result;
import org.kikyou.tia.netty.notify.web.dto.InfoDto;
import org.kikyou.tia.netty.notify.web.dto.UptimeDto;
import org.kikyou.tia.netty.notify.web.dto.UsageDto;
import org.kikyou.tia.netty.notify.web.service.InfoService;
import org.kikyou.tia.netty.notify.web.service.UptimeService;
import org.kikyou.tia.netty.notify.web.service.UsageService;
import org.springframework.stereotype.Component;

/**
 * 监听接收消息
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MonitorHandler {

    private final InfoService infoService;
    private final UptimeService uptimeService;
    private final UsageService usageService;

    @OnEvent(EventNam.MONITOR)
    public void onData(SocketIOClient client, String host, AckRequest ackSender)  {


        log.info("MONITOR host {}", host);
        if (client.isChannelOpen() && client.isWritable()) {

            InfoDto info = infoService.getInfo();
            UptimeDto uptime = uptimeService.getUptime();
            UsageDto usage = usageService.getUsage();

            client.sendEvent(EventNam.SYSTEM, info, SystemType.MONITOR_INFO.getName());
            client.sendEvent(EventNam.SYSTEM, uptime, SystemType.MONITOR_UPTIME.getName());
            client.sendEvent(EventNam.SYSTEM, usage, SystemType.MONITOR_USAGE.getName());


         /*   Codec<Result> Codec = ProtobufProxy.create(Result.class);
            client.sendEvent(EventNam.SYSTEM, Codec.encode(new Result().okData(info)), SystemType.MONITOR_INFO.getName());
            client.sendEvent(EventNam.SYSTEM, Codec.encode(new Result().okData(uptime)), SystemType.MONITOR_UPTIME.getName());
            client.sendEvent(EventNam.SYSTEM, Codec.encode(new Result().okData(usage)), SystemType.MONITOR_USAGE.getName());*/
        }

    }


}
