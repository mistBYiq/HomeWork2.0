package org.example.filter;

import org.apache.commons.lang3.StringUtils;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthHeaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest castedRequest = (HttpServletRequest) servletRequest;
        String authHeader = castedRequest.getHeader("X-Auth-Token");
        if (StringUtils.isNotBlank(authHeader)) {
            System.out.println("Header was found with payload: " + authHeader);
        } else {
            System.out.println("Header was not found!!!");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
