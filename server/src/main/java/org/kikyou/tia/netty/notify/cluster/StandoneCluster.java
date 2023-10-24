package org.kikyou.tia.netty.notify.cluster;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "cluster", name = "model", havingValue = "standone")
public class StandoneCluster implements TiaCluster {
    @Override
    public boolean isLeader() {
        return false;
    }

    @Override
    public void allMembers() {
        log.debug("Standone model");
    }

    @Override
    public void SyncNameSpaceMessage(String type, Object o) {
        log.debug("Standone model");
    }

    @Override
    public void SyncUserMessage(String type, Object o) {
        log.debug("Standone model");
    }


    @Override
    public boolean isCluster() {
        return false;
    }

    @Override
    public void SyncSystemssage(String type, Object o) {

    }
}
