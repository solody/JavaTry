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
        System.out.println(f1.getAbsolutePath());
        System.out.println(f1.getCanonicalPath());
        File f2 = new File(".");
        for (File ff: f2.listFiles()){
            System.out.println(" - " + ff.getName());
        }

        File f3 = File.createTempFile("tmp-", ".txt"); // 提供临时文件的前缀和后缀
        f3.deleteOnExit(); // JVM退出时自动删除
        System.out.println(f3.isFile());
        System.out.println(f3.getCanonicalPath());

        File f4 = new File("./test-mkdirs/d/i/r");
        f4.mkdirs();
        f4.delete();
        f4.getParentFile().delete();
        f4.getParentFile().getParentFile().delete();
        f4.getParentFile().getParentFile().getParentFile().delete();
    }

    @Test
    void testInputStream() throws IOException {
        // 创建 FileInputStream 对象
        InputStream inputStream1 = new FileInputStream("./README.md");
        InputStream inputStream2 = Files.newInputStream(Paths.get("./README.md"));

        for (;;) {
            int byteValue = inputStream1.read(); // 反复调用 read() 方法，直到返回-1
            if (byteValue == -1) {
                break;
            }
            System.out.println(byteValue); // 打印 byte 的十进制值
        }
        inputStream1.close(); // 关闭流

        // 批量读取字节
        try (InputStream inputStream3 = new FileInputStream("./README.md")) {
            // 定义300个字节大小的缓冲区:
            byte[] buffer = new byte[300];
            int n;
            while ((n = inputStream3.read(buffer)) != -1) { // 读取到缓冲区
                System.out.println("Read " + n + " bytes.");
            }
        }

        // ByteArrayInputStream 用字节数组作为输入流
        byte[] data = { 72, 101, 108, 108, 111, 33 };
        try (InputStream input4 = new ByteArrayInputStream(data)) {
            int n;
            while ((n = input4.read()) != -1) {
                System.out.println((char)n);
            }
        }

        // Reader 会自动识别文本字节码并转为字符流
        try (FileReader fileReader = new FileReader("./README.md", StandardCharsets.UTF_8)) {
            int n;
            StringBuilder sb = new StringBuilder();
            while ((n = fileReader.read()) != -1) {
                sb.append((char) n);
            }
            System.out.println(sb);
        }
    }

    @Test
    void testChar() {

        // Java 的 char 只能表示 BMP (0-65536) 范围的 unicode 字符
        // 超过的 BMP 范围的字符，需要用 char[] 字符数组来表示
        char[] bmpChar = Character.toChars(Integer.parseInt("48", 16));  // 1D306 是一个辅助平面字符
        char[] outOfBmpChar = Character.toChars(Integer.parseInt("1D306", 16));  // 1D306 是一个辅助平面字符
        String s = Character.toString(Integer.parseInt("1f604", 16)); // 1f604 是一个辅助平面字符
        System.out.println(bmpChar);
        System.out.println(outOfBmpChar);
        System.out.println(s);

        // 超过的 BMP 范围的字符，需要表示成两人代理对字符

        // 把一个字符代码拆为高低两个代理位
        int outOfBMPChar = 128169;
        char highSurrogate1 = Character.highSurrogate(outOfBMPChar);
        char lowSurrogate1 = Character.lowSurrogate(outOfBMPChar);
        System.out.println(new char[]{highSurrogate1, lowSurrogate1});

        char highSurrogate = '\uD83D'; // 代理对的高位代理部分
        char lowSurrogate = '\uDCA9';  // 代理对的低位代理部分

        // 检查是否为代理对的一部分
        if (Character.isHighSurrogate(highSurrogate)) {
            System.out.println((int) highSurrogate + " 是高位代理");
        }
        if (Character.isLowSurrogate(lowSurrogate)) {
            System.out.println((int) lowSurrogate + " 是低位代理");
        }

        // 将代理对组合成一个代码点
        int codePoint = Character.toCodePoint(highSurrogate, lowSurrogate);
        System.out.println("合成的代码点: " + codePoint);
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
        try (
                InputStream input4 = new FileInputStream("./test.txt");
                OutputStream output4 = new FileOutputStream("./test2.txt")
        ) {
            input4.transferTo(output4); // transferTo的作用是?
        }
    }
}
