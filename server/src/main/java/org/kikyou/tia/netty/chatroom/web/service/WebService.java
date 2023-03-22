package org.kikyou.tia.netty.chatroom.web.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.constant.StatusType;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.web.vo.Menu;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Menu> getListBySortOk(){
        MapSqlParameterSource param = new MapSqlParameterSource();

        List<Menu> entries = namedParameterJdbcTemplate.query("select * from db_menu order by sort",param,new BeanPropertyRowMapper<Menu>(Menu.class));
        if(CollectionUtil.isEmpty(entries)){
            return null;
        }else {
            return entries;
        }

    }


}
