package org.kikyou.tia.netty.chatroom.cluster;

import lombok.extern.slf4j.Slf4j;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.blocks.cs.ReceiverAdapter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClusterReceiver implements Receiver {

    //接收到消息后会调用此函数
    public void receive(Message msg) {
        String line = msg.getSrc() + ": " + msg.getObject();
        System.out.println(line);

    }
}