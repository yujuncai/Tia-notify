package org.kikyou.tia.netty.chatroom.cluster;


import cn.hutool.extra.spring.SpringUtil;
import org.kikyou.tia.netty.chatroom.config.ServerRunner;

public interface TiaCluster {

    default void addNamespace(String namespace) {
        ServerRunner sr= SpringUtil.getBean("serverRunner");
        sr.addNameSpaceHandler(namespace);
        try {
            SyncNameSpaceMessage(namespace);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    default void RemoveNamespace(String namespace) {
        ServerRunner sr= SpringUtil.getBean("serverRunner");
        sr.RemoveNameSpaceHandler(namespace);
        try {
            SyncNameSpaceMessage(namespace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    default void UpdateNamespace(String namespace) {
        ServerRunner sr= SpringUtil.getBean("serverRunner");
        sr.RemoveNameSpaceHandler(namespace);
        sr.addNameSpaceHandler(namespace);
        try {
            SyncNameSpaceMessage(namespace);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isCluster();
    public boolean isLeader();
    public void allMembers();
    public void SyncNameSpaceMessage(String namespace )throws Exception;
}
