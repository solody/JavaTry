package org.example.TryJava.Socket;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SocketTest {
    @Test
    void tcpTest() throws IOException, InterruptedException {
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.main(new String[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        server.start();

        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.setIn(new ByteArrayInputStream("haha\nkent\nbye".getBytes()));
                    Client.main(new String[]{});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        client.start();

        // Let Junit wait for client die.
        client.join();
    }
}
