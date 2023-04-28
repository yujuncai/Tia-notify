package org.kikyou.tia.netty.chatroom.web.controller;


import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.kikyou.tia.netty.chatroom.web.config.Auth;
import org.kikyou.tia.netty.chatroom.web.dto.InfoDto;
import org.kikyou.tia.netty.chatroom.web.dto.UptimeDto;
import org.kikyou.tia.netty.chatroom.web.dto.UsageDto;
import org.kikyou.tia.netty.chatroom.web.service.MonitorIndexService;
import org.kikyou.tia.netty.chatroom.web.utils.HttpServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;


@Controller
@RequestMapping(value = "/monitor")
@RequiredArgsConstructor
public class IndexController
{


    private final MonitorIndexService indexService;


    @GetMapping
    @Auth
    public String getIndex(final Model model) throws IOException
    {
        String host = HttpServletUtil.getParameter("host");

        if(StrUtil.isEmpty(host)){
            return "redirect:/login";
        }

        return indexService.getIndex(host,model);
    }



}