package com.xxx.predictionofcurrentsignal.config;

import com.sun.org.apache.regexp.internal.RE;
import com.xxx.predictionofcurrentsignal.dao.entity.User;
import com.xxx.predictionofcurrentsignal.dao.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class MyUserDetailService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        log.info("登录用户是:"+account);
        String[] parameter = StringUtils.split(account,":");
        if(parameter[0].equals("mobile")){
            return userRepository.findUserByTel(parameter[1]);
        }else if(parameter[0].equals("account")){
            return userRepository.findUserByAccount(parameter[1]);
        }
        return null;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登录用户是:"+userId);
        return userRepository.findUserByName(userId);
    }

}