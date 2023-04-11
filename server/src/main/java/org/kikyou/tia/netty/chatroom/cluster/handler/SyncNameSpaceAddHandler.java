package org.kikyou.tia.netty.chatroom.cluster.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.config.ServerRunner;
import org.kikyou.tia.netty.chatroom.constant.ClusterMessageType;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SyncNameSpaceAddHandler implements  BaseHandler{

    private final ServerRunner serverRunner;

    @Override
    public void doHandler(Object name) {
        serverRunner.addNameSpaceHandler((String) name);
    }

    @Override
    public String getProviderName() {
        return ClusterMessageType.SYNC_NAMESPACE_ADD.getName();
    }
}
