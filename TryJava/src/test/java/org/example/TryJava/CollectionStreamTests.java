package org.example.TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionStreamTests {
    @Test
    void test() {
        Random random = new Random();
        IntStream.generate(() -> random.nextInt(1000)).forEach(System.out::println);
        List<Integer> integers = IntStream.range(0, 1000).boxed().collect(Collectors.toList());
    }
}
