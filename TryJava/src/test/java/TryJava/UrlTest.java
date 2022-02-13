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
        byte[] bytes = baidu_url.openConnection().getInputStream().readAllBytes();
        for (byte b: bytes){
            // Notice that b might is not a ascii char.
            System.out.print((char) b);
        }

        baidu_url = new URL("https://www.baidu.com/index.php");
        InputStream inputStream = baidu_url.openConnection().getInputStream();
        // Scanner will handle the charset.
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
    }
}
