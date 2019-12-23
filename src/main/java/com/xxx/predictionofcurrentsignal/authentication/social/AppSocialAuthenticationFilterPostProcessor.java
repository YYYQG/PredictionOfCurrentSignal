package com.xxx.predictionofcurrentsignal.authentication.social;

import com.xxx.predictionofcurrentsignal.authentication.handler.AuthenticationFailureHandler;
import com.xxx.predictionofcurrentsignal.authentication.handler.AuthenticationSuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.SocialAuthenticationFailureHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppSocialAuthenticationFilterPostProcessor {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    public void process(SocialAuthenticationFilter socialAuthenticationFilter){
        log.info("社交登录token");
        socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        //socialAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        socialAuthenticationFilter.setAuthenticationFailureHandler(new MySocialAuthenticationFailureHandler(authenticationFailureHandler));
    }

}
