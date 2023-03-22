package org.kikyou.tia.netty.chatroom.cluster;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.chatroom.utils.monitor.*;
import org.kikyou.tia.netty.chatroom.web.vo.DisksInfoVo;
import org.kikyou.tia.netty.chatroom.web.vo.NameSpaceVo;
import org.kikyou.tia.netty.chatroom.web.vo.NetworkInfoVo;
import org.kikyou.tia.netty.chatroom.web.vo.SystemVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Keeping {


    private final SocketIOServer socketIOServer;
    private final StringRedisTemplate stringRedisTemplate;
    public static  final String NAMESPACE_KEY="namespace_all";
    public static  final String MONITOR_KEY="monitor_all";
    @Scheduled(fixedDelay = 60_000)
    public void keeping() throws InterruptedException {
        List<NameSpaceVo> spacevos=new ArrayList<>();
        Collection<SocketIONamespace> allNamespaces = socketIOServer.getAllNamespaces();
        allNamespaces.stream().forEach((n) ->{
            SocketIONamespace namespace = socketIOServer.getNamespace(n.getName());
            long count = namespace.getAllClients().stream().count();
            NameSpaceVo v=new NameSpaceVo();
            v.setNamespace(n.getName());
            v.setCounts(String.valueOf(count));
            spacevos.add(v);
        });


        SystemVo vo=new SystemVo();
       // System.out.println("-----------系统信息-----------");
        OSInfo osInfo = SystemInfoUtil.getSystemInfo();
       // System.out.println("操作系统："+ osInfo.getOs());
        vo.setOs(osInfo.getOs());
       // System.out.println("系统架构："+ osInfo.getOsArch());
        vo.setOsArch(osInfo.getOsArch());
       // System.out.println("Java版本："+ osInfo.getJavaVersion());
        vo.setJavaVersion(osInfo.getJavaVersion());
       // System.out.println("工作目录："+ osInfo.getUserDir());
        vo.setUserDir(osInfo.getUserDir());
       // System.out.println("cpu核心数："+ osInfo.getCpuCount());
        vo.setCpuCount(osInfo.getCpuCount());
       // System.out.println("主机host："+ osInfo.getHost());
        vo.setHost(osInfo.getHost());
       // System.out.println("主机名称："+ osInfo.getHostName());
        vo.setHostName(osInfo.getHostName());

        String bootTime = osInfo.getBootTime();
        String runTime = "";
        if (StrUtil.isNotEmpty(bootTime)) {
            DateTime start = DateUtil.parse(bootTime, DatePattern.NORM_DATETIME_PATTERN);
            runTime = DateUtil.formatBetween(start, DateUtil.date());
        }
      //  System.out.println("系统正常运行时长："+ runTime);
        vo.setRunTime(runTime);
        //运行时信息
       // System.out.println("-----------运行时信息-----------");
        OSRuntimeInfo osRuntimeInfo = SystemInfoUtil.getOSRuntimeInfo();
        //1.CPU信息
       // System.out.println("------cpu信息------");
       // System.out.println("cpu使用率：" + SystemInfoUtil.formatRate(osRuntimeInfo.getCpuUsage()));
        vo.setCpuUsage(SystemInfoUtil.formatRate(osRuntimeInfo.getCpuUsage()));
       // System.out.println("cpu基准速度：" + osRuntimeInfo.getCpuMaxFreq());
        vo.setCpuMaxFreq(osRuntimeInfo.getCpuMaxFreq());
       // System.out.println("cpu速度：" + osRuntimeInfo.getCpuCurrentFreq());
        vo.setCpuCurrentFreq(osRuntimeInfo.getCpuCurrentFreq());
        //2.内存信息
      //  System.out.println("------内存信息------");
        //系统内存总量
        long total = osRuntimeInfo.getTotalMemory();
        long used = osRuntimeInfo.getUsedMemory();
        double usage = used * 1.0 / total;
       // System.out.println("系统内存总量：" + total + " -> " + SystemInfoUtil.formatData(total));
        vo.setTotalMemory(SystemInfoUtil.formatData(total));
       // System.out.println("系统内存使用量：" + used + " -> " + SystemInfoUtil.formatData(used));
        vo.setUsedMemory(SystemInfoUtil.formatData(used));
       // System.out.println("系统内存使用率：" + SystemInfoUtil.formatRate(usage));
        vo.setUsage(SystemInfoUtil.formatRate(usage));
        //可用虚拟总内存
        long swapTotal = osRuntimeInfo.getSwapTotalMemory();
        //已用虚拟内存
        long swapUsed = osRuntimeInfo.getSwapUsedMemory();
       // System.out.println("可用虚拟总内存(swap)：" + swapTotal + " -> " + SystemInfoUtil.formatData(swapTotal));
        vo.setSwapTotalMemory(SystemInfoUtil.formatData(swapTotal));
       // System.out.println("虚拟内存使用量(swap)：" + swapUsed + " -> " + SystemInfoUtil.formatData(swapUsed));
        vo.setSwapUsed(SystemInfoUtil.formatData(swapUsed));
        //3.磁盘信息
       // System.out.println("------磁盘信息------");
       // System.out.println("磁盘读取速度：" + osRuntimeInfo.getDiskReadRate() + "Kb/s");
        vo.setDiskReadRate(String.valueOf(osRuntimeInfo.getDiskReadRate()));
      //  System.out.println("磁盘写入速度：" + osRuntimeInfo.getDiskWriteRate() + "Kb/s");
        vo.setDiskWriteRate(String.valueOf(osRuntimeInfo.getDiskWriteRate()));

        List<DisksInfo> disksList = osRuntimeInfo.getDisksList();
        List<DisksInfoVo> diskvo=new ArrayList<>();
        for (DisksInfo disksInfo : disksList) {
           // System.out.println("挂载点：" + disksInfo.getDirName());
          //  System.out.println("文件系统名称：" + disksInfo.getSysTypeName());
          //  System.out.println("文件系统类型：" + disksInfo.getTypeName());
          //  System.out.println("磁盘总量：" + disksInfo.getTotal() + " -> " + SystemInfoUtil.formatData(disksInfo.getTotal()));
           // System.out.println("磁盘使用量：" + disksInfo.getUsed() + " -> " + SystemInfoUtil.formatData(disksInfo.getUsed()));
           // System.out.println("磁盘剩余量：" + disksInfo.getFree() + " -> " + SystemInfoUtil.formatData(disksInfo.getFree()));
          //  System.out.println("磁盘使用率：" + SystemInfoUtil.formatRate(disksInfo.getUsage()));

            DisksInfoVo v=new DisksInfoVo();
            v.setDirName(disksInfo.getDirName());
            v.setSysTypeName(disksInfo.getSysTypeName());
            v.setTypeName(disksInfo.getTypeName());
            v.setTotal(SystemInfoUtil.formatData(disksInfo.getTotal()));
            v.setUsed(SystemInfoUtil.formatData(disksInfo.getUsed()));
            v.setFree(SystemInfoUtil.formatData(disksInfo.getFree()));
            v.setUsedrate(SystemInfoUtil.formatRate(disksInfo.getUsage()));
            diskvo.add(v);
        }
        vo.setDisksInfos(diskvo);

        //4.网卡网络信息
        List<NetworkInfo> netList = SystemInfoUtil.getNetworkInfo();
       // System.out.println("------网卡网络信息------");

        List<NetworkInfoVo> netvos=new ArrayList<>();

        for (NetworkInfo networkInfo : netList) {
          //  System.out.println("ipv4地址："+networkInfo.getIpv4Address());
          //  System.out.println("mac地址："+networkInfo.getMacAddress());
           // System.out.println("网卡名称："+networkInfo.getNetworkName());
            double send = networkInfo.getSend() / 1024.0;
            double accept = networkInfo.getAccept() / 1024.0;
           // System.out.println("上传速度↑："+String.format("%.1f%s", send, "Kbps"));
          //  System.out.println("下载速度↓："+String.format("%.1f%s", accept, "Kbps"));
            NetworkInfoVo v=new NetworkInfoVo();
            v.setIpv4Address(networkInfo.getIpv4Address());
            v.setMacAddress(networkInfo.getMacAddress());
            v.setNetworkName(networkInfo.getNetworkName());
            v.setSend(String.format("%.1f%s", send, "Kbps"));
            v.setAccept(String.format("%.1f%s", accept, "Kbps"));
            netvos.add(v);
        }
        vo.setNetworkInfos(netvos);


        stringRedisTemplate.opsForHash().put(NAMESPACE_KEY,osInfo.getHost(), JSONUtil.toJsonStr(spacevos));
        stringRedisTemplate.opsForHash().put(MONITOR_KEY,osInfo.getHost(), JSONUtil.toJsonStr(vo));

    }
}
