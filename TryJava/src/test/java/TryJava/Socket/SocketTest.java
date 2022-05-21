package TryJava.Socket;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SocketTest {
    @Test
    void tcpTest() throws IOException {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.main(new String[]{});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        server.start();

        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Client.main(new String[]{});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        client.start();
    }
}
