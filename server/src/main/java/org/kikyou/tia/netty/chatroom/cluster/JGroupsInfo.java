package org.kikyou.tia.netty.chatroom.cluster;



import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ObjectMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "cluster" ,name="enabled",havingValue = "true")
public class JGroupsInfo {




    @Value("${jgroups.config}")
    private String jGroupsConfig;

    @Value("${jgroups.clustername}")
    private String clusterName;

    private JChannel channel;

    @PostConstruct
    public void init() {
        log.info("init");
        try {
            log.info(jGroupsConfig);
            log.info(clusterName);
            channel = new JChannel(jGroupsConfig);
            channel.receiver(SpringUtil.getBean("org.kikyou.tia.netty.chatroom.cluster.ClusterReceiver"));
            channel.connect(clusterName);

        } catch (Exception ex) {
            log.error("registering the channel in JMX failed: {}", ex.toString());
        }
    }

    @PreDestroy
    public void close() {
        log.info("close");
        if (channel != null) {
            channel.close();
        }
    }

    public boolean isLeader() {
        boolean isLeader = false;
        Address address = channel.getView().getMembers().get(0);
        String state = channel.getState();
        if (address.equals(channel.getAddress())) {
            log.info("I'm ({}) the leader,state {}", channel.getAddress(),state);
            isLeader = true;
        }
        else {
            log.info("I'm ({}) the leader,state {}", channel.getAddress(),state);
        }
        return isLeader;
    }



    public void sendMessage() throws Exception {

        Message msg=new ObjectMessage(null, "Hello");

        channel.send(msg);
    }

}