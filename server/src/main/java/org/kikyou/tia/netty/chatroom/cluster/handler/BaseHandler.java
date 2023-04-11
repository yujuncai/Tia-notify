package org.kikyou.tia.netty.chatroom.cluster.handler;

public interface BaseHandler {

    public void doHandler(Object name);

    public String getProviderName();
}
