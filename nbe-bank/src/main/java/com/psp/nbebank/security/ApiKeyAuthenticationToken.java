package com.psp.nbebank.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Custom authentication token for API key-based authentication.
 * Extends the AbstractAuthenticationToken class to provide authentication
 * using an API key.
 */
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String apiKey;

    /**
     * Constructs an ApiKeyAuthenticationToken with the provided API key.
     * Sets the authentication status to true.
     *
     * @param apiKey the API key used for authentication
     */
    public ApiKeyAuthenticationToken(String apiKey) {
        super(null);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }

    /**
     * Returns the credentials associated with this authentication token.
     * Since this is API key-based authentication, credentials are not used.
     *
     * @return null as no credentials are required
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Returns the principal associated with this authentication token.
     * In this case, the principal is the API key.
     *
     * @return the API key
     */
    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}