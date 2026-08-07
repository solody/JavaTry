package com.solody.trycloudoauth2login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Browser-facing AS URL (via Gateway). issuer-uri discovery returns
     * http://authorization-server:8080/... which the browser cannot reach.
     */
    private static final String PUBLIC_END_SESSION_ENDPOINT =
            "http://authorization-server.solody.com/connect/logout";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            ClientRegistrationRepository clientRegistrationRepository) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/", "/provider-logged-out", "/error", "/.well-known/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login((oauth2) -> oauth2
                        .defaultSuccessUrl("/", true)
                        .authorizationEndpoint((authorization) -> authorization
                                .authorizationRequestResolver(
                                        pkceResolver(clientRegistrationRepository)
                                )
                        )
                ).logout((logout) -> logout
                        .logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository))
                );
        return http.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler(ClientRegistrationRepository clientRegistrationRepository) {
        ClientRegistrationRepository withPublicLogoutEndpoint = (registrationId) -> {
            ClientRegistration registration = clientRegistrationRepository.findByRegistrationId(registrationId);
            if (registration == null) {
                return null;
            }
            Map<String, Object> metadata = new HashMap<>(registration.getProviderDetails().getConfigurationMetadata());
            metadata.put("end_session_endpoint", PUBLIC_END_SESSION_ENDPOINT);
            return ClientRegistration.withClientRegistration(registration)
                    .providerConfigurationMetadata(metadata)
                    .build();
        };

        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedLogoutSuccessHandler(withPublicLogoutEndpoint);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/provider-logged-out");
        return oidcLogoutSuccessHandler;
    }

    /**
     * Authorization Server 7 defaults to require-proof-key=true; confidential
     * clients only send PKCE when explicitly customized.
     */
    private static OAuth2AuthorizationRequestResolver pkceResolver(
            ClientRegistrationRepository clientRegistrationRepository) {
        DefaultOAuth2AuthorizationRequestResolver resolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository, "/oauth2/authorization");
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
        return resolver;
    }
}
