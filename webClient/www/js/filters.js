function friendlyTime(value)  {

  if(value){

  var time=new Date().getTime();
  time=parseInt((time-value)/1000);
  //存储转换值
  var s;
  if(time<60*3){//三分钟内
    return '刚刚';
  }else if((time<60*60)&&(time>=60*3)){
    //超过十分钟少于1小时
    s = Math.floor(time/60);
    return  s+"分钟前";
  }else if((time<60*60*24)&&(time>=60*60)){
    //超过1小时少于24小时
    s = Math.floor(time/60/60);
    return  s+"小时前";
  }else if((time<60*60*24*3)&&(time>=60*60*24)){
    //超过1天少于3天内
    s = Math.floor(time/60/60/24);
    return s+"天前";
  }else{
    //超过3天
    var date= new Date(value);
    return date.getFullYear()+"."+(date.getMonth()+1)+"."+date.getDate();
  }}else{
    return '';
  }
}
function formatTime (value)  {
  var date=new Date(value);
  var year=date.getFullYear();
  var month=date.getMonth()+1;
  var day=date.getDate();
  var hour=date.getHours()>9?date.getHours():("0"+date.getHours());
  var minutes=date.getMinutes()>9?date.getMinutes():("0"+date.getMinutes());
  var seconds=date.getSeconds()>9?date.getSeconds():("0"+date.getSeconds());
  return year+"."+month+"."+day+" "+hour+":"+minutes+":"+seconds;
}
