package org.kikyou.tia.netty.notify.cluster;

import cn.hutool.json.JSONUtil;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.web.service.InfoService;
import org.kikyou.tia.netty.notify.web.service.UptimeService;
import org.kikyou.tia.netty.notify.web.service.UsageService;
import org.kikyou.tia.netty.notify.web.vo.NameSpaceVo;
import org.kikyou.tia.netty.notify.web.vo.SystemVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class Keeping {


    private final SocketIOServer socketIOServer;
    private final StringRedisTemplate stringRedisTemplate;




    private final InfoService infoService;
    private final UptimeService uptimeService;
    private final UsageService usageService;


    public static  final String NAMESPACE_KEY="namespace_all";
    public static  final String MONITOR_KEY="monitor_all";

    public static  final String MONITOR_KEY_INFO="monitor_info";
    public static  final String MONITOR_KEY_UPTIME="monitor_uptime";
    public static  final String MONITOR_KEY_USAGE="monitor_usage";


    public static  String HOST=null;
    @SneakyThrows
    @Scheduled(fixedDelay = 60_000, initialDelay = 5000)
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

        InetAddress address = InetAddress.getLocalHost();
        HOST=address.getHostAddress();

        SystemVo vo=new SystemVo();
        Properties props = System.getProperties();
        // System.out.println("操作系统："+ osInfo.getOs());
        vo.setOs(props.getProperty("os.name"));
        // System.out.println("系统架构："+ osInfo.getOsArch());
        vo.setOsArch(props.getProperty("os.arch"));
        // System.out.println("Java版本："+ osInfo.getJavaVersion());
        vo.setJavaVersion(props.getProperty("java.version"));
        // System.out.println("工作目录："+ osInfo.getUserDir());
        vo.setUserDir(props.getProperty("user.dir"));
        vo.setHost(address.getHostAddress());
        // System.out.println("主机名称："+ osInfo.getHostName());
        vo.setHostName(address.getHostName());


        stringRedisTemplate.opsForHash().put(NAMESPACE_KEY,HOST, JSONUtil.toJsonStr(spacevos));
        stringRedisTemplate.opsForHash().put(MONITOR_KEY,HOST, JSONUtil.toJsonStr(vo));

    }




 /*   @Scheduled(fixedDelay = 60_000, initialDelay = 10000)
    public void cluster() throws Exception {

        if(tiaCluster.isCluster()){
            tiaCluster.allMembers();
            tiaCluster.isLeader();
           }
    }*/



    @Scheduled(fixedDelay = 5_000, initialDelay = 1000)
    public void monitor() throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        HOST=address.getHostAddress();
        var info = infoService.getInfo();
        var uptime=  uptimeService.getUptime();
        var usage=  usageService.getUsage();
        stringRedisTemplate.opsForValue().set(MONITOR_KEY_INFO.concat(HOST), JSONUtil.toJsonStr(info),10, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(MONITOR_KEY_UPTIME.concat(HOST), JSONUtil.toJsonStr(uptime),10, TimeUnit.SECONDS);
        stringRedisTemplate.opsForValue().set(MONITOR_KEY_USAGE.concat(HOST), JSONUtil.toJsonStr(usage),10, TimeUnit.SECONDS);

    }


}
