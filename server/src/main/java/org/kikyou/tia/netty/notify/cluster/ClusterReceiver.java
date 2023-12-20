package org.kikyou.tia.netty.notify.cluster;

import lombok.extern.slf4j.Slf4j;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.kikyou.tia.netty.notify.cluster.handler.BaseHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@ConditionalOnBean(JGroupsCluster.class)
public class ClusterReceiver implements Receiver {


    /**
     * 策略
     * KEY为业务编码
     * VALUE为具体实现类
     */
    private final ConcurrentHashMap<String, BaseHandler> strategy = new ConcurrentHashMap<>();


    public ClusterReceiver(List<BaseHandler> handlerList) {
        handlerList.forEach(handler -> {

            strategy.put(handler.getProviderName(), handler);
        });
        log.info("注入策略完毕");
    }


    //接收到消息后会调用此函数

    public void receive(Message msg) {
        log.info("收到 {} 的消息 {} 目标为 {}", msg.getSrc(), msg.getObject(), msg.getDest());

        if (msg.getSrc().equals(JGroupsCluster.localAddress)) {
            log.info("本地消息,丢弃!");
            return;
        }
        @SuppressWarnings("rawtypes")
        ClusterMessageVo vo = msg.getObject();
        BaseHandler h = strategy.get(vo.getMsgType());
        if (h != null) {
            h.doHandler(vo.getData());
        } else {
            log.error("没有对应的策略");
        }

    }
}