package com.solody.JavaLang;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ListTest extends TestCase {

    @Test
    public void testList() {
        ArrayList<String> strings = new ArrayList<>();

        strings.add("apple");
        strings.add("banana");
        strings.add("strawberry");
        Assertions.assertEquals(3, strings.size());

        String apple = strings.get(0);
        Assertions.assertEquals("apple", apple);
        Assertions.assertEquals(3, strings.size());

        strings.remove("apple");
        Assertions.assertEquals(2, strings.size());

        String banana = strings.get(0);
        Assertions.assertEquals("banana", banana);
    }
}
