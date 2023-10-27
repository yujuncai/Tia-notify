package org.kikyou.tia.netty.notify.handler;

import cn.hutool.core.util.StrUtil;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.config.MonitorKeyConfiguration;
import org.kikyou.tia.netty.notify.models.MainBody;
import org.kikyou.tia.netty.notify.service.MainBodyService;
import org.kikyou.tia.netty.notify.utils.MySecureUtil;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationHandler implements AuthorizationListener {

    private final MainBodyService mainBodyService;

    private final MonitorKeyConfiguration monitorKeyConfiguration;

    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData handshakeData) {




        //signature 为通过AppSecret加密的 namespace
        String signature = handshakeData.getSingleUrlParam("signature");
        String appid = handshakeData.getSingleUrlParam("appid");
        if (StrUtil.isNotBlank(signature) && StrUtil.isNotBlank(appid)) {


            if (appid.equals(monitorKeyConfiguration.getAppid())) {
                String s = MySecureUtil.aesDecrypt(monitorKeyConfiguration.getKey(), signature);
                if ("/monitor".equals(s)) {
                  return   AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
                }
            }

            //todo 加入时间限制
            log.info("appid-{}   signature-{}      url-{}", appid, signature, handshakeData.getUrl());
            MainBody body = mainBodyService.getMainBodyByAppId(appid);
            if (body == null) {
                log.error("MainBody is null");
                return   AuthorizationResult.FAILED_AUTHORIZATION;
            }
            String s = MySecureUtil.aesDecrypt(body.getAppSecret(), signature);
            if (!body.getNameSpace().equals(s)) {
                log.error("{}  ----  {}", s, body.getNameSpace());
                return   AuthorizationResult.FAILED_AUTHORIZATION;
            }
            return   AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
        } else {


            log.error("signature err...");
            return   AuthorizationResult.FAILED_AUTHORIZATION;
        }
    }
}
