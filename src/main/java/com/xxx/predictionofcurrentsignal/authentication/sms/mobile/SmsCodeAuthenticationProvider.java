package com.xxx.predictionofcurrentsignal.authentication.sms.mobile;

import com.xxx.predictionofcurrentsignal.dao.entity.User;
import com.xxx.predictionofcurrentsignal.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {


    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken authenticationToken= (SmsCodeAuthenticationToken) authentication;
        User userDetails = (User) userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
        if(userDetails==null){
            throw new InternalAuthenticationServiceException("无法获取用户信息！");
        }
        SmsCodeAuthenticationToken authenticationResult=new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
