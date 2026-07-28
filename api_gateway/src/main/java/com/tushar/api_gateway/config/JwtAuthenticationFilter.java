package com.tushar.api_gateway.config;

import com.tushar.api_gateway.client.UserServiceClient;
import com.tushar.api_gateway.dto.ValidateTokenRequest;
import com.tushar.api_gateway.dto.ValidateTokenResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements Filter {

    private final UserServiceClient userServiceClient;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/auth/login",
            "/auth/signup",
            "/actuator/health"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        if (isPublicEndpoint(path)) {
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                log.error("Error in filter chain", e);
            }
            return;
        }

        // Extract token from header
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // Create request
            ValidateTokenRequest validateRequest = new ValidateTokenRequest();
            validateRequest.setToken(token);

            ValidateTokenResponse validateResponse = userServiceClient.validateToken(validateRequest);

            if (!validateResponse.isValid()) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            httpRequest.setAttribute("X-User-Id", String.valueOf(validateResponse.getUserId()));
            httpRequest.setAttribute("X-User-Email", validateResponse.getEmail());
            httpRequest.setAttribute("X-User-Roles", validateResponse.getRoles());

            chain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT validation failed: {}", e.getMessage());
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isPublicEndpoint(String path) {
        for (String endpoint : PUBLIC_ENDPOINTS) {
            if (path.contains(endpoint)) {
                return true;
            }
        }
        return false;
    }
}
