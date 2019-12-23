package com.xxx.predictionofcurrentsignal.authentication.social.qq.api;

import java.io.IOException;

public interface QQ {

    //获取用户信息
    QQUserInfo getUserInfo() throws IOException;

}
