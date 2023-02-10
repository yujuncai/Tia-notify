import cn.hutool.core.collection.CollectionUtil;
import jakarta.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kikyou.tia.netty.chatroom.KikyouNettyChatroomApplication;
import org.kikyou.tia.netty.chatroom.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = KikyouNettyChatroomApplication.class)
public class WebSpringTest {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Test
    public void testAddUser() {



        MapSqlParameterSource param = new MapSqlParameterSource();
        param.addValue("username", "111");

        List<User> entries = namedParameterJdbcTemplate.query("select * from db_user where name=:username",param,new BeanPropertyRowMapper<User>(User.class));
        System.out.println(entries);
        System.out.println(entries.size());

        //插入返回主键
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource param1 = new MapSqlParameterSource();
        param1.addValue("name", "李白");
        param1.addValue("password", "11111");
        namedParameterJdbcTemplate.update("insert into db_user(username, password) values (:name, :email)", param1, keyHolder);
        int keyId = keyHolder.getKey().intValue();
        System.out.println("插入的数据Id = " + keyId);

        //查询数据
        List<Map<String, Object>> mapList = namedParameterJdbcTemplate.queryForList("select * from db_user", new HashMap());
        for (Map<String, Object> userInfo : mapList) {
            System.out.println("id = " + userInfo.get("id") + ", name = " + userInfo.get("username") + ", email = " + userInfo.get("email"));
        }




    }
}
