package org.example.TryJava.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sel4jLogging {
    static Logger logger = LoggerFactory.getLogger(Sel4jLogging.class);
    public static void main(String[] args) {
        logger.info("asdfasfd");
    }
}
