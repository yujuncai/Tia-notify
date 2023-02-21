
var params=window.location.href;
function initSocket() {
 /* http://localhost?appid=987654321&signature=5QUVeOOjIWu4Ul/VqFBh1w==
  http://localhost:8080/?appid=987654321&signature=5QUVeOOjIWu4Ul/VqFBh1w==&namespace=tia-java
  */

  var namespace = $.getUrlParam('namespace');
  var appid= $.getUrlParam('appid');
  var signature = $.getUrlParam('signature');




  if (namespace == '' || appid == ''|| signature == ''|| namespace == undefined   ||appid == undefined   ||signature == undefined) {

    alert('必要的参数为空!');
    return;
  }

  var param= '/'+namespace+'?'+'appid='+appid+'&signature='+signature;
  var url='http://localhost:80'+param;

  var token = sessionStorage.getItem("tia-token")
  socket = io(url, {
    transports: ['websocket', 'polling'],
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



  socket.on('history-message', function (data,messages) {
    mid=data;
    grouphistory=messages;
    console.log("接收到消息:" + data+" "+messages);
  });




  socket.on('registerFail', function (data) {
    ShowFailure(data);
    console.log(data);
  });


  socket.on('registerSuccess', function (data) {

    console.log(data);

  });
  socket.on('loginFail', function (data) {
    ShowFailure(data);
    console.log(data);

    window.location.href =params ;
  });
  socket.on('serverErr', function (data) {
    ShowFailure(data);
    window.location.href = params;
    console.log(data);
  });
  socket.on('loginSuccess', function (data,onlineUsers) {

    token = data.token;
    user = data.user;
    sessionStorage.setItem("tia-token", data.token)
    console.log(data);
    $("#aid").attr("href", "/home/");
    $("#pid").trigger('click');

    console.log(onlineUsers);
    onlines=onlineUsers;

  });
  socket.on('message', function (v1,v2,v3,v4) {

    console.log(v1);
    console.log(v2);
    console.log(v3);
    console.log(v4);
    receiveMessage(v3,v1,v2,v4);
  });
  socket.on('system', function (var1,var2) {

    console.log("------system------- "+var1+" "+var2+" "+ (var2=='join'));

    if(var2=='join'){
      var s=  addOnlineUser(var1);
      $("#messages-wrapper").append(s);
      onlines.push(var1);
    }

    if(var2=='logout'){
     delOnlineUser(var1);
      $.each(onlines, function (index, obj) {
        if (var1.id == obj.id) {
          onlines.splice(index,1);
        }
      });
    }


  });
}
var mid='';
var grouphistory=[];
var onlines=[];
var user = {
  name: '',
  password: '',
  time: '',
  avatarUrl: '',
  ip: '',
  deviceType: '',
  type: '',
  id: '',
  nameSpace: '',
  currId: ''
}

//发送点对点消息
function login() {
  var u = {
    name: '',
    password: '',
    avatarUrl: '',
    deviceType: ''
  }
  var name = $("#name").val();
  var password = $("#password").val()
  if (name == '' || password == '') {
    ShowFailure("用户名/密码不能为空！");
    return
  }
  u.name = name;
  u.password = password;
  u.deviceType = getDeviceType(window.navigator.userAgent);
  u.avatarUrl = "img/20180414165827.jpg";
  socket.emit('login', u);


}


function register() {
  socket.emit('register', "begin");
}

function history(t) {
  socket.emit('history', t);
}

function messagesTo(u,t,c) {
  socket.emit('message', u,t,c,"text");
}


function logout() {
  socket.emit('logout', "");
  sessionStorage.clear();
  window.location.href = params;
}



function getDeviceType(userAgent) {
  userAgent = userAgent.toLowerCase();
  let bIsIpad = userAgent.match(/ipad/i) == "ipad";
  let bIsIphoneOs = userAgent.match(/iphone os/i) == "iphone os";
  let bIsMidp = userAgent.match(/midp/i) == "midp";
  let bIsUc7 = userAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
  let bIsUc = userAgent.match(/ucweb/i) == "ucweb";
  let bIsAndroid = userAgent.match(/android/i) == "android";
  let bIsCE = userAgent.match(/windows ce/i) == "windows ce";
  let bIsWM = userAgent.match(/windows mobile/i) == "windows mobile";
  if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
    return "phone";
  } else {
    return "pc";
  }
}

function ShowTip(tip, type) {
  var $tip = $('#tip');
  if ($tip.length == 0) {
    // 设置样式，也可以定义在css文件中
    $tip = $('<span id="tip" style="position:fixed;top:50px;left: 50%;z-index:9999;height: 55px;padding: 0 30px;line-height: 45px;"></span>');
    $('body').append($tip);
  }
  $tip.stop(true).prop('class', 'alert alert-' + type).text(tip).css('margin-left', -$tip.outerWidth() / 2).fadeIn(500).delay(3000).fadeOut(2000);
}

function ShowMsg(msg) {
  ShowTip(msg, 'info');
}

function ShowSuccess(msg) {
  ShowTip(msg, 'success');
}

function ShowFailure(msg) {
  ShowTip(msg, 'danger');
}

function ShowWarn(msg, $focus, clear) {
  ShowTip(msg, 'warning');
  if ($focus) {
    $focus.focus();
    if (clear) $focus.val('');
  }
  return false;
}

function initprofile() {

  $("#profile-avatarUrl").attr("src", user.avatarUrl);
  $("#profile-nameSpace").text(user.nameSpace);
  $("#profile-name").html(user.name);
  $("#profile-currId").html(user.currId);


}
(function ($) {
  $.getUrlParam = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
  }
})(jQuery);
