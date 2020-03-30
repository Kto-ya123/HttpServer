timestr = "00:00:00";
title=document.title;
tid = 0;
pause = 0;
var to;
var bcount;
var tcount;
function time(n) {
    tid=window.setTimeout("time(1)",to);
    today = new Date()
    if(today.getMinutes() < 10) pad = "0"
    else		    pad = "";
    if(today.getSeconds() < 10) pads = "0"
    else  		    pads = "";
    timestr=today.getHours()+":"+pad+today.getMinutes()+":"+pads+today.getSeconds();
    document.title = title+' '+timestr;
    window.clearTimeout(tid);
    tid=window.setTimeout("time()",to);
}
function start(x) {
    f=x;
    to=60;
    time(x);
}
function cleartids() {
    window.clearTimeout(tid);
}
