package TryJava;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class UrlTest {

    @Test
    public void testUrl() throws IOException {
        URL url = new URL("https://abc.com/123/456/a.php");
        assertEquals("https", url.getProtocol());
        assertEquals("abc.com", url.getHost());
        assertEquals(-1, url.getPort());
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
