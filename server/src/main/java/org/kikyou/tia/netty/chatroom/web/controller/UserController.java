package org.kikyou.tia.netty.chatroom.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.web.config.Auth;
import org.kikyou.tia.netty.chatroom.web.service.WebService;
import org.kikyou.tia.netty.chatroom.web.utils.HttpServletUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final StringRedisTemplate stringRedisTemplate;

    private final RedisTemplate<String, Object> redisTemplate;
    private final WebService userService;

    @Auth
    @GetMapping("/main/user/index")
    public String index(Model model) {
        String search = HttpServletUtil.getRequest().getQueryString();
        model.addAttribute("search", search);
        return "/system/user/index";
    }



}
