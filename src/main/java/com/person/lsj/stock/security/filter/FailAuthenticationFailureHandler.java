package com.person.lsj.stock.security.filter;

import com.person.lsj.stock.constant.ErrorReason;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class FailAuthenticationFailureHandler implements AuthenticationFailureHandler {
    protected static final Log logger = LogFactory.getLog(FailAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (response.isCommitted()) {
            logger.trace("Did not write to response since already committed");
            return;
        }

        if (exception instanceof InternalAuthenticationServiceException) {
            logger.debug("Responding with 401 status code");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(ErrorReason.AUTH_INTERNAL_ERROR_DENINED);

        }

        if (exception instanceof AuthenticationException) {
            logger.debug("Responding with 401  status code");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(ErrorReason.AUTH_USER_DENINED);
        }
    }
}
