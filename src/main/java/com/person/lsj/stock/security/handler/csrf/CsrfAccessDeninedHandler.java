package com.person.lsj.stock.security.handler.csrf;

import com.person.lsj.stock.constant.ErrorReason;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import java.io.IOException;

public class CsrfAccessDeninedHandler implements AccessDeniedHandler {
    protected static final Log logger = LogFactory.getLog(CsrfAccessDeninedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (accessDeniedException instanceof InvalidCsrfTokenException
                || accessDeniedException instanceof MissingCsrfTokenException) {
            if (response.isCommitted()) {
                logger.trace("Did not write to response since already committed");
                return;
            }

            if (accessDeniedException instanceof InvalidCsrfTokenException) {
                logger.debug("Responding with 403 status code");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(ErrorReason.CSRF_ACCESS_DENINED);
            } else {
                logger.debug("Responding with 403 status code");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write(ErrorReason.CSRF_MISSING_DENINED);
            }
        }
    }
}
