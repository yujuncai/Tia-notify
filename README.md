# Tia

小巧,高性能,可插拔,可集群的多租户IM客服

## 技术栈说明

### 后端
| 技术栈 | 版本 |
| --- | --- |
| socket.io-client(java-websocket客户端支持) | 1.0.2 |
| netty-socketio(socket.io的java语言服务端实现) | 1.7.23 |
| jdk | >= 17 |
| springboot | 3.0.0 |
| redisson | 3.18.1 |
| Jgroups | 5.2.2.Final |


### 前端(待补充)
| 技术栈 | 版本 |
| --- | --- |
| socket.io-client(js-websocket客户端支持) | 2.5.0(目前netty-socketio仅支持2.x版本的socket.io-client) |


## 文档

1.为什么不用原生的websocket,而选用socketio

[为何选用Socket.io](https://github.com/yujuncai/Tia-im/blob/main/docs/%E4%B8%BA%E4%BD%95%E9%80%89%E7%94%A8%E7%9A%84%E6%98%AFSocket.io.md)

2.集群的思路

[集群思路](https://github.com/yujuncai/Tia-im/blob/main/docs/%E9%9B%86%E7%BE%A4%E6%80%9D%E8%B7%AF.md)

3.系统调优

[系统调优](https://github.com/yujuncai/Tia-im/blob/main/docs/%E7%B3%BB%E7%BB%9F%E8%B0%83%E4%BC%98.md)

4.EIO协议说明

[eio协议官方说明](https://socket.io/zh-CN/docs/v4/engine-io-protocol/#protocol)


---

## 规范说明

### 提交格式

项目格式化代码的方式采用IntelliJ Idea默认的格式化

- 一个功能点作为一次提交...重要

代码提交的说明(commit message) 按照下面给出的些常用格式

-  feat[module]: 新增某一项功能
-  perf[module]: 优化了模块代码或者优化了什么功能
-  fix[module]: 修改了什么bug
-  test[module]: 测试了什么东西
-  del[module]: 删除了某些功能或者无用代码
-  ref[module]: 重命名或者重构了模块
-  doc[module]: 增加了什么文档

更多详情请参考:<br />[commit-types](https://github.com/pvdlg/conventional-changelog-metahub#commit-types)

---

## 主要功能
    客服场景下的常用功能
- 1.文本消息 (完成)
- 2.图片消息 (完成)
- 3.消息回执 (未完成)
- 4.离线消息 (未完成)
- 5.文档输出 (未完成)
- 6.前端IM页面插件实现(待招募)
- 7.集群实现(jgroups) (未完成)
- 8.管理后台 (待招募)
- 8.多渠道接入 (未完成) 
- 9.多租户 (完成中) 


## 页面展示

#### H5
  
  客户端demo源码地址:<br />[客户端demo](https://github.com/yujuncai/Tia-web-demo)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051430945-a33353ef-2903-458b-b8a6-7b9625822d91.png)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051504408-3f8af933-6017-4be6-8fa0-e23a74431f74.png)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051674904-fd1a2c7c-19a5-4cfe-9950-83b5efdc2cb9.png)

#### PC

##### ![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1680076640606-48cff368-f70f-44cf-91e8-f3a2ecffa562.png)

##### ![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1680076663702-43d0671b-508c-4710-92da-a465d8c6e94a.png)
