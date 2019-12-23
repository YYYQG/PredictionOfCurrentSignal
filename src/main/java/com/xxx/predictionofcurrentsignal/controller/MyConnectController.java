package com.xxx.predictionofcurrentsignal.controller;



import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xxx.predictionofcurrentsignal.util.ResponseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.GenericTypeResolver;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UrlPathHelper;

@RestController
@RequestMapping({"/myconnect"})
public class MyConnectController implements InitializingBean {
    private static final Log logger = LogFactory.getLog(org.springframework.social.connect.web.ConnectController.class);
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final ConnectionRepository connectionRepository;
    private final MultiValueMap<Class<?>, ConnectInterceptor<?>> connectInterceptors = new LinkedMultiValueMap();
    private final MultiValueMap<Class<?>, DisconnectInterceptor<?>> disconnectInterceptors = new LinkedMultiValueMap();
    private ConnectSupport connectSupport;
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();
    private String viewPath = "connect/";
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private String applicationUrl = null;
    protected static final String DUPLICATE_CONNECTION_ATTRIBUTE = "social_addConnection_duplicate";
    protected static final String PROVIDER_ERROR_ATTRIBUTE = "social_provider_error";
    protected static final String AUTHORIZATION_ERROR_ATTRIBUTE = "social_authorization_error";

