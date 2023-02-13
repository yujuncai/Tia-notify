package org.kikyou.tia.netty.client.shell;

import org.kikyou.tia.netty.chatroom.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserMap {
    public  static Map<String, User> map=new ConcurrentHashMap();
}
