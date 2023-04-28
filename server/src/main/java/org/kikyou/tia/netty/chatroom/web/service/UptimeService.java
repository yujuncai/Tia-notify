package org.kikyou.tia.netty.chatroom.web.service;

import jakarta.annotation.Resource;
import org.kikyou.tia.netty.chatroom.web.dto.UptimeDto;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;

@Service
public class UptimeService
{
    @Resource
    private SystemInfo systemInfo;


    public UptimeDto getUptime()
    {
        UptimeDto uptimeDto = new UptimeDto();

        long uptimeInSeconds = systemInfo.getOperatingSystem().getSystemUptime();

        uptimeDto.setDays(String.format("%02d", (int) uptimeInSeconds / 86400));
        uptimeDto.setHours(String.format("%02d", (int) (uptimeInSeconds % 86400) / 3600));
        uptimeDto.setMinutes(String.format("%02d", (int) (uptimeInSeconds / 60) % 60));
        uptimeDto.setSeconds(String.format("%02d", (int) uptimeInSeconds % 60));

        return uptimeDto;
    }
}