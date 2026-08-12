package com.solody.trycloudauthorizationserver.grant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "app.sms")
public class InMemorySmsCodeVerifier implements SmsCodeVerifier {

    private Map<String, String> codes = new HashMap<>();

    public void setCodes(Map<String, String> codes) {
        this.codes = codes;
    }

    @Override
    public boolean verify(String phone, String code) {
        String expectedCode = codes.get(phone);
        return expectedCode != null && expectedCode.equals(code);
    }
}
