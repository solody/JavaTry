package com.solody.JavaLang;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class IOTest extends TestCase {

    @Test
    public void testWriteFile() throws IOException {
        File file = new File(".");
        System.out.print(file.getAbsoluteFile().getCanonicalPath());
    }
}
