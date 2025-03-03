package org.example.TryJava.Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

    private final static int PORT = 6666;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(PORT)){
            System.out.printf("Server is running on port %d %n", PORT);
            for (;;) {
                Socket sock = ss.accept();
                System.out.printf("%nConnection created from %s %n", sock.getRemoteSocketAddress());
                Thread t = new Handler(sock);
                t.start();
            }
        } catch (Exception exception) {
            System.out.printf("Server was crashed! %s %n", exception.getMessage());
        }
    }
}

class Handler extends Thread {
    Socket sock;

    public Handler(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try (InputStream input = this.sock.getInputStream();
             OutputStream output = this.sock.getOutputStream()) {
            handle(input, output);
            System.out.printf("Connection from [%s] was closed. %n", sock.getRemoteSocketAddress());
        } catch (Exception e) {
            System.out.printf("Communication error: %s, %n", e.getMessage());
            try {
                this.sock.close();
                System.out.println("Connection was closed successful.");
            } catch (Exception ioe) {
                System.out.printf("Connection close with error: %s, %n", ioe.getMessage());
            }
        }
    }

    private void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        writer.write("hello\n");
        writer.flush();
        for (;;) {
            String s = reader.readLine();
            if (s == null) {
                throw new RuntimeException("Can not read from remote client!");
            }
            if (s.equals("bye")) {
                writer.write("bye\n");
                writer.flush();
                break;
            }
            writer.write("ok: " + s + "\n");
            writer.flush();
        }
    }
}
