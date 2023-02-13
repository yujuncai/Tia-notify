package org.kikyou.tia.netty.client;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.SneakyThrows;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.client.shell.UserMap;

import java.net.URI;

public class MianTest {
    @SneakyThrows
    public static void main(String[] args)

    {


     String a = """
   [{"avatarUrl":"static/img/avatar/group-icon.png","name":"群聊天室","id":"group_001","type":"group"},
   {"deviceType":"pc","password":"222","avatarUrl":"./static/img/avatar/20180414165927.jpg","ip":"127.0.0.1","name":"222","id":"39da88e4-d9a6-493a-b98f-e1cb1df0ab20","time":1676272910257,"type":"user","roomId":"a07c32d3-65e5-45b8-b58b-3dd20620a2ce"},
   {"deviceType":"pc","password":"444","avatarUrl":"./static/img/avatar/20180414165821.jpg","ip":"127.0.0.1","name":"444","id":"d492dcde-c9ee-486b-8bee-c579ed18795e","time":1676271222735,"type":"user","roomId":"04482a18-0c7e-4b6c-ac24-eab46abecc43"}]
   """;

        JSONArray jsonArray=   JSONUtil.parseArray(a);
        for (int i = 0; i < jsonArray.size(); i++) {
            User u = JSONUtil.toBean((JSONObject) jsonArray.get(i), User.class);
            UserMap.map.put(u.getName(),u);
            System.out.println(u);
        }
    }
}
