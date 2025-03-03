package org.example.TryJava.Socket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {

    private final static String HOST = "localhost";
    private final static int PORT = 6666;

    public static void main(String[] args) throws IOException {
        try {
            Socket sock = new Socket(HOST, PORT);
            InputStream input = sock.getInputStream();
            OutputStream output = sock.getOutputStream();
            System.out.printf("Connected to remote server %s:%d %n", HOST, PORT);
            handle(input, output);
        } catch (Exception exception) {
            System.out.printf("Communication error: %s, %n", exception.getMessage());
        }
        System.out.println("Connection was closed.");
    }

    private static void handle(InputStream input, OutputStream output) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

        Scanner scanner = new Scanner(System.in);
        System.out.println("[server] " + reader.readLine());

        for (;;) {
            System.out.print(">>> "); // 打印提示

            String s = scanner.nextLine(); // 读取一行输入

            // 发送到服务器
            writer.write(s);
            writer.newLine();
            writer.flush();

            // 读取服务器回应
            String resp = reader.readLine();
            if (resp == null) {
                throw new RuntimeException("Can not read from remote server!");
            }
            System.out.printf("%s <<< [server] %n", resp);
            if (resp.equals("bye")) {
                break;
            }
        }
    }
}
