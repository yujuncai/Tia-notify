package org.kikyou.tia.netty.notify.service;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.models.MainBody;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainBodyService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


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


}
