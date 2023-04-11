package org.kikyou.tia.netty.chatroom.cluster;


import cn.hutool.core.map.MapUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.kikyou.tia.netty.chatroom.config.ServerRunner;
import org.kikyou.tia.netty.chatroom.constant.ClusterMessageType;

import java.util.Map;

public interface TiaCluster {

    default void addNamespace(String namespace) {
        ServerRunner sr= SpringUtil.getBean("serverRunner");
        sr.addNameSpaceHandler(namespace);
        try {
            SyncNameSpaceMessage(ClusterMessageType.SYNC_NAMESPACE_ADD.getName(),namespace);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    default void RemoveNamespace(String namespace) {
        ServerRunner sr= SpringUtil.getBean("serverRunner");
        sr.RemoveNameSpaceHandler(namespace);
        try {
            SyncNameSpaceMessage(ClusterMessageType.SYNC_NAMESPACE_REMOVE.getName(), namespace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    default void UpdateNamespace(String oldnamespace,String namespace) {
        ServerRunner sr= SpringUtil.getBean("serverRunner");
        sr.RemoveNameSpaceHandler(oldnamespace);
        sr.addNameSpaceHandler(namespace);
        try {
            Map<String,String> map= MapUtil.newHashMap();
            map.put("old",oldnamespace);
            map.put("new",namespace);
            SyncNameSpaceMessage(ClusterMessageType.SYNC_NAMESPACE_UPDATE.getName(), map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isCluster();
    public boolean isLeader();
    public void allMembers();
    public void SyncNameSpaceMessage(String type,Object o )throws Exception;


}
