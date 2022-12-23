package org.example.TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeTest {
    @Test
    public void testType() {
        Male male = new Male();
        Female female = new Female();
        Assertions.assertEquals("Come on, Kent", male.say("Kent"));
        Assertions.assertEquals("Hi, Kent", female.say("Kent"));
    }
}
