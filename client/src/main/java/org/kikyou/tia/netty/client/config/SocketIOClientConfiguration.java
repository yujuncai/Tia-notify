package org.kikyou.tia.netty.client.config;

import cn.hutool.json.JSONUtil;
import com.corundumstudio.socketio.SocketIOServer;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class SocketIOClientConfiguration {


    @Bean
    public Socket socketIOServer() {
        IO.Options options = new IO.Options();

        Socket socket = IO.socket(URI.create("http://localhost/"), options); //


        return socket;
    }



}
