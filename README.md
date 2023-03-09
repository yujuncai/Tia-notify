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

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051430945-a33353ef-2903-458b-b8a6-7b9625822d91.png#averageHue=%2347c4cd&clientId=u119cd82b-b50f-4&from=paste&height=304&id=u8495bd0f&name=image.png&originHeight=795&originWidth=419&originalType=binary&ratio=1&rotation=0&showTitle=false&size=26001&status=done&style=none&taskId=uc3a4b5e1-0e5f-4190-b7d9-50ba4c96e5e&title=&width=160)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051504408-3f8af933-6017-4be6-8fa0-e23a74431f74.png#averageHue=%23fefefe&clientId=u119cd82b-b50f-4&from=paste&height=303&id=uc2bb633f&name=image.png&originHeight=783&originWidth=411&originalType=binary&ratio=1&rotation=0&showTitle=false&size=15490&status=done&style=none&taskId=ua324a1f2-bc3e-40a3-acf7-2d8c3019ce7&title=&width=159)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051674904-fd1a2c7c-19a5-4cfe-9950-83b5efdc2cb9.png#averageHue=%23fefefe&clientId=u119cd82b-b50f-4&from=paste&height=304&id=u06637072&name=image.png&originHeight=672&originWidth=316&originalType=binary&ratio=1&rotation=0&showTitle=false&size=10906&status=done&style=none&taskId=u7d414317-ef9d-4439-8cb9-e4bdc405129&title=&width=143)

#### PC

##### ![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051448560-c425894e-c469-4a71-a9a3-1fa2c1b79eb7.png#averageHue=%2349c8cb&clientId=u119cd82b-b50f-4&from=paste&height=184&id=u87c5b0a6&name=image.png&originHeight=744&originWidth=1936&originalType=binary&ratio=1&rotation=0&showTitle=false&size=29953&status=done&style=none&taskId=ue1942d29-b0e9-41bc-a304-08ba1240559&title=&width=478)

##### ![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051733875-2c0f4c77-f175-4c52-bf0d-d48614a76c91.png#averageHue=%23fefefe&clientId=u119cd82b-b50f-4&from=paste&height=239&id=u6763acfd&name=image.png&originHeight=968&originWidth=1923&originalType=binary&ratio=1&rotation=0&showTitle=false&size=26022&status=done&style=none&taskId=u999369f9-4c55-4a4f-90ee-2b03c06141c&title=&width=475)
