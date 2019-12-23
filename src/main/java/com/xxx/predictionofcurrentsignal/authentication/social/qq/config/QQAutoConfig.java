package com.xxx.predictionofcurrentsignal.authentication.social.qq.config;

import com.xxx.predictionofcurrentsignal.authentication.social.qq.connect.QQConnectionFactory;
import com.xxx.predictionofcurrentsignal.properties.QQProperties;
import com.xxx.predictionofcurrentsignal.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;


@Configuration
@ConditionalOnProperty(prefix = "com.security.qq",name = "app-id")
@Slf4j
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        QQProperties qqProperties = securityProperties.getQq();
        log.info("QQProperties",String.valueOf(qqProperties));
        connectionFactoryConfigurer.addConnectionFactory(new QQConnectionFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret()));
    }
}
