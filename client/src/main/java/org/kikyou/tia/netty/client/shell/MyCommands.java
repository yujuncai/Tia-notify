package org.kikyou.tia.netty.client.shell;

import cn.hutool.json.JSONUtil;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.kikyou.tia.netty.notify.constant.EventNam;
import org.kikyou.tia.netty.notify.models.User;
import org.kikyou.tia.netty.notify.utils.MySecureUtil;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class MyCommands {

    private final Socket socket;

    @ShellMethod(value = "check")
    public void check() {
        log.info("是否连接上 " + socket.connected());

    }

    @ShellMethod(value = "login")
    public void login() {
        log.info("发起login");
        User user = new User();
        user.setName("111");
        user.setPassword("111");
        user.setAvatarUrl("img/20180414165827.jpg");
        user.setTime(1L);
        user.setIp("127.0.0.1");
        //socket.emit(EventNam.LOGIN, JSONUtil.parseObj(user));

//ack
        socket.emit(EventNam.LOGIN, new Object[]{JSONUtil.parseObj(user)}, (ack) -> {
            log.info(ack.length + "");
            log.info(String.valueOf(ack));
            log.info(Arrays.stream(ack).count() + "");
        });

    }

    @ShellMethod(value = "register")
    public void register() {
        log.info("发起register");
        User user = new User();
        user.setName("111");
        user.setPassword("111");
        user.setAvatarUrl("img/20180414165827.jpg");
        user.setTime(1L);
        user.setIp("127.0.0.1");

        socket.emit(EventNam.REGISTER, JSONUtil.parseObj(user));
    }


    ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(60);

    @SneakyThrows
    @ShellMethod(value = "register50")
    public void register50() {
        for (int i = 0; i < 50; i++) {

            int finalI = i;
            Runnable runnable = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    try {
                        IO.Options options = new IO.Options();
                        String signature = MySecureUtil.aesEncrypt("jvZJhHtp3vOVmpool6QlMw==", "/tia-java");
                        String s = "http://localhost".concat("?").concat("appid=987654321").concat("&").concat("signature=" + signature);
                        Manager m = new Manager(new URI(s));
                        Socket socket = m.socket("/tia-java");
                        Socket connect = socket.connect();
                        log.info("发起register");
                        User user = new User();
                        user.setName(String.valueOf(finalI));
                        user.setPassword(String.valueOf(finalI));
                        user.setAvatarUrl("img/20180414165827.jpg");
                        user.setTime(1L);
                        user.setIp("127.0.0.1");
                        connect.emit(EventNam.REGISTER, JSONUtil.parseObj(user));
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info(Thread.currentThread().getName());
                }
            };
            // 将任务交给线程池管理
            newFixedThreadPool.execute(runnable);
        }

    }

    //模拟50个用户登陆
    @SneakyThrows
    @ShellMethod(value = "login50")
    public void login50() {

        for (int i = 0; i < 50; i++) {

            int finalI = i;
            Runnable runnable = new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    try {
                        IO.Options options = new IO.Options();
                        String signature = MySecureUtil.aesEncrypt("jvZJhHtp3vOVmpool6QlMw==", "/tia-java");
                        String s = "http://localhost".concat("?").concat("appid=987654321").concat("&").concat("signature=" + signature);
                        Manager m = new Manager(new URI(s));
                        Socket socket = m.socket("/tia-java");
                        Socket connect = socket.connect();
                        log.info("发起login");
                        User user = new User();
                        user.setName(String.valueOf(finalI));
                        user.setPassword(String.valueOf(finalI));
                        user.setAvatarUrl("img/20180414165827.jpg");
                        user.setTime(1L);
                        user.setIp("127.0.0.1");
                        connect.emit(EventNam.LOGIN, JSONUtil.parseObj(user));
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info(Thread.currentThread().getName());
                }
            };
            // 将任务交给线程池管理
            newFixedThreadPool.execute(runnable);
        }
    }


    @ShellMethod(value = "message2user")
    public void message2user(String currid) {
        log.info("发起message to user");
        if (!StringUtils.hasText(currid)) {
            log.info("参数为空!");
        }
        User form = new User();
        form.setId("111111111");
        form.setName("111");
        form.setPassword("111");
        form.setAvatarUrl("img/20180414165827.jpg");
        form.setTime(1L);
        form.setIp("127.0.0.1");
        form.setType("user");

        User to = new User();
        to.setId("2222222");
        to.setName("222");
        to.setPassword("222");
        to.setAvatarUrl("img/20180414165827.jpg");
        to.setTime(1L);
        to.setIp("127.0.0.1");
        to.setType("user");
        to.setCurrId(currid);

        socket.emit(EventNam.MESSAGE, JSONUtil.parseObj(form), JSONUtil.parseObj(to), "hello ~~~~user", "text");
    }

    @ShellMethod(value = "message2group")
    public void message2group() {
        User form = new User();
        form.setName("111");
        form.setPassword("111");
        form.setAvatarUrl("img/20180414165827.jpg");
        form.setTime(1L);
        form.setIp("127.0.0.1");
        form.setType("user");
        //  to:User(id=group_001, name=群聊天室, password=null, time=null, avatarUrl=static/img/avatar/group-icon.png, ip=null, deviceType=null, roomId=null, type=group) content:1 type:text

        User to = new User();
        to.setId("group_001");
        to.setName("222");
        to.setAvatarUrl("img/20180414165827.jpg");
        to.setTime(1L);
        to.setIp("127.0.0.1");
        to.setType("group");

        socket.emit(EventNam.MESSAGE, JSONUtil.parseObj(form), JSONUtil.parseObj(to), "hello ~~~~group", "text");
    }


    @ShellMethod(value = "logout")
    public void logout() {
        log.info("发起logout");
        User user = new User();
        socket.emit(EventNam.LOGOUT, JSONUtil.parseObj(user));

    }

    @ShellMethod(value = "all")
    public void all() {
        log.info("获取在线所有用户");
        UserMap.map.forEach((k, v) -> {
            log.info("用户名:" + k);
            log.info("用户currid:" + v.getCurrId());
            log.info("用户:" + v);
            log.info("______________________________");
        });

    }
}