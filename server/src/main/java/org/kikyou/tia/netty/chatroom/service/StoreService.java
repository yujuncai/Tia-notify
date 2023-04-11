package org.kikyou.tia.netty.chatroom.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.constant.Common;
import org.kikyou.tia.netty.chatroom.constant.MessageType;
import org.kikyou.tia.netty.chatroom.models.Message;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.utils.MyFileUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.kikyou.tia.netty.chatroom.constant.Common.GROUP_001_MESSAGE;
import static org.kikyou.tia.netty.chatroom.constant.Common.ID_KEY;


/**
 * store data
 * @author yujuncai
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final StringRedisTemplate stringRedisTemplate;

    private final RedisTemplate<String,Object> redisTemplate;

    private final FileUploadService fileUploadService;

   /* @Async
    public void saveOrUpdateUser(User user, StatusType status) {
        log.debug("保存/更新user: {}, StatusType: {}", user, status);
        Map<String, Object> map = MyBeanUtils.beanToMapNotIgnoreNullValue(user);
        redisTemplate.opsForHash().putAll(user.getName(), map);
    }*/

    @Async
    public void saveGroupMessage(User from, User to, String message/*当是图片的时候,这里传的是base64*/, MessageType type) throws Exception {
        if (MessageType.IMAGE.equals(type)) {
            File file = MyFileUtils.base64ToFile(message);
            message = fileUploadService.upload(file);
        }
        Message storeMsg = new Message();
        storeMsg.setFrom(from);
        storeMsg.setTo(to);
        storeMsg.setType(type.getName());
        storeMsg.setTime(System.currentTimeMillis());
        storeMsg.setContent(message);

        // 之后按时间排序获取
        stringRedisTemplate.opsForZSet().add(GROUP_001_MESSAGE, JSONUtil.toJsonStr(storeMsg), storeMsg.getTime());
    }

    public List<Message> getGroupMessages() {
        Set<String> set = stringRedisTemplate.opsForZSet().range(GROUP_001_MESSAGE, 0, 100);

        if (CollectionUtils.isEmpty(set)) {
            return Collections.emptyList();
        }
        return set.stream().map(str -> JSONUtil.toBean(str, Message.class))
                .collect(Collectors.toList());
    }

    public User getUserByName(String name){
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(name);
        if (MapUtil.isEmpty(entries)){
            return null;
        }
        return BeanUtil.mapToBean(entries, User.class, true, CopyOptions.create());
    }


    public void setIdKeyV(String id,String namespace,User u){
        if(StrUtil.isBlank(namespace)){
            namespace= Common.DEFAULT;
        }
        redisTemplate.opsForValue().set(namespace.concat("_").concat(ID_KEY).concat(id), u);
    }
    public User getIdKeyV(String id,String namespace){
        if(StrUtil.isBlank(namespace)){
            namespace= Common.DEFAULT;
        }
        return (User) redisTemplate.opsForValue().get(namespace.concat("_").concat(ID_KEY).concat(id)
        );
    }

    public void delIdKeyV(String id,String namespace){
        if(StrUtil.isBlank(namespace)){
            namespace= Common.DEFAULT;
        }
         redisTemplate.delete(namespace.concat("_").concat(ID_KEY).concat(id));
    }

}
