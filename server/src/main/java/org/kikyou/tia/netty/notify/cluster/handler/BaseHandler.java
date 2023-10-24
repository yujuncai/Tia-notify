package org.kikyou.tia.netty.notify.cluster.handler;

public interface BaseHandler {

    void doHandler(Object name);

    String getProviderName();
}