    @Inject
    public MyConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.connectionRepository = connectionRepository;
    }

    /** @deprecated */
    @Deprecated
    public void setInterceptors(List<ConnectInterceptor<?>> interceptors) {
        this.setConnectInterceptors(interceptors);
    }

    public void setConnectInterceptors(List<ConnectInterceptor<?>> interceptors) {
        Iterator var2 = interceptors.iterator();

        while(var2.hasNext()) {
            ConnectInterceptor<?> interceptor = (ConnectInterceptor)var2.next();
            this.addInterceptor(interceptor);
        }

    }

    public void setDisconnectInterceptors(List<DisconnectInterceptor<?>> interceptors) {
        Iterator var2 = interceptors.iterator();

        while(var2.hasNext()) {
            DisconnectInterceptor<?> interceptor = (DisconnectInterceptor)var2.next();
            this.addDisconnectInterceptor(interceptor);
        }

    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    public void setSessionStrategy(SessionStrategy sessionStrategy) {
        this.sessionStrategy = sessionStrategy;
    }

    public void addInterceptor(ConnectInterceptor<?> interceptor) {
        Class<?> serviceApiType = GenericTypeResolver.resolveTypeArgument(interceptor.getClass(), ConnectInterceptor.class);
        this.connectInterceptors.add(serviceApiType, interceptor);
    }

    public void addDisconnectInterceptor(DisconnectInterceptor<?> interceptor) {
        Class<?> serviceApiType = GenericTypeResolver.resolveTypeArgument(interceptor.getClass(), DisconnectInterceptor.class);
        this.disconnectInterceptors.add(serviceApiType, interceptor);
    }

    @RequestMapping(
            method = {RequestMethod.GET}
    )
    public String connectionStatus(NativeWebRequest request, Model model) {
        this.setNoCache(request);
        this.processFlash(request, model);
        Map<String, List<Connection<?>>> connections = this.connectionRepository.findAllConnections();
        model.addAttribute("providerIds", this.connectionFactoryLocator.registeredProviderIds());
        model.addAttribute("connectionMap", connections);
        return this.connectView();
    }

    /*@RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.GET}
    )
    public String connectionStatus(@PathVariable String providerId, NativeWebRequest request, Model model) {
        this.setNoCache(request);
        this.processFlash(request, model);
        List<Connection<?>> connections = this.connectionRepository.findConnections(providerId);
        this.setNoCache(request);
        if (connections.isEmpty()) {
            return this.connectView(providerId);
        } else {
            model.addAttribute("connections", connections);
            return this.connectedView(providerId);
        }
    }*/

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity connect(@PathVariable String providerId, NativeWebRequest request) {
        ConnectionFactory<?> connectionFactory = this.connectionFactoryLocator.getConnectionFactory(providerId);
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
        this.preConnect(connectionFactory, parameters, request);
        try {

            //redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fmyconnect%2Fweixin
            String url = this.connectSupport.buildOAuthUrl(connectionFactory, request, parameters);
            System.out.print(url.contains("redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fmyconnect%2Fweixin"));
            String newUrl = url.replace("redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fmyconnect%2Fweixin","redirect_uri=http%3A%2F%2Fwww.pinzhi365.com%2Fconnect%2Fweixin");
            return ResponseEntity.success(newUrl);
        } catch (Exception var6) {
            this.sessionStrategy.setAttribute(request, "social_provider_error", var6);
            throw new RuntimeException("");
        }
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.GET},
            params = {"oauth_token"}
    )
    public RedirectView oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
        try {
            OAuth1ConnectionFactory<?> connectionFactory = (OAuth1ConnectionFactory)this.connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = this.connectSupport.completeConnection(connectionFactory, request);
            this.addConnection(connection, connectionFactory, request);
        } catch (Exception var5) {
            this.sessionStrategy.setAttribute(request, "social_provider_error", var5);
            logger.warn("Exception while handling OAuth1 callback (" + var5.getMessage() + "). Redirecting to " + providerId + " connection status page.");
        }

        return this.connectionStatusRedirect(providerId, request);
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.GET},
            params = {"code"}
    )
    public RedirectView oauth2Callback(@PathVariable String providerId, NativeWebRequest request) {
        try {
            OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory)this.connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = this.connectSupport.completeConnection(connectionFactory, request);
            this.addConnection(connection, connectionFactory, request);
        } catch (Exception var5) {
            this.sessionStrategy.setAttribute(request, "social_provider_error", var5);
            logger.warn("Exception while handling OAuth2 callback (" + var5.getMessage() + "). Redirecting to " + providerId + " connection status page.");
        }

        return this.connectionStatusRedirect(providerId, request);
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.GET},
            params = {"error"}
    )
    public RedirectView oauth2ErrorCallback(@PathVariable String providerId, @RequestParam("error") String error, @RequestParam(value = "error_description",required = false) String errorDescription, @RequestParam(value = "error_uri",required = false) String errorUri, NativeWebRequest request) {
        Map<String, String> errorMap = new HashMap();
        errorMap.put("error", error);
        if (errorDescription != null) {
            errorMap.put("errorDescription", errorDescription);
        }

        if (errorUri != null) {
            errorMap.put("errorUri", errorUri);
        }

        this.sessionStrategy.setAttribute(request, "social_authorization_error", errorMap);
        return this.connectionStatusRedirect(providerId, request);
    }

    @RequestMapping(
            value = {"/{providerId}"},
            method = {RequestMethod.DELETE}
    )
    public RedirectView removeConnections(@PathVariable String providerId, NativeWebRequest request) {
        ConnectionFactory<?> connectionFactory = this.connectionFactoryLocator.getConnectionFactory(providerId);
        this.preDisconnect(connectionFactory, request);
        this.connectionRepository.removeConnections(providerId);
        this.postDisconnect(connectionFactory, request);
        return this.connectionStatusRedirect(providerId, request);
    }

    @RequestMapping(
            value = {"/{providerId}/{providerUserId}"},
            method = {RequestMethod.DELETE}
    )
    public RedirectView removeConnection(@PathVariable String providerId, @PathVariable String providerUserId, NativeWebRequest request) {
        ConnectionFactory<?> connectionFactory = this.connectionFactoryLocator.getConnectionFactory(providerId);
        this.preDisconnect(connectionFactory, request);
        this.connectionRepository.removeConnection(new ConnectionKey(providerId, providerUserId));
        this.postDisconnect(connectionFactory, request);
        return this.connectionStatusRedirect(providerId, request);
    }

    protected String connectView() {
        return this.getViewPath() + "status";
    }

    protected String connectView(String providerId) {
        return this.getViewPath() + providerId + "Connect";
    }

    protected String connectedView(String providerId) {
        return this.getViewPath() + providerId + "Connected";
    }

    protected RedirectView connectionStatusRedirect(String providerId, NativeWebRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest)request.getNativeRequest(HttpServletRequest.class);
        String path = "/connect/" + providerId + this.getPathExtension(servletRequest);
        if (this.prependServletPath(servletRequest)) {
            path = servletRequest.getServletPath() + path;
        }

        return new RedirectView(path, true);
    }

    public void afterPropertiesSet() throws Exception {
        this.connectSupport = new ConnectSupport(this.sessionStrategy);
        if (this.applicationUrl != null) {
            this.connectSupport.setApplicationUrl(this.applicationUrl);
        }

    }

    private boolean prependServletPath(HttpServletRequest request) {
        return !this.urlPathHelper.getPathWithinServletMapping(request).equals("");
    }

    private String getPathExtension(HttpServletRequest request) {
        String fileName = this.extractFullFilenameFromUrlPath(request.getRequestURI());
        String extension = StringUtils.getFilenameExtension(fileName);
        return extension != null ? "." + extension : "";
    }

    private String extractFullFilenameFromUrlPath(String urlPath) {
        int end = urlPath.indexOf(63);
        if (end == -1) {
            end = urlPath.indexOf(35);
            if (end == -1) {
                end = urlPath.length();
            }
        }

        int begin = urlPath.lastIndexOf(47, end) + 1;
        int paramIndex = urlPath.indexOf(59, begin);
        end = paramIndex != -1 && paramIndex < end ? paramIndex : end;
        return urlPath.substring(begin, end);
    }

    private String getViewPath() {
        return this.viewPath;
    }

    private void addConnection(Connection<?> connection, ConnectionFactory<?> connectionFactory, WebRequest request) {
        try {
            this.connectionRepository.addConnection(connection);
            this.postConnect(connectionFactory, connection, request);
        } catch (DuplicateConnectionException var5) {
            this.sessionStrategy.setAttribute(request, "social_addConnection_duplicate", var5);
        }

    }

    private void preConnect(ConnectionFactory<?> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
        Iterator var4 = this.interceptingConnectionsTo(connectionFactory).iterator();

        while(var4.hasNext()) {
            ConnectInterceptor interceptor = (ConnectInterceptor)var4.next();
            interceptor.preConnect(connectionFactory, parameters, request);
        }

    }

    private void postConnect(ConnectionFactory<?> connectionFactory, Connection<?> connection, WebRequest request) {
        Iterator var4 = this.interceptingConnectionsTo(connectionFactory).iterator();

        while(var4.hasNext()) {
            ConnectInterceptor interceptor = (ConnectInterceptor)var4.next();
            interceptor.postConnect(connection, request);
        }

    }

    private void preDisconnect(ConnectionFactory<?> connectionFactory, WebRequest request) {
        Iterator var3 = this.interceptingDisconnectionsTo(connectionFactory).iterator();

        while(var3.hasNext()) {
            DisconnectInterceptor interceptor = (DisconnectInterceptor)var3.next();
            interceptor.preDisconnect(connectionFactory, request);
        }

    }

    private void postDisconnect(ConnectionFactory<?> connectionFactory, WebRequest request) {
        Iterator var3 = this.interceptingDisconnectionsTo(connectionFactory).iterator();

        while(var3.hasNext()) {
            DisconnectInterceptor interceptor = (DisconnectInterceptor)var3.next();
            interceptor.postDisconnect(connectionFactory, request);
        }

    }

    private List<ConnectInterceptor<?>> interceptingConnectionsTo(ConnectionFactory<?> connectionFactory) {
        Class<?> serviceType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
        List<ConnectInterceptor<?>> typedInterceptors = (List)this.connectInterceptors.get(serviceType);
        if (typedInterceptors == null) {
            typedInterceptors = Collections.emptyList();
        }

        return typedInterceptors;
    }

    private List<DisconnectInterceptor<?>> interceptingDisconnectionsTo(ConnectionFactory<?> connectionFactory) {
        Class<?> serviceType = GenericTypeResolver.resolveTypeArgument(connectionFactory.getClass(), ConnectionFactory.class);
        List<DisconnectInterceptor<?>> typedInterceptors = (List)this.disconnectInterceptors.get(serviceType);
        if (typedInterceptors == null) {
            typedInterceptors = Collections.emptyList();
        }

        return typedInterceptors;
    }

    private void processFlash(WebRequest request, Model model) {
        this.convertSessionAttributeToModelAttribute("social_addConnection_duplicate", request, model);
        this.convertSessionAttributeToModelAttribute("social_provider_error", request, model);
        model.addAttribute("social_authorization_error", this.sessionStrategy.getAttribute(request, "social_authorization_error"));
        this.sessionStrategy.removeAttribute(request, "social_authorization_error");
    }

    private void convertSessionAttributeToModelAttribute(String attributeName, WebRequest request, Model model) {
        if (this.sessionStrategy.getAttribute(request, attributeName) != null) {
            model.addAttribute(attributeName, Boolean.TRUE);
            this.sessionStrategy.removeAttribute(request, attributeName);
        }

    }

    private void setNoCache(NativeWebRequest request) {
        HttpServletResponse response = (HttpServletResponse)request.getNativeResponse(HttpServletResponse.class);
        if (response != null) {
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 1L);
            response.setHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
        }

    }
}
