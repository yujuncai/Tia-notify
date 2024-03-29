package org.kikyou.tia.netty.notify.web.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.cluster.Keeping;
import org.kikyou.tia.netty.notify.config.MonitorKeyConfiguration;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.utils.MySecureUtil;
import org.kikyou.tia.netty.notify.vo.SignatureTime;
import org.kikyou.tia.netty.notify.web.config.Auth;
import org.kikyou.tia.netty.notify.web.enums.MenuTypeEnum;
import org.kikyou.tia.netty.notify.web.service.WebService;
import org.kikyou.tia.netty.notify.web.vo.Menu;
import org.kikyou.tia.netty.notify.web.vo.NameSpaceVo;
import org.kikyou.tia.netty.notify.web.vo.SystemVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;


@SuppressWarnings("OptionalGetWithoutIsPresent")
@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {
    private final StringRedisTemplate stringRedisTemplate;

    private final RedisTemplate<String, Object> redisTemplate;
    private final WebService menuService;

    private final MonitorKeyConfiguration monitorKeyConfiguration;

    @Auth
    @GetMapping("/main/index")
    public String index(Model model) {

        Set<Object> namespaceKey = stringRedisTemplate.opsForHash().keys(Keeping.NAMESPACE_KEY);
        Set<Object> monitorKey = stringRedisTemplate.opsForHash().keys(Keeping.MONITOR_KEY);



        List<List<NameSpaceVo>> sos =  namespaceKey.stream().map(s -> {
            String value = (String) stringRedisTemplate.opsForHash().get(Keeping.NAMESPACE_KEY, s);
            return JSONUtil.toList(value, NameSpaceVo.class);
        }).collect(Collectors.toList());



        List<SystemVo> mos =   monitorKey.stream().map(s -> {
            String value = (String) stringRedisTemplate.opsForHash().get(Keeping.MONITOR_KEY, s);
            return (JSONUtil.toBean(value, SystemVo.class));
        }).collect(Collectors.toList());


        model.addAttribute("namespace", sos);
        model.addAttribute("monitor", mos);


        SignatureTime vo=new SignatureTime();
        vo.setSignature("/monitor");
        vo.setTimes(DateUtil.current());
        String json = JSONUtil.toJsonStr(vo);

        String signature = MySecureUtil.aesEncrypt(monitorKeyConfiguration.getKey(), json);

        String s = "&appid=".concat(monitorKeyConfiguration.getAppid()).concat("&").concat("signature=" + signature).concat("&namespace=monitor");
        model.addAttribute("url", s);

        return "/system/main/index";
    }

    @Auth
    @GetMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        Optional<Cookie> code_cookie = Arrays.stream(request.getCookies()).filter(p -> p.getName().equals("online_token")).findFirst();
        String vale = code_cookie.get().getValue();
        User u = (User) redisTemplate.opsForValue().get("WEB_ONLINE_".concat(vale));
        model.addAttribute("user", u);


        // 菜单键值对(ID->菜单)
        Map<Long, Menu> keyMenu = new HashMap<>(16);
        List<Menu> menus = menuService.getMenuListBySortOk();
        menus.forEach(menu -> keyMenu.put(menu.getId(), menu));

        // 封装菜单树形数据
        Map<Long, Menu> treeMenu = new HashMap<>(16);
        keyMenu.forEach((id, menu) -> {
            if (!menu.getType().equals(MenuTypeEnum.BUTTON.getCode())) {
                if (keyMenu.get(menu.getPid()) != null) {
                    keyMenu.get(menu.getPid()).getChildren().put(Long.valueOf(menu.getSort()), menu);
                } else {
                    if (menu.getType().equals(MenuTypeEnum.DIRECTORY.getCode())) {
                        treeMenu.put(Long.valueOf(menu.getSort()), menu);
                    }
                }
            }
        });

        model.addAttribute("treeMenu", treeMenu);
        return "/main";
    }
}
