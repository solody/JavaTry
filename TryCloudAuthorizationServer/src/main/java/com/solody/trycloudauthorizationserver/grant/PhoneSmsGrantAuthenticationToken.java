package com.solody.trycloudauthorizationserver.grant;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.io.Serial;
import java.util.Collections;
import java.util.Set;

public class PhoneSmsGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    public static final AuthorizationGrantType GRANT_TYPE =
            new AuthorizationGrantType("urn:ietf:params:oauth:grant-type:phone_sms");

    @Serial
    private static final long serialVersionUID = 1L;

    private final String phone;
    private final String code;
    private final Set<String> scopes;

    public PhoneSmsGrantAuthenticationToken(
            String phone,
            String code,
            Authentication clientPrincipal,
            Set<String> scopes) {
        super(GRANT_TYPE, clientPrincipal, null);
        this.phone = phone;
        this.code = code;
        this.scopes = scopes != null ? scopes : Collections.emptySet();
    }

    public String getPhone() {
        return phone;
    }

    public String getCode() {
        return code;
    }

    public Set<String> getScopes() {
        return scopes;
    }
}
