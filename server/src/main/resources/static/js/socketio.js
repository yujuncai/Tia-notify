

function initSocket() {

  var namespace = $.getUrlParam('namespace');
  var appid= $.getUrlParam('appid');
  var signature = $.getUrlParam('signature');
  var host = $.getUrlParam('host');

  if (namespace == '' || appid == ''|| signature == ''|| namespace == undefined   ||appid == undefined   ||signature == undefined) {
    alert('必要的参数为空!');
    return;
  }
  var param= '/'+namespace+'?'+'appid='+appid+'&signature='+signature;
  var url='http://'+host+':8080'+param;
  console.log(url)
  var token = sessionStorage.getItem("tia-token")
  socket = io(url, {
    reconnectionAttempts: 10,
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

  socket.on('system', function (var1,var2) {

    //console.log("------system------- "+var1+" "+var2.name+" ");
    sendRequest(var1,var2)

  });

   setInterval(function(){
    console.log("每5秒执行一次");
    monitor(host);
  },2000);
}
(function ($) {
  $.getUrlParam = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
  }
})(jQuery);


function monitor(host) {
  socket.emit('monitor', host);
}




