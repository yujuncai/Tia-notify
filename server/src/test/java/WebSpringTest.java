import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kikyou.tia.netty.notify.TiaNettyChatroomApplication;
import org.kikyou.tia.netty.notify.cluster.Keeping;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.web.vo.NameSpaceVo;
import org.kikyou.tia.netty.notify.web.vo.SystemVo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TiaNettyChatroomApplication.class)
public class WebSpringTest {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
   // @Test
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



    @Test
    public void testredis() {

        Set<Object> namespaceKey = stringRedisTemplate.opsForHash().keys(Keeping.NAMESPACE_KEY);
        Set<Object> monitorKey = stringRedisTemplate.opsForHash().keys(Keeping.MONITOR_KEY);

        namespaceKey.stream().forEach(s ->{
            String value= (String) stringRedisTemplate.opsForHash().get(Keeping.NAMESPACE_KEY,s);
            List<NameSpaceVo>  list= JSONUtil.toList(value,NameSpaceVo.class);
            System.out.println(list);
        });

        monitorKey.stream().forEach(s ->{
            String value= (String)  stringRedisTemplate.opsForHash().get(Keeping.MONITOR_KEY,s);
            SystemVo vo=(JSONUtil.toBean(value, SystemVo.class));
            System.out.println(vo);
        });

    }

}
