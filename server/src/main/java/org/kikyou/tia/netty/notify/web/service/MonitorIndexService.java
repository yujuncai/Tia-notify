package org.kikyou.tia.netty.notify.web.service;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.kikyou.tia.netty.notify.cluster.Keeping;
import org.kikyou.tia.netty.notify.web.dto.InfoDto;
import org.kikyou.tia.netty.notify.web.dto.UptimeDto;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.net.InetAddress;



@Service
public class MonitorIndexService {

    @Resource
    StringRedisTemplate stringRedisTemplate;




    private String getVersion() throws IOException {
        return "Developer mode";

    }


    public String getIndex(String host, final Model model) throws IOException {

        InetAddress address = InetAddress.getLocalHost();
        model.addAttribute("theme", "light");
        model.addAttribute("serverName", address.getHostAddress());


        String info = stringRedisTemplate.opsForValue().get(Keeping.MONITOR_KEY_INFO.concat(host));
        String uptime = stringRedisTemplate.opsForValue().get(Keeping.MONITOR_KEY_UPTIME.concat(host));

        if (StrUtil.isNotEmpty(info)) {
            model.addAttribute("info", JSONUtil.toBean(info, InfoDto.class));
        }
        if (StrUtil.isNotEmpty(uptime)) {
            model.addAttribute("uptime", JSONUtil.toBean(uptime, UptimeDto.class));
        }
        model.addAttribute("version", getVersion());




        return "/system/monitor/index";
    }
}