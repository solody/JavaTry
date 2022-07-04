package TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UrlTest {

    @Test
    public void testUrlEncode() throws UnsupportedEncodingException, MalformedURLException {
        String val = "123|456";
        assertEquals("123%7C456", URLEncoder.encode(val, "UTF-8"));
        assertEquals("http://click.tangormedia.com/click/offer?gaid=%7Bgaid%7D&offerId=1126318&clickId=%7Bclick_id%7D&affiliate=1037&affSub=%7Baff_sub%7D",
                fixUrlEncode("http://click.tangormedia.com/click/offer?offerId=1126318&affiliate=1037&clickId={click_id}&gaid={gaid}&affSub={aff_sub}"));

        assertEquals("http://click.tangormedia.com/click/offer",
                fixUrlEncode("http://click.tangormedia.com/click/offer?"));

        assertEquals("http://click.tangormedia.com/click/offer",
                fixUrlEncode("http://click.tangormedia.com/click/offer"));

    }

    private String fixUrlEncode(String sourceUrl) throws MalformedURLException, UnsupportedEncodingException {
        URL url = new URL(sourceUrl);
        Map<String, String> map = queryStringToMap(url.getQuery());
        String queryString = mapToQueryString(map);
        if (!queryString.equals("")) queryString = "?" + queryString;
        return sourceUrl.split("\\?")[0] + queryString;
    }

    @Test
    public void testQueryString() {
        String queryString = "a=123&b=456&c=789";
        Map<String, String> map = queryStringToMap(queryString);
        map.put("a", "&&|()88");
        Assertions.assertEquals("a=%26%26%7C%28%2988&b=456&c=789", mapToQueryString(map));


        String emptyQueryString = "";
        Map<String, String> emptyMap = queryStringToMap(emptyQueryString);
        Assertions.assertEquals(0, emptyMap.size());

        Assertions.assertEquals("", mapToQueryString(emptyMap));
   }

    private Map<String, String> queryStringToMap(String queryString) {
        Map<String, String> params = new HashMap<>();
        if (queryString == null || queryString.equals("")) return params;
        String[] pares = queryString.split("&");
        for (String pare : pares) {
            String[] pareArr = pare.split("=");
            params.put(URLDecoder.decode(pareArr[0]), pareArr.length < 2 ? "" : URLDecoder.decode(pareArr[1]));
        }
        return params;
    }

    private String mapToQueryString(Map<String, String> map) {
        final StringBuilder queryString = new StringBuilder();
        map.entrySet().forEach(item -> {
            if (queryString.length() != 0) {
                queryString.append("&");
            }
            queryString.append(URLEncoder.encode(item.getKey()) + "=" + URLEncoder.encode(item.getValue()));
        });
        return queryString.toString();
    }

    @Test
    public void testUrl() throws IOException {
        URL url = new URL("https://abc.com/123/456/a.php?a=123&b=456");
        assertEquals("https", url.getProtocol());
        assertEquals("abc.com", url.getHost());
        assertEquals(-1, url.getPort());
        assertEquals("/123/456/a.php", url.getPath());
        assertEquals("a=123&b=456", url.getQuery());
        assertThrowsExactly(FileNotFoundException.class, () -> {
            Object content = url.openConnection().getContent();
        });

        URL baidu_url = new URL("https://www.baidu.com/index.php");
        InputStream inputStream = baidu_url.openConnection().getInputStream();
        // Scanner will handle the charset.
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }

    @Test
    public void testPrefixSupport() {
        String host = "www.java2s.com";
        String file = "/index.html";

        String[] schemes = {"http", "https", "ftp", "mailto", "telnet", "file", "ldap", "gopher",
                "jdbc", "rmi", "jndi", "jar", "doc", "netdoc", "nfs", "verbatim", "finger", "daytime",
                "systemresource"};

        for (int i = 0; i < schemes.length; i++) {
            try {
                URL u = new URL(schemes[i], host, file);
                System.out.println(schemes[i] + " is supported");
            } catch (Exception ex) {
                System.out.println(schemes[i] + " is not supported");
            }
        }
    }
}
