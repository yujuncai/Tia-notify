function initSocket(){
  var token = sessionStorage.getItem("tia-token")
  socket = io('http://localhost:80/tia-java?appid=987654321&signature=5QUVeOOjIWu4Ul/VqFBh1w==',{
    transports: ['websocket','polling'],
    transportOptions: {
      polling: {
        extraHeaders: {
          'token': token || ''
        }
      }
    }
  });
  socket.on('connect', function () {
    console.log('socket连接成功');
  });
  socket.on('disconnect', function () {
    console.log('socket断开连接');
  });

  socket.on('history-message', function (data) {
    console.log("接收到消息:" + data);
  });
  socket.on('registerFail', function (data) {

    console.log(data);
  });

  socket.on('registerFail', function (data) {

    console.log(data);
  });
  socket.on('registerSuccess', function (data) {

    console.log(data);
  });
  socket.on('loginFail', function (data) {

    console.log(data);
  });
  socket.on('serverErr', function (data) {

    console.log(data);
  });
  socket.on('loginSuccess', function (data) {

    console.log(data);
  });
  socket.on('message', function (data) {

    console.log(data);
  });
  socket.on('system', function (data) {
    console.log(data);

  });
}
var user = {
  name : '',
  id : '',
  password :'' ,
  time : '',
  avatarUrl :'',
  ip : '',
  deviceType : '',
  type : ''
}
//发送点对点消息
function login(){

  var name= $("#name").val();
  var password= $("#password").val()

  if(!name){
    alert("name err")
  }
  if(!password){
    alert("password err")
  }
  user.name=name;
  user.password=password;

  socket.emit('login', JSON.stringify(user));
}
//触发无限推送
function register(){
  socket.emit('register', "begin");
}



function logout(){

  socket.emit('logout', "room1");
}
//发送房间消息
function message(){
  socket.emit('testRoom', "testRoomData");
}
function getDeviceType(userAgent){
  userAgent=userAgent.toLowerCase();
  let bIsIpad = userAgent.match(/ipad/i)== "ipad";
  let bIsIphoneOs = userAgent.match(/iphone os/i)== "iphone os";
  let bIsMidp = userAgent.match(/midp/i)== "midp";
  let bIsUc7 = userAgent.match(/rv:1.2.3.4/i)== "rv:1.2.3.4";
  let bIsUc = userAgent.match(/ucweb/i)== "ucweb";
  let bIsAndroid = userAgent.match(/android/i)== "android";
  let bIsCE = userAgent.match(/windows ce/i)== "windows ce";
  let bIsWM = userAgent.match(/windows mobile/i)== "windows mobile";
  if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
    return "phone";
  } else {
    return "pc";
  }}
