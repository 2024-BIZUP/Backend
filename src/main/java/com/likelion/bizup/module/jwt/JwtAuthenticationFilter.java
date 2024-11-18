package com.likelion.bizup.module.jwt;

import com.likelion.bizup.module.jwt.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        // Access Token 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            String refreshToken = request.getHeader("Refresh-Token");
            if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
                String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
                String newAccessToken = jwtTokenProvider.createAccessToken(userId, "USER_ROLE");
                response.setHeader("Access-Token", newAccessToken);  // 새로운 Access Token을 헤더에 포함시켜 응답
            }
        }
        filterChain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
    private Authentication getAuthentication(String token) {
        String userId = jwtTokenProvider.getUserIdFromToken(token);
        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }

}

