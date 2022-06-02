package TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class FutureTest {
    @Test
    void testFuture() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<?> future = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("I'm a runnable task.");
            }
        });
        Assertions.assertFalse(future.isDone());
        while (!future.isDone()) {
            Thread.sleep(100L);
        }
        Assertions.assertTrue(future.isDone());

        Future<String> future2 = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("I'm another runnable task.");
            }
        }, new String("something"));
        Assertions.assertEquals("something", future2.get());

        Future<String> future3 = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("I'm a Callable task.");
                return "String return from callable task.";
            }
        });
        Assertions.assertEquals("String return from callable task.", future3.get());
    }

    @Test
    void testCompletableFuture() {

    }
}
