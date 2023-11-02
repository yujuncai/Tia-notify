package org.kikyou.tia.netty.notify.handler;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.config.AppConfiguration;
import org.kikyou.tia.netty.notify.constant.EventNam;
import org.kikyou.tia.netty.notify.models.Result;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.service.LoginService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.kikyou.tia.netty.notify.constant.Common.TOKEN;


/**
 * 监听连接事件
 *
 * @author yujuncai
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectHandler {

    private final AppConfiguration appConfiguration;

    private final LoginService loginService;


    @OnConnect
    public void onConnect(SocketIOClient client) {
        Map<String, List<String>> urlParams = client.getHandshakeData().getUrlParams();
        HttpHeaders httpHeaders = client.getHandshakeData().getHttpHeaders();
        String token = httpHeaders.get(TOKEN);

        log.info("客户端：{} 已连接, token: {}, urlParams:{} ,Namespace: {} ,url: {}", client.getSessionId(), token, urlParams, client.getNamespace().getName(), client.getHandshakeData().getUrl());


        User user = null;
        if (StrUtil.isNotBlank(token)) {
            try {
                if (JWTUtil.verify(token, appConfiguration.getTokenKey().getBytes(StandardCharsets.UTF_8))) {
                    JWTValidator.of(token).validateDate();
                    JSONObject payloads = JWTUtil.parseToken(token).getPayloads();
                    user = JSONUtil.toBean(payloads, User.class);
                }
            } catch (ValidateException e) {
                // token失效, 需要用户回到登录页面重新登录
                log.error("token失效, 重新认证...");
                client.sendEvent(EventNam.SERVER_ERR, new Result().error("请重新认证"));
                return;
            }
        }

        // 判断用户是否已经登录, 已登录的用户刷新token
        if (Objects.nonNull(user) && StrUtil.isNotBlank(user.getId())) {
            loginService.login(user, client, true);
        }
    }
}
