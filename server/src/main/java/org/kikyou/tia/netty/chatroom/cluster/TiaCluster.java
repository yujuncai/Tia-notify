package org.kikyou.tia.netty.chatroom.cluster;

public interface TiaCluster {


    public boolean isCluster();
    public boolean isLeader();
    public void allMembers();
    public void sendMessage( )throws Exception;
}
