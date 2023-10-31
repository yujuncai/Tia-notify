package org.kikyou.tia.netty.notify.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.AuthorizationResult;
import com.corundumstudio.socketio.HandshakeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.config.MonitorKeyConfiguration;
import org.kikyou.tia.netty.notify.models.MainBody;
import org.kikyou.tia.netty.notify.service.MainBodyService;
import org.kikyou.tia.netty.notify.utils.MySecureUtil;
import org.kikyou.tia.netty.notify.vo.SignatureTime;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationHandler implements AuthorizationListener {

    private final MainBodyService mainBodyService;

    private final MonitorKeyConfiguration monitorKeyConfiguration;

    @SuppressWarnings("ConstantConditions")
    @Override
    public AuthorizationResult getAuthorizationResult(HandshakeData handshakeData) {


        //signature 为通过AppSecret加密的 namespace
        String signature = handshakeData.getSingleUrlParam("signature");
        String appid = handshakeData.getSingleUrlParam("appid");
        if (StrUtil.isNotBlank(signature) && StrUtil.isNotBlank(appid)) {


            if (appid.equals(monitorKeyConfiguration.getAppid())) {
                String s = MySecureUtil.aesDecrypt(monitorKeyConfiguration.getKey(), signature);
                SignatureTime signatureTime = JSONUtil.toBean(s, SignatureTime.class);
                Long times = signatureTime.getTimes();
               // long current = DateUtil.current();
                if ("/monitor".equals(signatureTime.getSignature())&&times!=null) {
                    return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
                }
            }


            log.info("appid-> {}   signature-> {}      url-> {}", appid, signature, handshakeData.getUrl());

            MainBody body = mainBodyService.getMainBodyByAppId(appid);
            if (body == null) {
                log.error("MainBody is null");
                return AuthorizationResult.FAILED_AUTHORIZATION;
            }
            String s = MySecureUtil.aesDecrypt(body.getAppSecret(), signature);

            SignatureTime signatureTime = JSONUtil.toBean(s, SignatureTime.class);

            if (!body.getNameSpace().equals(signatureTime.getSignature())) {
                log.error("{}  ----  {}", s, body.getNameSpace());
                return AuthorizationResult.FAILED_AUTHORIZATION;
            }

            Long times = signatureTime.getTimes();
            long current = DateUtil.current();
            if(times==null||current-times>10000){  //大于10秒
                log.error("{}  ----  {}", s, current-times);
                return AuthorizationResult.FAILED_AUTHORIZATION;
            }

            return AuthorizationResult.SUCCESSFUL_AUTHORIZATION;
        } else {


            log.error("signature err...");
            return AuthorizationResult.FAILED_AUTHORIZATION;
        }
    }
}
