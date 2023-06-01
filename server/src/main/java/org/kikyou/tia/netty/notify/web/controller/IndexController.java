package org.kikyou.tia.netty.notify.web.controller;


import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.kikyou.tia.netty.notify.web.config.Auth;
import org.kikyou.tia.netty.notify.web.service.MonitorIndexService;
import org.kikyou.tia.netty.notify.web.utils.HttpServletUtil;
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