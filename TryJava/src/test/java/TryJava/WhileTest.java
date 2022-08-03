package TryJava;

import org.junit.jupiter.api.Test;

public class WhileTest {
    @Test
    void testTimes() throws InterruptedException {
        long times = 0;
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 10) {
            Thread.sleep(1L);
            times++;
        }
        System.out.println(times);
    }
}
