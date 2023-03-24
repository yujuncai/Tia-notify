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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Menu> getListBySortOk() {
        MapSqlParameterSource param = new MapSqlParameterSource();

        List<Menu> entries = namedParameterJdbcTemplate.query("select * from db_menu order by sort", param, new BeanPropertyRowMapper<Menu>(Menu.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries;
        }

    }


    public Menu getById(Long pid) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", pid);
        List<Menu> entries = namedParameterJdbcTemplate.query("select * from db_menu where id=:id", param, new BeanPropertyRowMapper<Menu>(Menu.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries.get(0);
        }


    }

    public List<Menu> findByPidAndIdNot(Long pid, Long id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("pid", pid);
        param.addValue("id", id);
        List<Menu> entries = namedParameterJdbcTemplate.query("select * from db_menu where pid=:pid and  id <>:id", param, new BeanPropertyRowMapper<Menu>(Menu.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries;
        }
    }


    @Transactional
    public void   saveMenu ( List<Menu> list){

        list.stream().forEach(s -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("icon",s.getIcon());
            param.addValue("pid",s.getPid());
            param.addValue("remark",s.getRemark());
            param.addValue("sort",s.getSort());
            param.addValue("status",1);
            param.addValue("title",s.getTitle());
            param.addValue("type",s.getType());
            param.addValue("url",s.getUrl());
            System.out.println("1111111");
            namedParameterJdbcTemplate.update("insert into db_menu(icon, pid,remark,sort,status,title,type,url) values (:icon, :pid,:remark,:sort,:status,:title,:type,:url)", param);
        });

    }



    @Transactional
    public void   updateMenu ( List<Menu> list){

        list.stream().forEach(s -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("id",s.getId());
            param.addValue("icon",s.getIcon());
            param.addValue("pid",s.getPid());
            param.addValue("remark",s.getRemark());
            param.addValue("sort",s.getSort());
            param.addValue("status",1);
            param.addValue("title",s.getTitle());
            param.addValue("type",s.getType());
            param.addValue("url",s.getUrl());

            namedParameterJdbcTemplate.update("update  db_menu set icon=:icon, pid=:pid,remark=:remark,sort=:sort,status=:status,title=:title,type=:type,url=:url where id=:id", param);
        });

    }




    @Transactional
    public boolean   delMenu ( List<Long> list){

        list.stream().forEach(s -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("id",s);
            namedParameterJdbcTemplate.update("  delete from db_menu where id=:id ", param);
        });

            return true;
    }

}