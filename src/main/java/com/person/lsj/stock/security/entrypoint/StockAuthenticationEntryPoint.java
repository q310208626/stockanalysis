package com.person.lsj.stock.security.entrypoint;

import com.person.lsj.stock.constant.ErrorReason;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class StockAuthenticationEntryPoint implements AuthenticationEntryPoint {
    protected static final Log logger = LogFactory.getLog(StockAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.debug(HttpServletResponse.SC_FORBIDDEN);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(ErrorReason.AUTH_NEED_LOGIN);
    }
}
