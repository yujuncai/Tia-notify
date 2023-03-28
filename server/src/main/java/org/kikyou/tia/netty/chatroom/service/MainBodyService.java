package org.kikyou.tia.netty.chatroom.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.constant.StatusType;
import org.kikyou.tia.netty.chatroom.models.MainBody;
import org.kikyou.tia.netty.chatroom.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainBodyService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SocketIOServer socketIOServer;
    public List<MainBody> getAllMainBody() {
        List<MainBody> entries = namedParameterJdbcTemplate.query("select * from db_main_body where mainStatus=0", new MapSqlParameterSource(), new BeanPropertyRowMapper<MainBody>(MainBody.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries;
        }
    }


    public MainBody getMainBodyByAppId(String appId) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("appId", appId);
        List<MainBody> entries = namedParameterJdbcTemplate.query("select * from db_main_body where appId=:appId and mainStatus=0", param, new BeanPropertyRowMapper<MainBody>(MainBody.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries.get(0);
        }
    }



    public void addNameSpaceHandler(String namesp){

        if (!namesp.isEmpty() && namesp.startsWith("/") && namesp.length() < 50) {
            log.info("  {}  加入namespace--------",  namesp);
            SocketIONamespace socketIONamespace = socketIOServer.addNamespace(namesp);
            //获取期待的类名
            List<String> classNames = Arrays.asList("loginHandler", "logoutHandler", "messageHandler", "registerHandler", "historyHandler");
            try {
                classNames.stream().forEach(s -> {
                    Object bean = SpringUtil.getBean(s);
                    Optional.ofNullable(bean).ifPresent(socketIONamespace::addListeners);
                });
            } catch (Exception e) {
                log.error("获取bean失败! {}", e);
            }
        }
    }
    public void RemoveNameSpaceHandler(String namesp){
         socketIOServer.removeNamespace(namesp);
    }

}
