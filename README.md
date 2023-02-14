# Tia
高性能,可插拔,可集群的IM服务

##技术栈说明

###后端
|技术栈	|版本|
|  ----  | ----  |
|socket.io-client(java-websocket客户端支持) |	1.0.2|
|netty-socketio(socket.io的java语言服务端实现)	|1.7.23|
|jdk	|>= 17|
|springboot	|3.0.0|
|redisson	|3.18.1|
|Jgroups	|5.2.2.Final|


###前端(待补充)

|技术栈	|版本|
|  ----  | ----  |
|socket.io-client(js-websocket客户端支持)	|2.5.0(目前netty-socketio仅支持2.x版本的socket.io-client)|

###文档

1.为什么不用原生的websocket,而选用socketio

[为何选用Socket.io](https://github.com/yujuncai/Tia-im/blob/main/docs/%E4%B8%BA%E4%BD%95%E9%80%89%E7%94%A8%E7%9A%84%E6%98%AFSocket.io.md)

2.集群的思路

[集群思路](https://github.com/yujuncai/Tia-im/blob/main/docs/%E9%9B%86%E7%BE%A4%E6%80%9D%E8%B7%AF.md)

3.系统调优

[系统调优](https://github.com/yujuncai/Tia-im/blob/main/docs/%E7%B3%BB%E7%BB%9F%E8%B0%83%E4%BC%98.md)

***

##规范说明

提交格式

项目格式化代码的方式采用IntelliJ Idea默认的格式化

-  一个功能点作为一次提交...重要

代码提交的说明(commit message) 按照下面给出的些常用格式

-  feat[module]: 新增某一项功能

-  perf[module]: 优化了模块代码或者优化了什么功能

-  fix[module]: 修改了什么bug

-  test[module]: 测试了什么东西

-  del[module]: 删除了某些功能或者无用代码

-  ref[module]: 重命名或者重构了模块

-  doc[module]: 增加了什么文档

更多详情请参考:
[commit-types](https://github.com/pvdlg/conventional-changelog-metahub#commit-types)

***

###主要功能

   - 1.单聊 (完成)

   - 2.群聊 (建组,加入组)

   - 3.图片发送 (完成)

   - 4.离线消息 (未完成)

   - 5.文档输出 (未完成)

   - 6.前端IM页面插件实现(待招募)

   - 7.集群实现(jgroups) (未完成)

   - 8.管理后台 (未完成)

   - 9.交叉认证到第三方系统 (未完成)
