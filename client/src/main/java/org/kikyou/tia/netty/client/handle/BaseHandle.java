package org.kikyou.tia.netty.client.handle;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.client.shell.UserMap;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseHandle {

    @Resource
    Socket socket;

    @PostConstruct
    public void init() {
        System.out.println("初始化接收消息------------");
        socket.on(EventNam.HISTORY_MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.HISTORY_MESSAGE + " : " + args[0]);
            }
        });


        socket.on(EventNam.REGISTER_FAIL, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.REGISTER_FAIL + " : " + args[0]);
            }
        });


        socket.on(EventNam.REGISTER_SUCCESS, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.REGISTER_SUCCESS + " : " + args[0]);
            }
        });


        socket.on(EventNam.LOGIN_FAIL, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.LOGIN_FAIL + " : " + args[0]);
            }
        });


        socket.on(EventNam.SERVER_ERR, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.SERVER_ERR + " : " + args[0]);
            }
        });


        socket.on(EventNam.LOGIN_SUCCESS, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.LOGIN_SUCCESS + " : " + args[0]);
                System.out.println(EventNam.LOGIN_SUCCESS + " : " + args[1]);
                if (!JSONUtil.isNull(args[1])) {
                   String json= String.valueOf(args[1]);
                    JSONArray jsonArray = JSONUtil.parseArray(json);
                    for (int i = 0; i < jsonArray.size(); i++) {
                        User u = JSONUtil.toBean((JSONObject) jsonArray.get(i), User.class);
                        UserMap.map.put(u.getName(),u);
                    }
                }

            }
        });


        socket.on(EventNam.MESSAGE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                System.out.println(EventNam.MESSAGE + " : " + args[0]);
                System.out.println(EventNam.MESSAGE + " : " + args[1]);
                System.out.println(EventNam.MESSAGE + " : " + args[2]);
                System.out.println(EventNam.MESSAGE + " : " + args[3]);
            }
        });


        socket.on(EventNam.SYSTEM, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.SYSTEM + " : " + args[0]);
            }
        });


        socket.on(EventNam.LOGIN, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.LOGIN + " : " + args[0]);
            }
        });

        socket.on(EventNam.LOGOUT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.LOGOUT + " : " + args[0]);
            }
        });

        socket.on(EventNam.REGISTER, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(EventNam.REGISTER + " : " + args[0]);
            }
        });



    }
}