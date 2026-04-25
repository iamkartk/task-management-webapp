package com.example.api_gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtGatewayFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        System.out.println("Gateway PATH: " + path);

        // ✅ Public endpoints - JWT validation skip
        if (path.contains("/tasks/api/v1/auth") ||
                path.contains("/actuator") ||
                path.contains("/swagger-ui") ||
                path.contains("/v3/api-docs")) {
            System.out.println("PUBLIC ENDPOINT, SKIPPING JWT");
            return chain.filter(exchange);
        }
        if (path.startsWith("/actuator")) {
            return chain.filter(exchange);
        }
        exchange.getRequest().getHeaders().forEach((key, value) -> {
            System.out.println("HEADER: " + key + " = " + value);
        });

        // 🔹 2. Get Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst("Authorization");

        System.out.println("Gateway AUTH HEADER: " + authHeader);

        // 🔹 3. Header missing → reject
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 🔹 4. Extract token
        String token = authHeader.substring(7);

        // 🔹 5. Validate token
        if (!jwtUtil.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }


        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header("Authorization", authHeader)
                .build();

        // 🔹 6. Forward request WITH header
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}