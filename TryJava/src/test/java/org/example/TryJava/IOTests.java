package org.example.TryJava;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

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
}
