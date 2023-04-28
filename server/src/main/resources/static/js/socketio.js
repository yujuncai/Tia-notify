

function initSocket() {

  var namespace = $.getUrlParam('namespace');
  var appid= $.getUrlParam('appid');
  var signature = $.getUrlParam('signature');

  if (namespace == '' || appid == ''|| signature == ''|| namespace == undefined   ||appid == undefined   ||signature == undefined) {
    alert('必要的参数为空!');
    return;
  }
  var param= '/'+namespace+'?'+'appid='+appid+'&signature='+signature;
  var url='http://localhost:8080'+param;
  console.log(url)
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


}
(function ($) {
  $.getUrlParam = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
  }
})(jQuery);