(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[13],{hZ0M:function(e,t,r){"use strict";r.r(t);var a=r("d6i3"),n=r.n(a),c=(r("miYZ"),r("tsqr")),u=r("p0pE"),s=r.n(u),p=r("URIt");t["default"]={namespace:"predict",state:{file:{},fileList:[],realOutput:[],error:{},total:0,errorNum:0,predictIsOk:!1},effects:{predict:n.a.mark(function e(t,r){var a,u,o,i;return n.a.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return a=t.payload,u=r.call,o=r.put,e.next=4,u(p["b"],a);case 4:if(i=e.sent,0!==i.code){e.next=10;break}return e.next=8,o({type:"saveOutput",payload:s()({},i.data,{predictIsOk:!1,errorNum:Object.keys(i.data.error).length,total:i.data.realOutput.length})});case 8:e.next=11;break;case 10:c["a"].error("\u6587\u4ef6\u683c\u5f0f\u51fa\u9519\uff01");case 11:case"end":return e.stop()}},e)})},reducers:{saveFile:function(e,t){var r=t.payload;return s()({},e,r)},saveOutput:function(e,t){var r=t.payload;return s()({},e,r)},changePredictIsOk:function(e,t){var r=t.payload;return s()({},e,r)}}}}}]);