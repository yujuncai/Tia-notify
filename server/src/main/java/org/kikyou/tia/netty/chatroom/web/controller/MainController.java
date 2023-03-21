package org.kikyou.tia.netty.chatroom.web.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.kikyou.tia.netty.chatroom.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Controller
@RequiredArgsConstructor
public class MainController{


    private final RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/index")
    public String index(Model model){
        return "/system/main/index";
    }


    @GetMapping("/main")
    public String main(HttpServletRequest request, HttpServletResponse response, Model model){
        Optional<Cookie> code_cookie=  Arrays.stream(request.getCookies()).filter(p -> p.getName().equals("online_token") ).findFirst();;
        String  vale  =code_cookie.get().getValue();
       User u= (User) redisTemplate.opsForValue().get("WEB_ONLINE_".concat(vale));
        model.addAttribute("user", u);
        model.addAttribute("treeMenu", null);
        return "/main";
    }
}
