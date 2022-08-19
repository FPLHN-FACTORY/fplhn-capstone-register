package com.fpolydatn.infrastructure.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HangNT
 */
@Component
public class OAuth2AuthorizationRequestRedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String path = request.getServletPath();
        if (!path.contains("/authentication") || !"/".equals(path)) {
            filterChain.doFilter(request, response);
        }
//        if (Objects.isNull(authentication)) {
//            response.sendRedirect("/");
//            filterChain.doFilter(request,response);
//        }
    }

}
