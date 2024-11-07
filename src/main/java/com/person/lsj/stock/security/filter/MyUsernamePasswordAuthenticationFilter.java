package com.person.lsj.stock.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.person.lsj.stock.security.request.RequestBodyCopyServletRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequestWrapper requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new RequestBodyCopyServletRequestWrapper((HttpServletRequest) request);
            super.doFilter(requestWrapper, response, chain);
        } else {
            super.doFilter(request, response, chain);
        }
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        try {
            Map requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
            Object o = requestMap.get(getPasswordParameter());
            if (o != null) {
                return String.valueOf(o);
            } else {
                return "";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        try {
            Map requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
            Object o = requestMap.get(getUsernameParameter());
            if (o != null) {
                return String.valueOf(o);
            } else {
                return "";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
