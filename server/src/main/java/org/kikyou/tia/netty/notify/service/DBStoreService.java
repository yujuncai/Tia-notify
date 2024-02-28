package org.kikyou.tia.netty.notify.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.constant.Common;
import org.kikyou.tia.netty.notify.constant.StatusType;
import org.kikyou.tia.netty.notify.models.Message;
import org.kikyou.tia.netty.notify.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DBStoreService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public User getUserByName(String name) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("username", name);
        List<User> entries = namedParameterJdbcTemplate.query("select * from db_user where name=:username", param, new BeanPropertyRowMapper<User>(User.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries.get(0);
        }


    }

    @Async("asyncExecutor")
    public void saveOrUpdateUser(User dbuser, User user, StatusType status) {
        log.debug("保存/更新user: {}, StatusType: {}", user, status);


        //插入数据
        if (dbuser != null) {
            BeanUtil.copyProperties(user, dbuser, "id", "password");
            MapSqlParameterSource param = new MapSqlParameterSource();
            log.info("用户信息: {}", dbuser);
            initParam(dbuser, param);
            namedParameterJdbcTemplate.update("Update db_user Set time=:time,avatarUrl=:avatarUrl,ip=:ip,deviceType=:deviceType,currId=:currId,type=:type,nameSpace=:nameSpace Where id = :id", param);
        } else {
            MapSqlParameterSource param = new MapSqlParameterSource();
            initParam(user, param);
            namedParameterJdbcTemplate.update("insert into db_user(id,name, password,time,avatarUrl,ip,deviceType,currId,type,nameSpace) " +
                    "values " +
                    "(:id,:name, :password,:time,:avatarUrl,:ip,:deviceType,:currId,:type,:nameSpace)", param);
        }


    }

    private void initParam(User u, MapSqlParameterSource param) {
        param.addValue("id", u.getId());
        param.addValue("name", u.getName());
        param.addValue("password", DigestUtil.md5Hex(u.getPassword().concat(Common.SALT)));
        param.addValue("time", DateUtil.current());
        param.addValue("avatarUrl", u.getAvatarUrl());
        param.addValue("ip", u.getIp());
        param.addValue("deviceType", u.getDeviceType());
        param.addValue("currId", u.getCurrId());
        param.addValue("type", u.getType());
        param.addValue("nameSpace", u.getNameSpace());
    }


    @Async("asyncExecutor")
    public void saveMessage(Message m,Integer readFlag) {
        MapSqlParameterSource param = new MapSqlParameterSource();

        param.addValue("id", m.getId());
        param.addValue("from_user", m.getFrom().getId());
        param.addValue("to_user", m.getTo().getId());
        param.addValue("contents", m.getContent());
        param.addValue("message_type", m.getType());
        param.addValue("read_flag", readFlag);
        param.addValue("times", DateUtil.date());

        namedParameterJdbcTemplate.update("insert into db_message(id,from_user, to_user,contents,message_type,read_flag,times) " +
                "values " +
                "(:id,:from_user, :to_user,:contents,:message_type,:read_flag,:times)", param);

    }

    @Async("asyncExecutor")
    public void updateMessage(Message m) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", m.getId());
        param.addValue("read_flag", 1);//标记为已读
        namedParameterJdbcTemplate.update("Update db_message Set read_flag=:read_flag Where id = :id", param);

    }
}
