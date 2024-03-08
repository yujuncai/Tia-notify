package org.kikyou.tia.netty.notify.cluster.handler;

public interface BaseHandler {

    void doHandler(Object name);

    //获取操作  参考ClusterMessageType
    String getProviderName();
}
