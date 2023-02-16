package org.kikyou.tia.netty.client.shell;

import cn.hutool.json.JSONUtil;
import io.socket.client.Ack;
import io.socket.client.Socket;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.kikyou.tia.netty.chatroom.constant.EventNam;
import org.kikyou.tia.netty.chatroom.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@ShellComponent
@RequiredArgsConstructor
public class MyCommands {

    private final Socket socket;
    @ShellMethod(value = "check")
    public void check(){
       System.out.println("是否连接上 "+socket.connected());

    }

    @ShellMethod(value = "login")
    public void login(){
        System.out.println("发起login");
        User user = new User();
        user.setName("111");
        user.setPassword("111");
        user.setAvatarUrl("www.xxxx.com/static/img/avatar/20180414165815.jpg");
        user.setTime(1L);
        user.setIp("127.0.0.1");
        //socket.emit(EventNam.LOGIN, JSONUtil.parseObj(user));

//ack
       socket.emit(EventNam.LOGIN, new Object[]{JSONUtil.parseObj(user)},(ack)-> {
            System.out.println(ack.length);
            System.out.println(String.valueOf(ack));
            System.out.println(Arrays.stream(ack).count());
        });

    }
    @ShellMethod(value = "register")
    public void register(){
        System.out.println("发起register");
        User user = new User();
        user.setName("111");
        user.setPassword("111");
        user.setAvatarUrl("www.xxxx.com/static/img/avatar/20180414165815.jpg");
        user.setTime(1L);
        user.setIp("127.0.0.1");

        socket.emit(EventNam.REGISTER, JSONUtil.parseObj(user));
    }
    // form:User(id=cf54a807-b829-4cd6-a697-3ed11748b5b4, name=222, password=222, time=1676012342892, avatarUrl=./static/img/avatar/20180414165955.jpg, ip=127.0.0.1, deviceType=pc, roomId=cf54a807-b829-4cd6-a697-3ed11748b5b4, type=user)
    // to:User(id=7713bc0f-8525-446e-8875-740d75c8e591, name=111, password=111, time=1676012352755, avatarUrl=11, ip=127.0.0.1, deviceType=pc, roomId=7713bc0f-8525-446e-8875-740d75c8e591, type=user)
    // content:[微笑]
    // type:text







    @ShellMethod(value = "message2user")
    public void message2user(String currid){
        System.out.println("发起message to user");
        if(!StringUtils.hasText(currid)){
            System.out.println("参数为空!");
        }
        User form = new User();
        form.setName("111");
        form.setPassword("111");
        form.setAvatarUrl("www.xxxx.com/static/img/avatar/20180414165815.jpg");
        form.setTime(1L);
        form.setIp("127.0.0.1");
        form.setType("user");

        User to = new User();
        to.setName("222");
        to.setPassword("222");
        to.setAvatarUrl("www.xxxx.com/static/img/avatar/20180414165815.jpg");
        to.setTime(1L);
        to.setIp("127.0.0.1");
        to.setType("user");
        to.setCurrId(currid);

        socket.emit(EventNam.MESSAGE, JSONUtil.parseObj(form),JSONUtil.parseObj(to),"hello ~~~~user","text");
    }

    @ShellMethod(value = "message2group")
    public void message2group(){
        User form = new User();
        form.setName("111");
        form.setPassword("111");
        form.setAvatarUrl("www.xxxx.com/static/img/avatar/20180414165815.jpg");
        form.setTime(1L);
        form.setIp("127.0.0.1");
        form.setType("user");
      //  to:User(id=group_001, name=群聊天室, password=null, time=null, avatarUrl=static/img/avatar/group-icon.png, ip=null, deviceType=null, roomId=null, type=group) content:1 type:text

        User to = new User();
        to.setId("group_001");
        to.setName("222");
        to.setAvatarUrl("www.xxxx.com/static/img/avatar/20180414165815.jpg");
        to.setTime(1L);
        to.setIp("127.0.0.1");
        to.setType("group");

        socket.emit(EventNam.MESSAGE, JSONUtil.parseObj(form),JSONUtil.parseObj(to),"hello ~~~~group","text");
    }


    @ShellMethod(value = "logout")
    public void logout(){
        System.out.println("发起logout");
        User user = new User();
        socket.emit(EventNam.LOGOUT,  JSONUtil.parseObj(user));

    }

    @ShellMethod(value = "all")
    public void all(){
        System.out.println("获取在线所有用户");
       UserMap.map.forEach((k,v) -> {
           System.out.println("用户名:"+k);
           System.out.println("用户currid:"+v.getCurrId());
           System.out.println("用户:"+v);
           System.out.println("______________________________");
       });

    }
}