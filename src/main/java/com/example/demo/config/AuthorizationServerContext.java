package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Spring OAuth2 authorization server (token issue) server configuration.
 *
 * @author Kisong
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerContext extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;

    private TokenStore tokenStore;

    private static final String CLIENT_ID = "client";

    private static final String CLIENT_SECRET = "secret";

    private static final String GRANT_TYPE_PASSWORD = "password";

    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization";

    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh";

    private static final String SCOPE_READ = "read";

    private static final String SCOPE_WRITE = "write";

    private static final String SCOPE_TRUST = "trust";

    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 5;

    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 50;

    @Autowired
    public AuthorizationServerContext(final AuthenticationManager authenticationManager,
                                      final TokenStore tokenStore) {
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer.inMemory()
                .withClient(CLIENT_ID)
                .secret(CLIENT_SECRET)
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, GRANT_TYPE_AUTHORIZATION_CODE, GRANT_TYPE_REFRESH_TOKEN)
                .scopes(SCOPE_READ, SCOPE_WRITE, SCOPE_TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
                refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
    }

}
