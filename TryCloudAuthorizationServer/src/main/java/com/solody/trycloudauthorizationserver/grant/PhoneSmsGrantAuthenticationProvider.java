package com.solody.trycloudauthorizationserver.grant;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneSmsGrantAuthenticationProvider implements AuthenticationProvider {

    private static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    private static final OAuth2TokenType ID_TOKEN_TOKEN_TYPE = new OAuth2TokenType(OidcParameterNames.ID_TOKEN);

    private final SmsCodeVerifier smsCodeVerifier;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public PhoneSmsGrantAuthenticationProvider(
            SmsCodeVerifier smsCodeVerifier,
            OAuth2AuthorizationService authorizationService,
            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        Assert.notNull(smsCodeVerifier, "smsCodeVerifier cannot be null");
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.smsCodeVerifier = smsCodeVerifier;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PhoneSmsGrantAuthenticationToken phoneSmsGrantAuthentication =
                (PhoneSmsGrantAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal =
                getAuthenticatedClientElseThrowInvalidClient(phoneSmsGrantAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();

        if (registeredClient == null
                || !registeredClient.getAuthorizationGrantTypes().contains(phoneSmsGrantAuthentication.getGrantType())) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        String phone = phoneSmsGrantAuthentication.getPhone();
        String code = phoneSmsGrantAuthentication.getCode();
        if (!smsCodeVerifier.verify(phone, code)) {
            OAuth2Error error = new OAuth2Error(
                    OAuth2ErrorCodes.INVALID_GRANT,
                    "SMS verification code is invalid.",
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        Set<String> authorizedScopes = authorizedScopes(
                registeredClient, phoneSmsGrantAuthentication.getScopes());

        Authentication userPrincipal = new UsernamePasswordAuthenticationToken(phone, null, Collections.emptyList());

        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(userPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(phoneSmsGrantAuthentication.getGrantType())
                .authorizationGrant(phoneSmsGrantAuthentication);

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(phone)
                .authorizationGrantType(phoneSmsGrantAuthentication.getGrantType())
                .authorizedScopes(authorizedScopes);

        OAuth2TokenContext accessTokenContext = tokenContextBuilder
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .build();
        OAuth2Token generatedAccessToken = tokenGenerator.generate(accessTokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(
                    OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.",
                    ACCESS_TOKEN_REQUEST_ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        OAuth2AccessToken accessToken = accessToken(authorizationBuilder, generatedAccessToken, accessTokenContext);

        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN)) {
            OAuth2TokenContext refreshTokenContext = tokenContextBuilder
                    .tokenType(OAuth2TokenType.REFRESH_TOKEN)
                    .build();
            OAuth2Token generatedRefreshToken = tokenGenerator.generate(refreshTokenContext);
            if (generatedRefreshToken != null) {
                if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                    OAuth2Error error = new OAuth2Error(
                            OAuth2ErrorCodes.SERVER_ERROR,
                            "The token generator failed to generate a valid refresh token.",
                            ACCESS_TOKEN_REQUEST_ERROR_URI);
                    throw new OAuth2AuthenticationException(error);
                }
                refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                authorizationBuilder.refreshToken(refreshToken);
            }
        }

        OidcIdToken idToken = null;
        if (authorizedScopes.contains(OidcScopes.OPENID)) {
            OAuth2TokenContext idTokenContext = tokenContextBuilder
                    .tokenType(ID_TOKEN_TOKEN_TYPE)
                    .authorization(authorizationBuilder.build())
                    .build();
            OAuth2Token generatedIdToken = tokenGenerator.generate(idTokenContext);
            if (!(generatedIdToken instanceof Jwt jwt)) {
                OAuth2Error error = new OAuth2Error(
                        OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the ID token.",
                        ACCESS_TOKEN_REQUEST_ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            idToken = new OidcIdToken(
                    jwt.getTokenValue(), jwt.getIssuedAt(), jwt.getExpiresAt(), jwt.getClaims());
            OidcIdToken savedIdToken = idToken;
            authorizationBuilder.token(savedIdToken, metadata ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, savedIdToken.getClaims()));
        }

        authorizationService.save(authorizationBuilder.build());

        Map<String, Object> additionalParameters = Collections.emptyMap();
        if (idToken != null) {
            additionalParameters = new HashMap<>();
            additionalParameters.put(OidcParameterNames.ID_TOKEN, idToken.getTokenValue());
        }

        return new OAuth2AccessTokenAuthenticationToken(
                registeredClient, clientPrincipal, accessToken, refreshToken, additionalParameters);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneSmsGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private static Set<String> authorizedScopes(RegisteredClient registeredClient, Set<String> requestedScopes) {
        Set<String> authorizedScopes = new HashSet<>();
        if (!CollectionUtils.isEmpty(requestedScopes)) {
            for (String requestedScope : requestedScopes) {
                if (!registeredClient.getScopes().contains(requestedScope)) {
                    throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
                }
                authorizedScopes.add(requestedScope);
            }
        }
        return authorizedScopes;
    }

    private static OAuth2AccessToken accessToken(
            OAuth2Authorization.Builder builder,
            OAuth2Token token,
            OAuth2TokenContext accessTokenContext) {
        OAuth2AccessToken.TokenType tokenType = OAuth2AccessToken.TokenType.BEARER;
        if (token instanceof ClaimAccessor claimAccessor) {
            Map<String, Object> cnfClaims = claimAccessor.getClaimAsMap("cnf");
            if (!CollectionUtils.isEmpty(cnfClaims) && cnfClaims.containsKey("jkt")) {
                tokenType = OAuth2AccessToken.TokenType.DPOP;
            }
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                tokenType,
                token.getTokenValue(),
                token.getIssuedAt(),
                token.getExpiresAt(),
                accessTokenContext.getAuthorizedScopes());
        OAuth2TokenFormat accessTokenFormat = accessTokenContext.getRegisteredClient()
                .getTokenSettings()
                .getAccessTokenFormat();
        builder.token(accessToken, metadata -> {
            if (token instanceof ClaimAccessor claimAccessor) {
                metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, claimAccessor.getClaims());
            }
            metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, false);
            metadata.put(OAuth2TokenFormat.class.getName(), accessTokenFormat.getValue());
        });
        return accessToken;
    }

    private static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(
            PhoneSmsGrantAuthenticationToken authentication) {
        Authentication clientPrincipal = (Authentication) authentication.getPrincipal();
        if (clientPrincipal instanceof OAuth2ClientAuthenticationToken oauth2ClientPrincipal
                && oauth2ClientPrincipal.isAuthenticated()) {
            return oauth2ClientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }
}
