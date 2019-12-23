package com.xxx.predictionofcurrentsignal.config;

import com.xxx.predictionofcurrentsignal.authentication.handler.AuthenticationFailureHandler;
import com.xxx.predictionofcurrentsignal.authentication.handler.AuthenticationSuccessHandler;
import com.xxx.predictionofcurrentsignal.authentication.sms.mobile.SmsCodeAuthenticationSecurityConfig;
import com.xxx.predictionofcurrentsignal.authentication.sms.mobile.SmsCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.security.SpringSocialConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OAuth2ServerConfig {

    private static final String RESOURCE_ID = "predict";


    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

        @Autowired
        private AuthenticationFailureHandler authenticationFailureHandler;

        @Autowired
        private AuthenticationSuccessHandler authenticationSuccessHandler;

        @Autowired
        private SpringSocialConfigurer springSocialConfigurer;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
            smsCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
            http.formLogin()
                    .loginProcessingUrl("/api/authentication/form")
                    //.loginProcessingUrl("/authentication/form")
                    .failureHandler(authenticationFailureHandler)
                    .successHandler(authenticationSuccessHandler)
                    .and()
                    .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/weixin/signup").permitAll()
                    .antMatchers("/api/**").permitAll()
                    .antMatchers("/**/*.svg","/**/*.png","/**/*.jpg","/**/*.js","/**/*.css","/*.html","/*.json").permitAll()
                    .antMatchers("/connect/**").permitAll()
                    .antMatchers("/myconnect/**").permitAll()
                    //.antMatchers("/code/*","/authentication/mobile").permitAll().and().authorizeRequests().anyRequest().authenticated()
                    .antMatchers("/api/code/*","/api/authentication/mobile").permitAll().and().authorizeRequests().anyRequest().authenticated()
                    .and().apply(smsCodeAuthenticationSecurityConfig)
                    .and().apply(springSocialConfigurer);
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JwtAccessTokenConverter jwtAccessTokenConverter;

        @Autowired
        private TokenStore tokenStore;

        @Autowired
        private TokenEnhancer tokenEnhancer;

        @Autowired
        @Qualifier("myUserDetailService")
        private UserDetailsService userDetailService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            String secret = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("159");
            clients.inMemory().withClient("client_1")
                    .resourceIds(RESOURCE_ID)
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .accessTokenValiditySeconds(7200)
                    .scopes("select")
                    .authorities("client")
                    .secret(secret)
                    .and().withClient("client_2")
                    .resourceIds(RESOURCE_ID)
                    .authorizedGrantTypes("password", "refresh_token")
                    .accessTokenValiditySeconds(7200)
                    .scopes("select")
                    .authorities("client")
                    .secret(secret);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(tokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints
                    //.tokenStore(new RedisTokenStore(redisConnectionFactory))
                    .authenticationManager(authenticationManager)
                    .userDetailsService(userDetailService)
                    .tokenStore(tokenStore)
                    .accessTokenConverter(jwtAccessTokenConverter)
                    .tokenEnhancer(enhancerChain)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients().checkTokenAccess("permitAll()").tokenKeyAccess("isAuthenticated()");
        }

    }


}
