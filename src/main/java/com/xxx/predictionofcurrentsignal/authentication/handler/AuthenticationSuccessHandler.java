package com.xxx.predictionofcurrentsignal.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.predictionofcurrentsignal.properties.SecurityProperties;
import com.xxx.predictionofcurrentsignal.util.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ClientDetailsService clientDetailService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * Authentication是spring security的核心接口，封装了认证信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic ")) {
            throw new UnapprovedClientAuthenticationException("请求头无clien信息");
        }

        String[] tokens = extractAndDecodeHeader(header, request);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailService.loadClientByClientId(clientId);
        log.info(request.getHeader("Content-Type"));
        log.info(clientDetails.getClientSecret());
        log.info(clientSecret);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在" + clientId);
        } else if (passwordEncoder.matches(clientDetails.getClientSecret(), passwordEncoder.encode(clientSecret))) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
        }

        @SuppressWarnings("unchecked")
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

        OAuth2Authentication oauth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oauth2AccessToken = authorizationServerTokenServices.createAccessToken(oauth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        ResponseEntity responseEntity = ResponseEntity.success(oauth2AccessToken);
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity));
    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }

    /*public Map<String,Object> resultHandle(OAuth2AccessToken oAuth2AccessToken){

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("access_token",oAuth2AccessToken.getValue());
        result.put("refresh_token",oAuth2AccessToken.getRefreshToken());
        result.put("expires_in",oAuth2AccessToken.getExpiresIn());
        result.put("scope",oAuth2AccessToken.getScope());
        result.put("token_type",oAuth2AccessToken.getTokenType());
        return result;
    }*/

}
