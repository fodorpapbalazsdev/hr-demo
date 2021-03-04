package com.avinty.hr.security;

import com.avinty.hr.security.jwt.JwtTokenProvider;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");
        if (httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.toString()) || httpServletRequest.getRequestURI().contains("/auth")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            if (requestTokenHeader != null) {
                String jwtToken = requestTokenHeader.substring(7);
                if (jwtTokenProvider.validateToken(jwtToken)) {
                    httpServletRequest.setAttribute("email", jwtTokenProvider.getEmailFromJwt(jwtToken));
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } else {
                    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token"); // TODO use MessageConstants!
                }
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Token"); // TODO use MessageConstants!
            }
        }
    }
}
