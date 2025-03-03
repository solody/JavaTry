package org.example.TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeTest {

    final static Logger logger = LoggerFactory.getLogger(TypeTest.class);


    @Test
    public void testType() {

        logger.info("1231 --------------------");

        Male male = new Male();
        Female female = new Female();
        Assertions.assertEquals("Come on, Kent", male.say("Kent"));
        Assertions.assertEquals("Hi, Kent", female.say("Kent"));
    }
}
