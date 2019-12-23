package com.xxx.predictionofcurrentsignal.authentication.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableSocial
@Slf4j
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired(required = false)
    private ConnectionSignUp  connectionSignUp;

    @Autowired
    private AppSocialAuthenticationFilterPostProcessor appSocialAuthenticationFilterPostProcessor;

    @Autowired
    private ConnectionRepository  connectionRepository;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,
                connectionFactoryLocator, Encryptors.noOpText());
        //repository.setTablePrefix("test_");
        if(connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }
    @Bean
    public SpringSocialConfigurer springSocialConfigurer(){
        MySpringSocialConfigurer springSocialConfigurer = new MySpringSocialConfigurer();
        springSocialConfigurer.setAppSocialAuthenticationFilterPostProcessor(appSocialAuthenticationFilterPostProcessor);
        springSocialConfigurer.signupUrl("/weixin/signup");
        return springSocialConfigurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(){
        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }

    @Bean
    public ConnectController setConnectController(){
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

}
