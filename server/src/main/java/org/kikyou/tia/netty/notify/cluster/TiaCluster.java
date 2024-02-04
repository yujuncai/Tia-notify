package org.kikyou.tia.netty.notify.cluster;


import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.kikyou.tia.netty.notify.config.ServerRunner;
import org.kikyou.tia.netty.notify.constant.ClusterMessageType;
import org.kikyou.tia.netty.notify.constant.StaticConstant;

import java.util.Map;

public interface TiaCluster {

    default void addNamespace(String namespace) {
        ServerRunner sr = SpringUtil.getBean("serverRunner");
        sr.addNameSpaceHandler(namespace);
        SyncNameSpaceMessage(ClusterMessageType.SYNC_NAMESPACE_ADD.getName(), namespace);

    }


    default void RemoveNamespace(String namespace) {
        ServerRunner sr = SpringUtil.getBean("serverRunner");
        sr.RemoveNameSpaceHandler(namespace);
        SyncNameSpaceMessage(ClusterMessageType.SYNC_NAMESPACE_REMOVE.getName(), namespace);
    }


    default void UpdateNamespace(String oldnamespace, String namespace) {
        ServerRunner sr = SpringUtil.getBean("serverRunner");
        sr.RemoveNameSpaceHandler(oldnamespace);
        sr.addNameSpaceHandler(namespace);
        Map<String, String> map = MapUtil.newHashMap();
        map.put(StaticConstant.OLD_PARAM, oldnamespace);
        map.put(StaticConstant.NEW_PARAM, namespace);
        SyncNameSpaceMessage(ClusterMessageType.SYNC_NAMESPACE_UPDATE.getName(), map);
    }


    boolean isCluster();

    boolean isLeader();

    void allMembers();

    void SyncNameSpaceMessage(String type, Object o);

    void SyncUserMessage(String type, Object o);

    void SyncSystemssage(String type, Object o);
}
