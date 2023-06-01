package org.kikyou.tia.netty.notify.cluster;


import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jgroups.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "cluster", name = "model", havingValue = "jgroups")
@RequiredArgsConstructor
public class JGroupsCluster implements TiaCluster {


    @Value("${jgroups.config}")
    private String jGroupsConfig;

    @Value("${jgroups.clustername}")
    private String clusterName;

    private JChannel channel;

    public static Address localAddress;
    @PostConstruct
    public void init() {
        log.info("init");
        try {

            log.info("加载配置文件 {} 开始 {}", jGroupsConfig,DateUtil.date());
            channel = new JChannel(jGroupsConfig);
            log.info("加载配置文件 {} 结束 {}", jGroupsConfig,DateUtil.date());
            channel.receiver(SpringUtil.getBean("clusterReceiver"));
            channel.connect(clusterName);
            localAddress = channel.getAddress();
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

    @Override
    public boolean isCluster() {
        return true;
    }

    public boolean isLeader() {
        boolean isLeader = false;
        Address address = channel.getView().getMembers().get(0);
        String state = channel.getState();


        if (address.equals(channel.getAddress())) {
            log.info("I'm ({}-----{}) the leader,state {}", address, channel.getAddress(), state);
            isLeader = true;
        } else {
            log.info("I'm (({}-----{})) the follower,state {}", address, channel.getAddress(), state);
        }
        return isLeader;
    }

    public void allMembers() {
        List<Address> address = channel.getView().getMembers();
        address.stream().forEach(s -> {
            log.info("address is {}", s);
        });
    }
    @Async("asyncExecutor")
    public void SyncNameSpaceMessage(String type, Object o) {
        try {
            sendMessage(type, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async("asyncExecutor")
    public void SyncUserMessage(String type, Object o) {
        try {
            sendMessage(type, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Async("asyncExecutor")
    public void SyncSystemssage(String type, Object o) {
        try {
            sendMessage(type, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(String type, Object o) throws Exception {
        ClusterMessageVo v = new ClusterMessageVo();
        v.setMsgType(type);
        v.setData(o);
        Message msg = new ObjectMessage(null, v);
        channel.send(msg);
    }
}