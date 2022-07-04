package TryJava;

import org.junit.jupiter.api.Test;

public class LongTest {
    @Test
    void test() throws InterruptedException {
        Long last = System.currentTimeMillis();
        Thread.sleep(1200);
        Long requests = 5000L;
        Long window = System.currentTimeMillis()-last;
        Double qps = requests / (window / 1000D);
        System.out.println(qps);
    }
}
