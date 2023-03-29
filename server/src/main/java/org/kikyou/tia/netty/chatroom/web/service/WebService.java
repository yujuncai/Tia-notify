package org.kikyou.tia.netty.chatroom.web.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.config.ServerRunner;
import org.kikyou.tia.netty.chatroom.models.MainBody;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.service.MainBodyService;
import org.kikyou.tia.netty.chatroom.utils.MySecureUtil;
import org.kikyou.tia.netty.chatroom.web.page.PageSortUtil;
import org.kikyou.tia.netty.chatroom.web.vo.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final ServerRunner serverRunner;


    public List<Menu> getMenuListBySortOk() {
        MapSqlParameterSource param = new MapSqlParameterSource();

        List<Menu> entries = namedParameterJdbcTemplate.query("select * from db_menu order by sort", param, new BeanPropertyRowMapper<Menu>(Menu.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries;
        }

    }


    public Menu getMenuById(Long pid) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", pid);
        List<Menu> entries = namedParameterJdbcTemplate.query("select * from db_menu where id=:id", param, new BeanPropertyRowMapper<Menu>(Menu.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries.get(0);
        }


    }

    public List<Menu> findMenuByPidAndIdNot(Long pid, Long id) {
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


    @Transactional
    public Page<User> getUserPageList(String username) {

        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("username",username);
        String countsql="SELECT count(*) as count FROM db_user ";
        String querysql="SELECT * FROM db_user   ";
     if(StringUtils.isNotEmpty(username)){
         countsql=countsql+" where name=:username ";
         querysql=querysql+" where name=:username ";
     }
         Long count=   namedParameterJdbcTemplate.queryForObject(countsql,param,Long.class);
         Long totalSize = Objects.isNull(count) ? 0 : count;
        // 创建分页对象
        PageRequest pageable = PageSortUtil.pageRequest(Sort.Direction.ASC);
        if (totalSize.longValue() == 0) {
            List<User> list=new ArrayList<>();
            return  new PageImpl<>(list,pageable,totalSize);
        }
        List<User> list=   namedParameterJdbcTemplate.query(querysql+" limit "+pageable.getOffset() +","+pageable.getPageSize(),param,
                new BeanPropertyRowMapper<>(User.class));
        Page<User>  page = new PageImpl<>(list,pageable,totalSize);
        return page;
    }
    @Transactional
    public boolean   delUser ( List<String> list){
        list.stream().forEach(s -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("id",s);
            namedParameterJdbcTemplate.update("  delete from db_user where id=:id ", param);
        });
        return true;
    }


    @Transactional
    public Page<MainBody> getNameSpacePageList(String space) {

        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("space",space);
        String countsql="SELECT count(*) as count FROM db_main_body ";
        String querysql="SELECT * FROM db_main_body   ";
        if(StringUtils.isNotEmpty(space)){
            countsql=countsql+" where name=:space ";
            querysql=querysql+" where name=:space ";
        }
        Long count=   namedParameterJdbcTemplate.queryForObject(countsql,param,Long.class);
        Long totalSize = Objects.isNull(count) ? 0 : count;
        // 创建分页对象
        PageRequest pageable = PageSortUtil.pageRequest(Sort.Direction.ASC);
        if (totalSize.longValue() == 0) {
            List<MainBody> list=new ArrayList<>();
            return  new PageImpl<>(list,pageable,totalSize);
        }
        List<MainBody> list=   namedParameterJdbcTemplate.query(querysql+" limit "+pageable.getOffset() +","+pageable.getPageSize(),param,
                new BeanPropertyRowMapper<>(MainBody.class));
        Page<MainBody>  page = new PageImpl<>(list,pageable,totalSize);
        return page;
    }
    @Transactional
    public boolean   delNameSpace ( List<String> list){
        list.stream().forEach(s -> {

           MainBody b= this.getNameSpaceById(s);
            serverRunner.RemoveNameSpaceHandler(b.getNameSpace());

            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("id",s);
            namedParameterJdbcTemplate.update("  delete from db_main_body where id=:id ", param);


        });
        return true;
    }

    public MainBody getNameSpaceById(String id) {
        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("id", id);
        List<MainBody> entries = namedParameterJdbcTemplate.query("select * from db_main_body where id=:id", param, new BeanPropertyRowMapper<MainBody>(MainBody.class));
        if (CollectionUtil.isEmpty(entries)) {
            return null;
        } else {
            return entries.get(0);
        }


    }

    @Transactional
    public void   updateNameSapce ( List<MainBody> list) {

        list.stream().forEach(s -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("id",s.getId());
            param.addValue("name",s.getName());
            param.addValue("nameSpace",s.getNameSpace());
            param.addValue("mainStatus",s.getMainStatus());
            if (!s.getNameSpace().isEmpty() && s.getNameSpace().startsWith("/") && s.getNameSpace().length() < 50) {
                serverRunner.RemoveNameSpaceHandler(s.getNameSpace());
                serverRunner.addNameSpaceHandler(s.getNameSpace());
                namedParameterJdbcTemplate.update("update  db_main_body set name=:name, nameSpace=:nameSpace,mainStatus=:mainStatus where id=:id", param);
            }

        });
    }

    @Transactional
    public void   saveNameSapce ( List<MainBody> list) {

        list.stream().forEach(s -> {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue("id", IdUtil.fastSimpleUUID());
            param.addValue("name",s.getName());
            param.addValue("nameSpace",s.getNameSpace());
            param.addValue("appSecret", MySecureUtil.generateAesKey());
            param.addValue("mainStatus",0);
            param.addValue("appId",IdUtil.fastUUID());
            if (!s.getNameSpace().isEmpty() && s.getNameSpace().startsWith("/") && s.getNameSpace().length() < 50) {
                namedParameterJdbcTemplate.update("insert into db_main_body(id,name, nameSpace,appSecret,mainStatus,appId) values (:id,:name, :nameSpace,:appSecret,:mainStatus,:appId)", param);
                serverRunner.addNameSpaceHandler(s.getNameSpace());
            }
        });
    }
}