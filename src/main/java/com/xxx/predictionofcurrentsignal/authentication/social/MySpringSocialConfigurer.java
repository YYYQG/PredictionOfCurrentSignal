package com.xxx.predictionofcurrentsignal.authentication.social;

import lombok.extern.slf4j.Slf4j;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

@Slf4j
public class MySpringSocialConfigurer extends SpringSocialConfigurer {

    private AppSocialAuthenticationFilterPostProcessor appSocialAuthenticationFilterPostProcessor;

    public AppSocialAuthenticationFilterPostProcessor getAppSocialAuthenticationFilterPostProcessor() {
        return appSocialAuthenticationFilterPostProcessor;
    }

    public void setAppSocialAuthenticationFilterPostProcessor(AppSocialAuthenticationFilterPostProcessor appSocialAuthenticationFilterPostProcessor) {
        this.appSocialAuthenticationFilterPostProcessor = appSocialAuthenticationFilterPostProcessor;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl("/qqLogin");
        log.info(String.valueOf(appSocialAuthenticationFilterPostProcessor!=null));
        if (appSocialAuthenticationFilterPostProcessor!=null){
            appSocialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T) filter;
    }
}
