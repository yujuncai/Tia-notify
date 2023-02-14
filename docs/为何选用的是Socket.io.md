
Socket.IO 是一个库，可以在客户端和服务器之间实现 **低延迟**, **双向** 和 **基于事件的** 通信。<br />![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1676339917648-df66058b-2471-4067-b115-0e4ba0dd88cc.png#averageHue=%23000000&clientId=uf33cbc30-28c4-4&from=paste&id=u4ab295b2&name=image.png&originHeight=131&originWidth=840&originalType=url&ratio=1&rotation=0&showTitle=false&size=13706&status=done&style=none&taskId=u46c66028-7d59-41ca-a250-2ad0ea47479&title=) <br /> 
它建立在 [WebSocket](https://fr.wikipedia.org/wiki/WebSocket) 协议之上，并提供额外的保证，例如回退到 HTTP 长轮询或自动重新连接。
## 
Socket.IO除了支持WebSocket通讯协议外，还支持许多种轮询（Polling）机制以及其它实时通信方式，并封装成了通用的接口，
并且在服务端实现了这些实时机制的相应代码。Socket.IO实现的Polling通信机制包括Adobe Flash Socket、AJAX长轮询、AJAX multipart streaming、持久Iframe、JSONP轮询等。Socket.IO能够根据浏览器对通讯机制的支持情况自动地选择最佳的方式来实现网络实时应用

Socket.io是一个可以兼容几乎市面上所有浏览器的Web端即时通讯网络层框架，WebSocket协议只是其支持的数据传输方式的一种，
内置支持了几乎所有Web端长连接方案，而这些方案何时启用全是它自动决定，且上层API对用户是透明的，用户无需处理兼容性问题。<br />
![image.png](https://cdn.nlark.com/yuque/0/2023/png/1608622/1676340532310-254257bd-d160-4b80-b49e-386bb9b11bee.png#averageHue=%23eddec9&clientId=uf33cbc30-28c4-4&from=paste&height=431&id=DnVqJ&name=image.png&originHeight=431&originWidth=1373&originalType=binary&ratio=1&rotation=0&showTitle=false&size=54209&status=done&style=none&taskId=u38869cf7-abc5-429c-b550-0298a2180d1&title=&width=1373) <br />
有几种可用的 Socket.IO 服务器实现：

- JavaScript [https://github.com/socketio/socket.io](https://github.com/socketio/socket.io)
- Java: [https://github.com/mrniko/netty-socketio](https://github.com/mrniko/netty-socketio)
- Java: [https://github.com/trinopoty/socket.io-server-java](https://github.com/trinopoty/socket.io-server-java)
- Python: [https://github.com/miguelgrinberg/python-socketio](https://github.com/miguelgrinberg/python-socketio)

大多数主要语言的客户端实现：

- JavaScript:[https://github.com/socketio/socket.io-client](https://github.com/socketio/socket.io-client)  (可以在浏览器、Node.js 或 React Native 中运行)
- Java: [https://github.com/socketio/socket.io-client-java](https://github.com/socketio/socket.io-client-java)
- C++: [https://github.com/socketio/socket.io-client-cpp](https://github.com/socketio/socket.io-client-cpp)
- Swift: [https://github.com/socketio/socket.io-client-swift](https://github.com/socketio/socket.io-client-swift)
- Dart: [https://github.com/rikulo/socket.io-client-dart](https://github.com/rikulo/socket.io-client-dart)
- Python: [https://github.com/miguelgrinberg/python-socketio](https://github.com/miguelgrinberg/python-socketio)
- .Net: [https://github.com/doghappy/socket.io-client-csharp](https://github.com/doghappy/socket.io-client-csharp)
- Golang: [https://github.com/googollee/go-socket.io](https://github.com/googollee/go-socket.io)
- Rust: [https://github.com/1c3t3a/rust-socketio](https://github.com/1c3t3a/rust-socketio)
- Kotlin: [https://github.com/icerockdev/moko-socket-io](https://github.com/icerockdev/moko-socket-io)


## 我们想做到web,H5,小程序,uniapp通用,就会涉及到不同的设备,不同的系统,不同的浏览器,需要屏蔽底层兼容性问题,socketio是我们的不二选择!
