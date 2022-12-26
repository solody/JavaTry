package org.example.TryJava;

import org.junit.jupiter.api.Test;

import java.util.TimeZone;

public class TimezoneTest {
    @Test
    void test() {
        String id = TimeZone.getTimeZone("Asia/Shanghai").getID();
        id = TimeZone.getTimeZone("UTC").getID();
        id = TimeZone.getTimeZone("GMT+0:00").getID();
        id = TimeZone.getTimeZone("GMT+8:00").getID();
    }
}
