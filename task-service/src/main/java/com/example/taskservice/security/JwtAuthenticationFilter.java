package com.example.taskservice.security;

import com.example.taskservice.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    // Intercept every request and validate JWT token if present
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        System.out.println("PATH: " + request.getServletPath());
        System.out.println("AUTH HEADER: " + request.getHeader("Authorization"));
        String token = null;
        String username = null;
        // Check if Authorization header contains Bearer token
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                System.out.println("Debug 3");
                token = authHeader.substring(7);
                username = jwtUtil.extractUsername(token);
                System.out.println("TOKEN: " + token);
                System.out.println("USERNAME: " + username);
            }
        } catch (Exception e) {
            System.out.println("JWT ERROR: " + e.getMessage());
        }

        // If username found and user not already authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("Username from token: " + username);
            // Validate token against username and expiration
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

                //authenticated user object, इसमें stored है:username, roles, authorities
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Store authentication in SecurityContext

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
