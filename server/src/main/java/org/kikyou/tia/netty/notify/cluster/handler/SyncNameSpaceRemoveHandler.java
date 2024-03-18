package org.kikyou.tia.netty.notify.cluster.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.config.ServerRunner;
import org.kikyou.tia.netty.notify.constant.ClusterMessageType;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SyncNameSpaceRemoveHandler implements BaseHandler {
    //命名空间从集群移除
    private final ServerRunner serverRunner;

    @Override
    public void doHandler(Object name) {
        serverRunner.RemoveNameSpaceHandler((String) name);
    }


    @Override
    public String getProviderName() {
        return ClusterMessageType.SYNC_NAMESPACE_REMOVE.getName();
    }
}
