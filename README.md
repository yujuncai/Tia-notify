[中文说明](https://github.com/yujuncai/Tia-notify/blob/main/README_CN.md)

# Tia   

A clusterable multi-namespace long connection service based on Netty-socketIO

## Technology Stack

### Backend
| Technology | Version |
| --- | --- |
| socket.io-client (Java WebSocket client support) | 1.0.2 |
| netty-socketio (Socket.IO Java server implementation) | 2.0.6 |
| jdk | >= 17 |
| springboot | 3.0.0 |
| redisson | 3.24.3 |
| Jgroups | 5.3.0.Final |
| jprotobuf | 2.4.20 |

### Frontend (TBD)
| Technology | Version |
| --- | --- |
| socket.io-client (JS WebSocket client support)| 2.5.0(2.5.0 (currently netty-socketio only supports socket.io-client v2.x)) |


## Documentation

1.Why use Socket.IO instead of native WebSocket

[为何选用Socket.io](https://github.com/yujuncai/Tia-notify/blob/main/docs/%E4%B8%BA%E4%BD%95%E9%80%89%E7%94%A8%E7%9A%84%E6%98%AFSocket.io.md)

2.Clustering Approach

[集群思路](https://github.com/yujuncai/Tia-notify/blob/main/docs/%E9%9B%86%E7%BE%A4%E6%80%9D%E8%B7%AF.md)

3.System Optimization

[系统调优](https://github.com/yujuncai/Tia-notify/blob/main/docs/%E7%B3%BB%E7%BB%9F%E8%B0%83%E4%BC%98.md)

4.EIO Protocol Specification

[eio协议官方说明](https://socket.io/zh-CN/docs/v4/engine-io-protocol/#protocol)


---

## Specifications

### Commit Format

The project uses IntelliJ Idea's default formatting

- Each feature should be one commit ... This is important

The commit messages follow these common formats:

-  feat[module]: Add some feature
-  perf[module]: Optimize module code or some feature
-  fix[module]: Fix some bug
-  test[module]: Test something
-  del[module]: Delete some feature or useless code
-  ref[module]: Rename or refactor module
-  doc[module]: Add some docs

See more details::<br />[commit-types](https://github.com/pvdlg/conventional-changelog-metahub#commit-types)

---



## UI Showcase

#### H5

Mobile client demo source code:<br />[web client demo](https://github.com/yujuncai/Tia-web-demo)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051430945-a33353ef-2903-458b-b8a6-7b9625822d91.png)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051504408-3f8af933-6017-4be6-8fa0-e23a74431f74.png)

![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1677051674904-fd1a2c7c-19a5-4cfe-9950-83b5efdc2cb9.png)

#### PC

##### ![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1680076640606-48cff368-f70f-44cf-91e8-f3a2ecffa562.png)

##### ![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1680076663702-43d0671b-508c-4710-92da-a465d8c6e94a.png)


##### ![image.png](https://cdn.nlark.com/yuque/0/2023/jpeg/1608622/1685603430721-63291597-baf4-4884-8c8a-21ea91f48bb9.jpeg)



## Acknowledgements


The frontend code is really poorly written! Lots of code are copied from other projects, thanks to these projects:

https://github.com/auntvt/Timo         (Admin UI)
https://github.com/Rudolf-Barbu/Ward   (Monitoring UI)
https://github.com/nguyencse/HeyU      (Client UI)

## Quick Start

1.Check server config, make sure cluster is in standalone mode

2.Import SQL file. My local is MySQL 5.7, adjust types if 8.0

3.Configure Redis and MySQL, import SQL

4.Start server 

5.Admin portal http://localhost/web/login default port 80, Netty default 8080 To try client demo, 
put https://github.com/yujuncai/Tia-web-demo code into resources/static/webDemo folder

PS: Frontend is just for demo, cannot be used in production. Code quality is really bad. Any frontend experts willing to help improve it?