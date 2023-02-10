package org.kikyou.tia.netty.chatroom.cluster;



import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "cluster" ,name="enabled",havingValue = "true")
public class JGroupsInfo {




    @Value("${jgroups.config}")
    private String jGroupsConfig;

    @Value("${jgroups.cluster}")
    private String clusterName;

    private JChannel channel;

    @PostConstruct
    public void init() {
        log.info("init");
        try {
            log.info(jGroupsConfig);
            log.info(clusterName);
            channel = new JChannel(jGroupsConfig);
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
        if (address.equals(channel.getAddress())) {
            log.info("I'm ({}) the leader", channel.getAddress());
            isLeader = true;
        }
        else {
            log.info("I'm ({}) not the leader", channel.getAddress());
        }
        return isLeader;
    }
}