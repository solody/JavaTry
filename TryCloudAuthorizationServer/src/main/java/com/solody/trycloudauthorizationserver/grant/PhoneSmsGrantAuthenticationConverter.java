package com.solody.trycloudauthorizationserver.grant;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PhoneSmsGrantAuthenticationConverter implements AuthenticationConverter {

    private static final String PHONE_PARAMETER = "phone";
    private static final String SMS_CODE_PARAMETER = "code";
    private static final String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    @Nullable
    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!PhoneSmsGrantAuthenticationToken.GRANT_TYPE.getValue().equals(grantType)) {
            return null;
        }

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        if (clientPrincipal == null) {
            throwInvalidRequest(OAuth2ParameterNames.CLIENT_ID);
        }

        MultiValueMap<String, String> parameters = getParameters(request);

        String phone = parameters.getFirst(PHONE_PARAMETER);
        if (!StringUtils.hasText(phone) || parameters.get(PHONE_PARAMETER).size() != 1) {
            throwInvalidRequest(PHONE_PARAMETER);
        }

        String code = parameters.getFirst(SMS_CODE_PARAMETER);
        if (!StringUtils.hasText(code) || parameters.get(SMS_CODE_PARAMETER).size() != 1) {
            throwInvalidRequest(SMS_CODE_PARAMETER);
        }

        Set<String> scopes = null;
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope)) {
            if (parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
                throwInvalidRequest(OAuth2ParameterNames.SCOPE);
            }
            scopes = new HashSet<>(Arrays.asList(scope.split(" ")));
        }

        return new PhoneSmsGrantAuthenticationToken(phone, code, clientPrincipal, scopes);
    }

    private static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        request.getParameterMap().forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }
        });
        return parameters;
    }

    private static void throwInvalidRequest(String parameterName) {
        OAuth2Error error = new OAuth2Error(
                OAuth2ErrorCodes.INVALID_REQUEST,
                "OAuth 2.0 Parameter: " + parameterName,
                ACCESS_TOKEN_REQUEST_ERROR_URI);
        throw new OAuth2AuthenticationException(error);
    }
}
