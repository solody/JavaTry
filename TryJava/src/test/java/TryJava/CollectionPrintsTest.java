package TryJava;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

public class CollectionPrintsTest {
    @Test
    void test() {
        Queue<Long> queue = new LinkedList<>();
        queue.offer(123L);
        queue.offer(12L);
        queue.offer(124563L);

        System.out.println("values: " + queue);
    }
}
