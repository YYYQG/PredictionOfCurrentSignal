package com.xxx.predictionofcurrentsignal.authentication.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.predictionofcurrentsignal.util.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.security.SocialAuthenticationRedirectException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MySocialAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private AuthenticationFailureHandler delegate;

    @Autowired
    private ObjectMapper objectMapper;
    public MySocialAuthenticationFailureHandler(AuthenticationFailureHandler delegate) {
        this.delegate = delegate;
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof SocialAuthenticationRedirectException) {
            String url = ((SocialAuthenticationRedirectException)failed).getRedirectUrl();
            if("/weixin/signup".equals(url)){
                response.setContentType("application/json;charset=UTF=8");
                response.getWriter().write(objectMapper.writeValueAsString(ResponseEntity.fail()));
            }
        } else {
            this.delegate.onAuthenticationFailure(request, response, failed);
        }
    }
}