package com.psp.cibbank.security;

    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    /**
     * Security configuration class for the application.
     * Configures HTTP security settings, including API key authentication and stateless session management.
     */
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        /**
         * The API key used for authentication, injected from application properties.
         */
        @Value("${api.security.key}")
        private String apiKey;

        /**
         * Configures the security filter chain for the application.
         * Disables CSRF, sets session management to stateless, and adds a custom API key authentication filter.
         *
         * @param http the HttpSecurity object used to configure security settings
         * @return the configured SecurityFilterChain
         * @throws Exception if an error occurs during configuration
         */
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    // Disable CSRF protection as the application uses stateless authentication
                    .csrf(AbstractHttpConfigurer::disable)
                    // Configure session management to be stateless
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    // Add the custom API key authentication filter before the username-password filter
                    .addFilterBefore(new ApiKeyAuthFilter(apiKey), UsernamePasswordAuthenticationFilter.class)
                    // Require authentication for all requests
                    .authorizeHttpRequests(authz -> authz
                            .anyRequest().authenticated()
                    );
            // Build and return the security filter chain
            return http.build();
        }
    }