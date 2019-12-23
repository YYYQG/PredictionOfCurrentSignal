package com.xxx.predictionofcurrentsignal.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class SimpleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        /*filterChain.doFilter(servletRequest, servletResponse);*/
        HttpServletRequest request1 = (HttpServletRequest) request;
        String path = request1.getServletPath();
        if (path.contains("api")) {
            int index = path.indexOf("api") + 3;
            String newPath = path.substring(index);
            request.getRequestDispatcher(newPath).forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }


    }

    @Override
    public void destroy() {
    }
}