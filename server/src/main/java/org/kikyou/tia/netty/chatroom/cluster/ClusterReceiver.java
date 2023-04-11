package org.kikyou.tia.netty.chatroom.cluster;

import lombok.extern.slf4j.Slf4j;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.blocks.cs.ReceiverAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnBean(JGroupsCluster.class)
public class ClusterReceiver implements Receiver {

    //接收到消息后会调用此函数
    public void receive(Message msg) {
        log.info("收到 {} 的消息 {} ",msg.getSrc(),msg.getObject());
        if(msg.getSrc().equals(msg.getDest())){
            return;
        }
        ClusterMessageVo vo= msg.getObject();


    }
}