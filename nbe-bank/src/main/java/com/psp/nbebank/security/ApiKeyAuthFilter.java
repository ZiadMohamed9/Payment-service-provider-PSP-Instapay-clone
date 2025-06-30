package com.psp.nbebank.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom filter for API key-based authentication.
 * Extends the OncePerRequestFilter to ensure the filter is executed once per request.
 */
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final String API_KEY;

    /**
     * Constructs an ApiKeyAuthFilter with the provided API key.
     *
     * @param apiKey the API key used for authentication
     */
    public ApiKeyAuthFilter(String apiKey) {
        this.API_KEY = apiKey;
    }

    /**
     * Filters incoming requests to authenticate using the API key.
     * If the API key in the request header matches the configured API key,
     * an authentication token is set in the SecurityContext.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to pass the request and response to the next filter
     * @throws ServletException if an error occurs during filtering
     * @throws IOException if an I/O error occurs during filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Retrieve the API key from the request header
        String apiKey = request.getHeader("X-API-KEY");

        // Validate the API key and set authentication if valid
        if (apiKey != null && apiKey.equals(API_KEY)) {
            ApiKeyAuthenticationToken auth = new ApiKeyAuthenticationToken(apiKey);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}