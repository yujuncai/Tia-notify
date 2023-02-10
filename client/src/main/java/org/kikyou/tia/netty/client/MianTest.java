package org.kikyou.tia.netty.client;

import cn.hutool.json.JSONUtil;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.User;

import java.net.URI;

public class MianTest {
    @SneakyThrows
    public static void main(String[] args) {
        IO.Options options = new IO.Options();

        Socket socket = IO.socket(URI.create("http://localhost/"), options); //

        Socket connect = socket.connect();

        User user = new User();
        user.setName("111");
        user.setPassword("111");
        user.setAvatarUrl("11");
        user.setTime(1L);
        user.setType("2");
        user.setIp("111");

        socket.emit(EventNam.LOGIN, JSONUtil.parseObj(user));


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
                User user = new User();
                user.setName("111");
                user.setPassword("111");
                user.setAvatarUrl("11");
                user.setTime(1L);
                user.setType("2");
                user.setIp("111");

                socket.emit(EventNam.REGISTER, JSONUtil.parseObj(user));
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

        Thread.sleep(10000L);
    }
}
