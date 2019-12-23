package com.xxx.predictionofcurrentsignal.authentication.social.qq.connect;

import com.xxx.predictionofcurrentsignal.authentication.social.qq.api.QQ;
import com.xxx.predictionofcurrentsignal.authentication.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.security.SocialAuthenticationException;

import java.io.IOException;

public class QQAdapter implements ApiAdapter<QQ> {
    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ api, ConnectionValues connectionValues) {

        QQUserInfo userInfo;
        try {
            userInfo = api.getUserInfo();
        } catch (IOException e) {
            throw new SocialAuthenticationException("获取用户信息失败！");
        }
        connectionValues.setDisplayName(userInfo.getNickname());
        connectionValues.setImageUrl(userInfo.getFigureurl_qq_1());
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
