package org.kikyou.tia.netty.notify.cluster.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.config.ServerRunner;
import org.kikyou.tia.netty.notify.constant.ClusterMessageType;
import org.kikyou.tia.netty.notify.constant.StaticConstant;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SyncNameSpaceUpdateHandler implements BaseHandler {
    //集群中命名空间更新
    private final ServerRunner serverRunner;

    @Override
    public void doHandler(Object name) {
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) name;
        serverRunner.RemoveNameSpaceHandler(map.get(StaticConstant.OLD_PARAM));
        serverRunner.addNameSpaceHandler(map.get(StaticConstant.NEW_PARAM));
    }

    @Override
    public String getProviderName() {
        return ClusterMessageType.SYNC_NAMESPACE_UPDATE.getName();
    }
}
