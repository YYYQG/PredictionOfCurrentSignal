/**
 *
 */
package com.xxx.predictionofcurrentsignal.authentication.social.weixin.config;

import com.xxx.predictionofcurrentsignal.authentication.social.weixin.connect.WeixinConnectionFactory;
import com.xxx.predictionofcurrentsignal.properties.SecurityProperties;
import com.xxx.predictionofcurrentsignal.properties.WeiXinProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 *
 * @author zhailiang
 */
@Configuration
@ConditionalOnProperty(prefix = "com.security.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        WeiXinProperties weixinConfig = securityProperties.getWeixin();
        connectionFactoryConfigurer.addConnectionFactory( new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret()));
    }

}
