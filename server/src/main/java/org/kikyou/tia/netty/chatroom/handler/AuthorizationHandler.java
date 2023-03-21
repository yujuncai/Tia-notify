package org.kikyou.tia.netty.chatroom.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.MainBody;
import org.kikyou.tia.netty.chatroom.service.MainBodyService;
import org.kikyou.tia.netty.chatroom.utils.MySecureUtil;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationHandler implements AuthorizationListener {

    private final MainBodyService mainBodyService;


    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {
        //signature 为通过AppSecret加密的 namespace
        String signature = handshakeData.getSingleUrlParam("signature");
        String appid = handshakeData.getSingleUrlParam("appid");
        if (StrUtil.isNotBlank(signature) && StrUtil.isNotBlank(appid)) {
            //todo 加入时间限制
            log.info("appid-{}   signature-{}      url-{}", appid, signature, handshakeData.getUrl());
            MainBody body = mainBodyService.getMainBodyByAppId(appid);
            String s = MySecureUtil.aesDecrypt(body.getAppSecret(), signature);
            if (!body.getNameSpace().equals(s)) {
                log.error("{}  ----  {}", s, body.getNameSpace());
                return false;
            }
            return true;
        } else {
            log.error("signature err...");
            return false;
        }
    }
}
