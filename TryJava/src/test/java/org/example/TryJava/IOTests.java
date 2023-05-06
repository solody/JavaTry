package org.example.TryJava;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOTests {

    /**
     * <a href="https://www.liaoxuefeng.com/wiki/1252599548343744/1298069154955297">...</a>
     */
    @Test
    void testFile() throws IOException {
        File f1 = new File("./README.md");
        System.out.println(f1.getCanonicalPath());
        File f2 = new File(".");
        for (File ff: f2.listFiles()){
            System.out.println(ff.getName());
        }

        File f3 = File.createTempFile("tmp-", ".txt"); // 提供临时文件的前缀和后缀
        f3.deleteOnExit(); // JVM退出时自动删除
        System.out.println(f3.isFile());
        System.out.println(f3.getAbsolutePath());

        File f4 = new File("./test-mkdirs/d/i/r");
        f4.mkdirs();
        f4.delete();
        f4.getParentFile().delete();
        f4.getParentFile().getParentFile().delete();
        f4.getParentFile().getParentFile().getParentFile().delete();
    }

    @Test
    void testInputStream() throws IOException {
        // 创建一个FileInputStream对象:
        // InputStream input = new FileInputStream("./README.md");
        InputStream input = Files.newInputStream(Paths.get("./README.md"));
        for (;;) {
            int n = input.read(); // 反复调用read()方法，直到返回-1
            if (n == -1) {
                break;
            }
            System.out.println(n); // 打印byte的值
        }
        input.close(); // 关闭流


        InputStream input1 = null;
        try {
            input1 = new FileInputStream("./README.md");
            int n;
            while ((n = input1.read()) != -1) { // 利用while同时读取并判断
                System.out.println(n);
            }
        } finally {
            if (input1 != null) { input1.close(); }
        }


        try (InputStream input2 = new FileInputStream("./README.md")) {
            int n;
            while ((n = input2.read()) != -1) {
                System.out.println(n);
            }
        } // 编译器在此自动为我们写入finally并调用close()


        try (InputStream input3 = new FileInputStream("./README.md")) {
            // 定义1000个字节大小的缓冲区:
            byte[] buffer = new byte[1000];
            int n;
            while ((n = input3.read(buffer)) != -1) { // 读取到缓冲区
                System.out.println("read " + n + " bytes.");
            }
        }


        byte[] data = { 72, 101, 108, 108, 111, 33 };
        try (InputStream input4 = new ByteArrayInputStream(data)) {
            int n;
            while ((n = input4.read()) != -1) {
                System.out.println((char)n);
            }
        }


        String s;
        try (InputStream input5 = new FileInputStream("./README.md")) {
            int n;
            StringBuilder sb = new StringBuilder();
            while ((n = input5.read()) != -1) {
                sb.append((char) n);
            }
            s = sb.toString();
        }
        System.out.println(s);
    }

    @Test
    void testOutputStream() throws IOException {
        OutputStream output = new FileOutputStream("./test.txt");
        output.write(72); // H
        output.write(101); // e
        output.write(108); // l
        output.write(108); // l
        output.write(111); // o
        output.close();

        OutputStream output2 = new FileOutputStream("./test2.txt");
        output2.write("Hello".getBytes(StandardCharsets.UTF_8)); // Hello
        output2.close();


        byte[] data;
        try (ByteArrayOutputStream output3 = new ByteArrayOutputStream()) {
            output3.write("Hello ".getBytes(StandardCharsets.UTF_8));
            output3.write("world!".getBytes(StandardCharsets.UTF_8));
            data = output3.toByteArray();
        }
        System.out.println(new String(data, StandardCharsets.UTF_8));

        // 读取input.txt，写入output.txt:
        try (InputStream input4 = new FileInputStream("./test.txt");
             OutputStream output4 = new FileOutputStream("./test2.txt"))
        {
            input4.transferTo(output4); // transferTo的作用是?
        }
    }
}
