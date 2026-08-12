package com.solody.trycloudauthorizationserver.grant;

public interface SmsCodeVerifier {

    boolean verify(String phone, String code);
}
