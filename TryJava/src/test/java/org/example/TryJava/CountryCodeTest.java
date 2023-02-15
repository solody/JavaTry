package org.example.TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryCodeTest {
    @Test
    void test() {
        initCountryCodeMapping();
        String ios2 = iso3CountryCodeToIso2CountryCode("USA");
        Assertions.assertEquals("US", ios2);
    }

    private Map<String, Locale> localeMap;

    private void initCountryCodeMapping() {
        String[] countries = Locale.getISOCountries();
        localeMap = new HashMap<String, Locale>(countries.length);
        for (String country : countries) {
            Locale locale = new Locale("", country);
            localeMap.put(locale.getISO3Country().toUpperCase(), locale);
        }
    }

    private String iso3CountryCodeToIso2CountryCode(String iso3CountryCode) {
        return localeMap.get(iso3CountryCode).getCountry();
    }

    private String iso2CountryCodeToIso3CountryCode(String iso2CountryCode){
        Locale locale = new Locale("", iso2CountryCode);
        return locale.getISO3Country();
    }
}
