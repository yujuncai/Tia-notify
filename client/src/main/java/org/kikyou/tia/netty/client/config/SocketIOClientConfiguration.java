package org.kikyou.tia.netty.client.config;

import cn.hutool.json.JSONUtil;
import com.corundumstudio.socketio.SocketIOServer;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.utils.MySecureUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
@Component
@RequiredArgsConstructor
public class SocketIOClientConfiguration {


    @Bean
    public Socket socketIOServer() throws URISyntaxException {
        IO.Options options = new IO.Options();
///socket.io/?a=a&b=b&EIO=3&c=c&transport=polling
        //signature 为通过AppSecret加密的 namespace
//todo "{
// "time" : "12132434344",
// "name":"/tia-java"
// }"
        String signature = MySecureUtil.aesEncrypt("jvZJhHtp3vOVmpool6QlMw==", "/tia-java");
        String s="http://localhost:8080".concat("?").concat("appid=987654321").concat("&").concat("signature="+signature);
        log.info("完整的路径 {} ",s);
        Manager m=new Manager(new URI(s));
        Socket  socket=m.socket("/tia-java");
       // Socket socket = IO.socket(URI.create(s), options); //

        Socket connect = socket.connect();

        return connect;
    }



}
