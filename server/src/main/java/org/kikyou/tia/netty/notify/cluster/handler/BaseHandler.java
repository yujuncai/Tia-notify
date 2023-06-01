package org.kikyou.tia.netty.notify.cluster.handler;

public interface BaseHandler {

    public void doHandler(Object name);

    public String getProviderName();
}
