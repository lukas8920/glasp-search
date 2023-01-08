package org.kehrbusch;

import org.kehrbusch.failures.LoginAttemptService;
import org.kehrbusch.failures.Publisher;
import org.kehrbusch.util.InvalidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class JwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = Logger.getLogger(JwtTokenFilter.class.getName());

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginAttemptService loginAttemptService;
    private final Publisher publisher;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, LoginAttemptService loginAttemptService, Publisher publisher){
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginAttemptService = loginAttemptService;
        this.publisher = publisher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String ip = getClientIP(httpServletRequest);
        if (loginAttemptService.isBlocked(ip)) {
            logger.info("IP is blocked: " + ip);
            httpServletResponse.sendError(403, "Access Forbidden.");
            return;
        }

        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        try {
            if (token != null){
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                this.publisher.publishAuthorizationSuccess();
            }
        } catch (InvalidationException e){
            SecurityContextHolder.clearContext();
            this.publisher.publishAuthorizationFailure();
            logger.info("Return validation error with code " + e.getHttpStatus().value() + " to clients.");
            httpServletResponse.sendError(e.getHttpStatus().value(), e.getMessage());
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
