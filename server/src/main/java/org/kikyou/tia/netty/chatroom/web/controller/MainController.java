package org.kikyou.tia.netty.chatroom.web.controller;


import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.cluster.Keeping;
import org.kikyou.tia.netty.chatroom.models.User;
import org.kikyou.tia.netty.chatroom.web.config.Auth;
import org.kikyou.tia.netty.chatroom.web.enums.MenuTypeEnum;
import org.kikyou.tia.netty.chatroom.web.service.WebService;
import org.kikyou.tia.netty.chatroom.web.vo.Menu;
import org.kikyou.tia.netty.chatroom.web.vo.NameSpaceVo;
import org.kikyou.tia.netty.chatroom.web.vo.SystemVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController{
    private final StringRedisTemplate stringRedisTemplate;

    private final RedisTemplate<String,Object> redisTemplate;
    private final WebService menuService;
    @Auth
    @GetMapping("/main/index")
    public String index(Model model)  {

        Set<Object> namespaceKey = stringRedisTemplate.opsForHash().keys(Keeping.NAMESPACE_KEY);
        Set<Object> monitorKey = stringRedisTemplate.opsForHash().keys(Keeping.MONITOR_KEY);


        List<List<NameSpaceVo>> sos=new ArrayList<>();
        namespaceKey.stream().forEach(s ->{
            String value= (String) stringRedisTemplate.opsForHash().get(Keeping.NAMESPACE_KEY,s);
            List<NameSpaceVo>  list= JSONUtil.toList(value,NameSpaceVo.class);
            sos.add(list);
        });


        List<SystemVo> mos=new ArrayList<>();
        monitorKey.stream().forEach(s ->{
            String value= (String)  stringRedisTemplate.opsForHash().get(Keeping.MONITOR_KEY,s);
            SystemVo vo=(JSONUtil.toBean(value, SystemVo.class));
            mos.add(vo);
        });


        model.addAttribute("namespace", sos);
        model.addAttribute("monitor", mos);
        return "/system/main/index";
    }

    @Auth
    @GetMapping("/main")
    public String main(HttpServletRequest request, HttpServletResponse response, Model model){
        Optional<Cookie> code_cookie=  Arrays.stream(request.getCookies()).filter(p -> p.getName().equals("online_token") ).findFirst();;
        String  vale  =code_cookie.get().getValue();
       User u= (User) redisTemplate.opsForValue().get("WEB_ONLINE_".concat(vale));
        model.addAttribute("user", u);


        // 菜单键值对(ID->菜单)
        Map<Long, Menu> keyMenu = new HashMap<>(16);
        List<Menu> menus = menuService.getListBySortOk();
        menus.forEach(menu -> keyMenu.put(menu.getId(), menu));

        // 封装菜单树形数据
        Map<Long, Menu> treeMenu = new HashMap<>(16);
        keyMenu.forEach((id, menu) -> {
            if(!menu.getType().equals(MenuTypeEnum.BUTTON.getCode())){
                if(keyMenu.get(menu.getPid()) != null){
                    keyMenu.get(menu.getPid()).getChildren().put(Long.valueOf(menu.getSort()), menu);
                }else{
                    if(menu.getType().equals(MenuTypeEnum.DIRECTORY.getCode())){
                        treeMenu.put(Long.valueOf(menu.getSort()), menu);
                    }
                }
            }
        });

        model.addAttribute("treeMenu", treeMenu);
        return "/main";
    }
}
