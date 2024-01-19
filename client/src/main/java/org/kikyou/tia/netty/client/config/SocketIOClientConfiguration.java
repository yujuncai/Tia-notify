package org.kikyou.tia.netty.client.config;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.utils.MySecureUtil;
import org.kikyou.tia.netty.notify.vo.SignatureTime;
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
        //socket.io/?a=a&b=b&EIO=3&c=c&transport=polling     signature 为通过AppSecret加密的 namespace 和 时间戳
        SignatureTime vo=new SignatureTime();
        vo.setSignature("/tia-java");
        vo.setTimes(DateUtil.current());
        String json = JSONUtil.toJsonStr(vo);
        String signature = MySecureUtil.aesEncrypt("jvZJhHtp3vOVmpool6QlMw==", json);

        log.info("signature {} ", signature);
        //可以查看表里的appid和key
        String s = "http://localhost:8080".concat("?").concat("appid=987654321").concat("&").concat("signature=" + signature);
        log.info("完整的路径 {} ", s);
        Manager m = new Manager(new URI(s));
        Socket socket = m.socket("/tia-java");

        return socket.connect();
    }


}
