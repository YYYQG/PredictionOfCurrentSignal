(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[8],{"0evu":function(e,t,a){e.exports={main:"antd-pro-pages-account-settings-info-main",leftmenu:"antd-pro-pages-account-settings-info-leftmenu",right:"antd-pro-pages-account-settings-info-right",title:"antd-pro-pages-account-settings-info-title"}},JE8d:function(e,t,a){"use strict";a.r(t);a("bbsP");var n,r,i,s,o=a("/wGt"),c=(a("14J3"),a("BMrR")),l=(a("+L6B"),a("2/Rp")),u=(a("jCWc"),a("kPKH")),p=(a("Mwp2"),a("VXEj")),m=a("p0pE"),d=a.n(m),g=(a("Pwec"),a("CtXQ")),v=a("2Taf"),f=a.n(v),h=a("vZ4D"),b=a.n(h),y=a("l4Ni"),E=a.n(y),w=a("ujKo"),M=a.n(w),k=a("MhPg"),O=a.n(k),C=(a("5NDa"),a("5rEg")),j=(a("y8nQ"),a("Vl3Y")),I=a("q1tI"),N=a.n(I),D=a("Y2fQ"),U=a("MuoO"),V=a("WLrp"),x=a.n(V),S=j["a"].Item,P=C["a"].Group,z=(n=Object(U["connect"])(function(e){var t=e.loading,a=e.user;return{currentUser:a.currentUser,visible:a.visible,url:a.url,weixin:a.weixin,currentUserLoading:t.effects["user/fetchCurrent"]}}),r=j["a"].create(),n(i=r((s=function(e){function t(){var e,a;f()(this,t);for(var n=arguments.length,r=new Array(n),i=0;i<n;i++)r[i]=arguments[i];return a=E()(this,(e=M()(t)).call.apply(e,[this].concat(r))),a.state={count:0},a.showDrawer=function(){var e=a.props.dispatch;e({type:"user/changeVisible",payload:{visible:!0}})},a.onClick=function(){var e=a.props.dispatch;e({type:"user/getUrl",payload:{}})},a.onClose=function(){var e=a.props.dispatch;e({type:"user/changeVisible",payload:{visible:!1}})},a.getData=function(){var e,t,n,r=a.props,i=r.currentUser,s=r.weixin;r.url;return""!==i.tel?(e="\u5df2\u7ed1\u5b9a\u624b\u673a\u53f7",t=i.tel,n="\u4fee\u6539"):(e="\u7ed1\u5b9a\u624b\u673a\u8d26\u53f7",t="\u5f53\u524d\u4e3a\u7ed1\u5b9a\u624b\u673a\u53f7\uff01",n="\u7ed1\u5b9a"),s?("\u5df2\u7ed1\u5b9a\u5fae\u4fe1\u53f7","",""):("\u7ed1\u5b9a\u5fae\u4fe1\u8d26\u53f7","\u5f53\u524d\u4e3a\u7ed1\u5b9a\u5fae\u4fe1\uff01","\u7ed1\u5b9a"),[{title:e,description:t,actions:[N.a.createElement("a",{onClick:a.showDrawer},n)],avatar:N.a.createElement(g["a"],{type:"phone",className:"phone"})}]},a.onGetCaptcha=function(){var e=a.props,t=e.dispatch,n=e.form.validateFields;n(["mobile"],{},function(e,n){if(!e){var r=119;a.setState({count:r}),a.interval=setInterval(function(){r-=1,a.setState({count:r}),0===r&&clearInterval(a.interval)},1e3),t({type:"login/getCaptcha",payload:n.mobile}),console.log("Received values of form: ",n)}})},a.handleSubmit=function(e){e.preventDefault();var t=a.props,n=t.dispatch,r=t.form.validateFields,i=t.currentUser;r(function(e,t){e||n({type:"user/bindTel",payload:d()({},t,{userId:i.userId})})})},a}return O()(t,e),b()(t,[{key:"componentWillMount",value:function(){var e=this.props.dispatch;e({type:"user/weiXinStatus",payload:{}}),e({type:"user/getUrl",payload:{}})}},{key:"render",value:function(){var e=this.props,t=e.form.getFieldDecorator,a=e.visible,n=this.state.count;return N.a.createElement(I["Fragment"],null,N.a.createElement(p["a"],{itemLayout:"horizontal",dataSource:this.getData(),renderItem:function(e){return N.a.createElement(p["a"].Item,{actions:e.actions},N.a.createElement(p["a"].Item.Meta,{avatar:e.avatar,title:e.title,description:e.description}))}}),N.a.createElement("div",null,N.a.createElement(o["a"],{title:"\u7ed1\u5b9a\u624b\u673a\u53f7",height:300,onClose:this.onClose,visible:a,placement:"bottom"},N.a.createElement("div",{style:{width:"30%",margin:"auto"}},N.a.createElement(j["a"],{onSubmit:this.handleSubmit},N.a.createElement(S,null,N.a.createElement(P,{compact:!0},t("mobile",{rules:[{required:!0,message:Object(D["formatMessage"])({id:"validation.phone-number.required"})},{pattern:/^\d{11}$/,message:Object(D["formatMessage"])({id:"validation.phone-number.wrong-format"})}]})(N.a.createElement(C["a"],{size:"large",placeholder:Object(D["formatMessage"])({id:"form.phone-number.placeholder"})})))),N.a.createElement(S,null,N.a.createElement(c["a"],{gutter:8},N.a.createElement(u["a"],{span:16},t("smsCode",{rules:[{required:!0,message:Object(D["formatMessage"])({id:"validation.verification-code.required"})}]})(N.a.createElement(C["a"],{size:"large",placeholder:Object(D["formatMessage"])({id:"form.verification-code.placeholder"})}))),N.a.createElement(u["a"],{span:8},N.a.createElement(l["a"],{size:"large",disabled:n,className:x.a.getCaptcha,onClick:this.onGetCaptcha,style:{width:"100%"}},n?"".concat(n," s"):Object(D["formatMessage"])({id:"app.register.get-verification-code"}))))),N.a.createElement(S,null,N.a.createElement(c["a"],null,N.a.createElement("div",{style:{borderTop:"1px solid #e9e9e9",padding:"10px 16px",background:"#fff",textAlign:"center"}},N.a.createElement(u["a"],{span:9},N.a.createElement(l["a"],{htmlType:"submit",style:{width:"200px"},type:"primary",size:"large"},"\u7ed1\u5b9a")),N.a.createElement(u["a"],{span:6}),N.a.createElement(u["a"],{span:9},N.a.createElement(l["a"],{onClick:this.onClose,style:{width:"200px"},size:"large"},"\u53d6\u6d88"))))))))))}}]),t}(I["PureComponent"]),i=s))||i)||i);t["default"]=z},"N01/":function(e,t,a){"use strict";a.r(t);var n,r,i,s=a("2Taf"),o=a.n(s),c=a("vZ4D"),l=a.n(c),u=a("l4Ni"),p=a.n(u),m=a("ujKo"),d=a.n(m),g=a("MhPg"),v=a.n(g),f=(a("lUTK"),a("BvKs")),h=a("q1tI"),b=a.n(h),y=a("MuoO"),E=a("usdK"),w=a("Y2fQ"),M=a("v99g"),k=a("0evu"),O=a.n(k),C=f["b"].Item,j=(n=Object(y["connect"])(function(e){var t=e.user;return{currentUser:t.currentUser}}),n((i=function(e){function t(e){var a;o()(this,t),a=p()(this,d()(t).call(this,e)),a.getmenu=function(){var e=a.state.menuMap;return Object.keys(e).map(function(t){return b.a.createElement(C,{key:t},e[t])})},a.getRightTitle=function(){var e=a.state,t=e.selectKey,n=e.menuMap;return n[t]},a.selectKey=function(e){var t=e.key;E["a"].push("/account/settings/".concat(t)),a.setState({selectKey:t})},a.resize=function(){a.main&&requestAnimationFrame(function(){var e="inline",t=a.main.offsetWidth;a.main.offsetWidth<641&&t>400&&(e="horizontal"),window.innerWidth<768&&t>400&&(e="horizontal"),a.setState({mode:e})})};var n=e.match,r=e.location,i={base:b.a.createElement(w["FormattedMessage"],{id:"app.settings.menuMap.basic",defaultMessage:"Basic Settings"}),binding:b.a.createElement(w["FormattedMessage"],{id:"app.settings.menuMap.binding",defaultMessage:"Account Binding"})},s=r.pathname.replace("".concat(n.path,"/"),"");return a.state={mode:"inline",menuMap:i,selectKey:i[s]?s:"base"},a}return v()(t,e),l()(t,[{key:"componentDidMount",value:function(){window.addEventListener("resize",this.resize),this.resize()}},{key:"componentWillUnmount",value:function(){window.removeEventListener("resize",this.resize)}},{key:"render",value:function(){var e=this,t=this.props,a=t.children,n=t.currentUser;if(!n.username)return"";var r=this.state,i=r.mode,s=r.selectKey;return b.a.createElement(M["a"],null,b.a.createElement("div",{className:O.a.main,ref:function(t){e.main=t}},b.a.createElement("div",{className:O.a.leftmenu},b.a.createElement(f["b"],{mode:i,selectedKeys:[s],onClick:this.selectKey},this.getmenu())),b.a.createElement("div",{className:O.a.right},b.a.createElement("div",{className:O.a.title},this.getRightTitle()),a)))}}],[{key:"getDerivedStateFromProps",value:function(e,t){var a=e.match,n=e.location,r=n.pathname.replace("".concat(a.path,"/"),"");return r=t.menuMap[r]?r:"base",r!==t.selectKey?{selectKey:r}:null}}]),t}(h["Component"]),r=i))||r);t["default"]=j},VGLV:function(e,t,a){e.exports={row:"antd-pro-pages-account-settings-geographic-view-row",item:"antd-pro-pages-account-settings-geographic-view-item"}},WLrp:function(e,t,a){e.exports={main:"antd-pro-pages-account-settings-binding-view-main",getCaptcha:"antd-pro-pages-account-settings-binding-view-getCaptcha",submit:"antd-pro-pages-account-settings-binding-view-submit",login:"antd-pro-pages-account-settings-binding-view-login",error:"antd-pro-pages-account-settings-binding-view-error",success:"antd-pro-pages-account-settings-binding-view-success",warning:"antd-pro-pages-account-settings-binding-view-warning","progress-pass":"antd-pro-pages-account-settings-binding-view-progress-pass",progress:"antd-pro-pages-account-settings-binding-view-progress"}},dzOB:function(e,t,a){"use strict";a.r(t);a("+L6B");var n,r,i,s,o,c,l,u=a("2/Rp"),p=(a("5NDa"),a("5rEg")),m=a("p0pE"),d=a.n(m),g=a("2Taf"),v=a.n(g),f=a("vZ4D"),h=a.n(f),b=a("l4Ni"),y=a.n(b),E=a("ujKo"),w=a.n(E),M=a("MhPg"),k=a.n(M),O=(a("OaEy"),a("2fM7")),C=(a("y8nQ"),a("Vl3Y")),j=a("q1tI"),I=a.n(j),N=a("Y2fQ"),D=a("MuoO"),U=a("gJV7"),V=a.n(U),x=(a("T2oS"),a("W9HT")),S=a("VGLV"),P=a.n(S),z=O["a"].Option,F={label:"",key:""},K=(n=Object(D["connect"])(function(e){var t=e.geographic,a=t.province,n=t.isLoading,r=t.city;return{province:a,city:r,isLoading:n}}),n((i=function(e){function t(){var e,a;v()(this,t);for(var n=arguments.length,r=new Array(n),i=0;i<n;i++)r[i]=arguments[i];return a=y()(this,(e=w()(t)).call.apply(e,[this].concat(r))),a.componentDidMount=function(){var e=a.props.dispatch;e({type:"geographic/fetchProvince"})},a.getCityOption=function(){var e=a.props.city;return a.getOption(e)},a.getOption=function(e){return!e||e.length<1?I.a.createElement(z,{key:0,value:0},"\u6ca1\u6709\u627e\u5230\u9009\u9879"):e.map(function(e){return I.a.createElement(z,{key:e.id,value:e.id},e.name)})},a.selectProvinceItem=function(e){var t=a.props,n=t.dispatch,r=t.onChange;n({type:"geographic/fetchCity",payload:e.key}),r({province:e,city:F})},a.selectCityItem=function(e){var t=a.props,n=t.value,r=t.onChange;r({province:n.province,city:e})},a}return k()(t,e),h()(t,[{key:"componentDidUpdate",value:function(e){var t=this.props,a=t.dispatch,n=t.value;!e.value&&n&&n.province&&a({type:"geographic/fetchCity",payload:n.province.key})}},{key:"getProvinceOption",value:function(){var e=this.props.province;return this.getOption(e)}},{key:"conversionObject",value:function(){var e=this.props.value;if(!e)return{province:F,city:F};var t=e.province,a=e.city;return{province:t||F,city:a||F}}},{key:"render",value:function(){var e=this.conversionObject(),t=e.province,a=e.city,n=!1;return I.a.createElement(x["a"],{spinning:n,wrapperClassName:P.a.row},I.a.createElement(O["a"],{className:P.a.item,value:t,labelInValue:!0,showSearch:!0,onSelect:this.selectProvinceItem},this.getProvinceOption()),I.a.createElement(O["a"],{className:P.a.item,value:a,labelInValue:!0,showSearch:!0,onSelect:this.selectCityItem},this.getCityOption()))}}]),t}(j["PureComponent"]),r=i))||r),q=K,L=a("uy6Z"),T=a.n(L),A=function(e){function t(){return v()(this,t),y()(this,w()(t).apply(this,arguments))}return k()(t,e),h()(t,[{key:"render",value:function(){var e=this.props,t=e.value,a=e.onChange;return I.a.createElement(j["Fragment"],null,I.a.createElement(p["a"],{className:T.a.phone_number,onChange:function(e){a("".concat(e.target.value))},value:t,disabled:!0}))}}]),t}(j["PureComponent"]),R=A,W=a("WWNI"),B=a("Audq"),_=C["a"].Item,G=(O["a"].Option,function(e){var t=e.avatar;return I.a.createElement(j["Fragment"],null,I.a.createElement("div",{className:V.a.avatar_title},I.a.createElement(N["FormattedMessage"],{id:"app.settings.basic.avatar",defaultMessage:"Avatar"})),I.a.createElement("div",{className:V.a.avatar},I.a.createElement("img",{src:t,alt:"avatar"})))}),J=function(e,t,a){var n=t.province,r=t.city;n.key||a("Please input your province!"),r.key||a("Please input your city!"),a()},Q=function(e,t,a){t||a("Please input your phone number!"),a()},Z=(s=Object(D["connect"])(function(e){var t=e.user;return{currentUser:t.currentUser}}),o=C["a"].create(),s(c=o((l=function(e){function t(){var e,a;v()(this,t);for(var n=arguments.length,r=new Array(n),i=0;i<n;i++)r[i]=arguments[i];return a=y()(this,(e=w()(t)).call.apply(e,[this].concat(r))),a.setBaseInfo=function(){var e=a.props,t=e.currentUser,n=e.form;Object.keys(n.getFieldsValue()).forEach(function(e){var a={};if("address"===e&&null!==t[e]){var r=t[e].indexOf("\u7701"),i=t[e].substring(0,r+1),s=t[e].substring(r+1),o=B.find(function(e){return e.name===i}).id,c=W[o].find(function(e){return e.name===s}).id;a[e]={province:{label:i,key:o},city:{label:s,key:c}}}else a[e]=t[e]||null;n.setFieldsValue(a)})},a.handleSubmit=function(e){e.preventDefault();var t=a.props,n=t.dispatch,r=t.form.validateFields;r(function(e,t){e||(n({type:"user/updateUser",payload:d()({},t,{address:"".concat(t.address.province.label).concat(t.address.city.label)})}),console.log("Received values of form: ",t))})},a.getViewDom=function(e){a.view=e},a}return k()(t,e),h()(t,[{key:"componentDidMount",value:function(){this.setBaseInfo()}},{key:"getAvatarURL",value:function(){var e=this.props.currentUser;if(e.headerImg)return e.headerImg;var t="https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png";return t}},{key:"render",value:function(){var e=this.props.form.getFieldDecorator;return I.a.createElement("div",{className:V.a.baseView,ref:this.getViewDom},I.a.createElement("div",{className:V.a.left},I.a.createElement(C["a"],{layout:"vertical",onSubmit:this.handleSubmit,hideRequiredMark:!0},I.a.createElement(_,{label:Object(N["formatMessage"])({id:"app.settings.basic.email"})},e("email",{rules:[{required:!0,message:Object(N["formatMessage"])({id:"app.settings.basic.email-message"},{})}]})(I.a.createElement(p["a"],null))),I.a.createElement(_,{label:Object(N["formatMessage"])({id:"app.settings.basic.nickname"})},e("username",{rules:[{required:!0,message:Object(N["formatMessage"])({id:"app.settings.basic.nickname-message"},{})}]})(I.a.createElement(p["a"],{disabled:!0}))),I.a.createElement(_,{label:Object(N["formatMessage"])({id:"app.settings.basic.profile"})},e("userDescribe",{rules:[{required:!0,message:Object(N["formatMessage"])({id:"app.settings.basic.profile-message"},{})}]})(I.a.createElement(p["a"].TextArea,{placeholder:Object(N["formatMessage"])({id:"app.settings.basic.profile-placeholder"}),rows:4}))),I.a.createElement(_,{label:Object(N["formatMessage"])({id:"app.settings.basic.geographic"})},e("address",{rules:[{required:!0,message:Object(N["formatMessage"])({id:"app.settings.basic.geographic-message"},{})},{validator:J}]})(I.a.createElement(q,null))),I.a.createElement(_,{label:Object(N["formatMessage"])({id:"app.settings.basic.phone"})},e("tel",{rules:[{required:!0,message:Object(N["formatMessage"])({id:"app.settings.basic.phone-message"},{})},{validator:Q}]})(I.a.createElement(R,null))),I.a.createElement(u["a"],{type:"primary",htmlType:"submit"},I.a.createElement(N["FormattedMessage"],{id:"app.settings.basic.update",defaultMessage:"Update Information"})))),I.a.createElement("div",{className:V.a.right},I.a.createElement(G,{avatar:this.getAvatarURL()})))}}]),t}(j["Component"]),c=l))||c)||c);t["default"]=Z},gJV7:function(e,t,a){e.exports={baseView:"antd-pro-pages-account-settings-base-view-baseView",left:"antd-pro-pages-account-settings-base-view-left",right:"antd-pro-pages-account-settings-base-view-right",avatar_title:"antd-pro-pages-account-settings-base-view-avatar_title",avatar:"antd-pro-pages-account-settings-base-view-avatar",button_view:"antd-pro-pages-account-settings-base-view-button_view"}},uy6Z:function(e,t,a){e.exports={area_code:"antd-pro-pages-account-settings-phone-view-area_code",phone_number:"antd-pro-pages-account-settings-phone-view-phone_number"}}}]);