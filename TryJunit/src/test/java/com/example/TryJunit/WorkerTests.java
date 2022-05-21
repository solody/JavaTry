package com.example.TryJunit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WorkerTests {
    @Test
    void helloTest() {
        Worker worker = new Worker();
        Assertions.assertEquals("Hello, Kent", worker.hello("Kent"));
    }
}
