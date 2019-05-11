package com.chj.demo.security.jwt;

import com.chj.demo.config.constant.SecurityConstant;
import com.chj.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author chehaojie
 * @date 2019/05/11 17:11
 */
@Slf4j
@RequiredArgsConstructor
@WebFilter(urlPatterns = "/**")
@Component
public class JwtFilter implements Filter {

    private static final List<String> WHITE_LIST = Collections.singletonList("/api/auth/register");

    private final JwtService jwtService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("JwtFilter started");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (WHITE_LIST.contains(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String jwt = resolveToken(request);
            if (jwtService.isTokenValid(jwt)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }

    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(SecurityConstant.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
