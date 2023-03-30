package org.kikyou.tia.netty.chatroom.cluster;

import lombok.extern.slf4j.Slf4j;
import org.jgroups.Message;
import org.jgroups.blocks.cs.ReceiverAdapter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "cluster" ,name="enabled",havingValue = "true")
public class ClusterReceiver extends ReceiverAdapter {


    //接收到消息后会调用此函数
    public void receive(Message msg) {
        String line = msg.getSrc() + ": " + msg.getObject();
        System.out.println(line);

    }
}