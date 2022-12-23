package org.example.TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "String from CompletableFuture!";
            }
        });
        completableFuture.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("Got data: " + s);
                Assertions.assertEquals("String from CompletableFuture!", s);
            }
        });
    }

    @Test
    void testSerialCompletableFuture() throws InterruptedException {
        // 第一个任务:
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            return "601857";
        });
        // cfQuery成功后继续执行下一个任务:
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync((code) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            return 5 + Math.random() * 20;
        });
        // cfFetch成功后打印结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(2000);
    }

    @Test
    void testParallelCompletableFuture() throws InterruptedException {
        // 两个CompletableFuture执行异步查询:
        CompletableFuture<String> cfQueryFromSina = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://finance.sina.com.cn/code/");
        });
        CompletableFuture<String> cfQueryFrom163 = CompletableFuture.supplyAsync(() -> {
            return queryCode("中国石油", "https://money.163.com/code/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfQuery = CompletableFuture.anyOf(cfQueryFromSina, cfQueryFrom163);

        // 两个CompletableFuture执行异步查询:
        CompletableFuture<Double> cfFetchFromSina = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://finance.sina.com.cn/price/");
        });
        CompletableFuture<Double> cfFetchFrom163 = cfQuery.thenApplyAsync((code) -> {
            return fetchPrice((String) code, "https://money.163.com/price/");
        });

        // 用anyOf合并为一个新的CompletableFuture:
        CompletableFuture<Object> cfFetch = CompletableFuture.anyOf(cfFetchFromSina, cfFetchFrom163);

        // 最终结果:
        cfFetch.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(200);
    }

    static String queryCode(String name, String url) {
        System.out.println("query code from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException ignored) {
        }
        return "601857";
    }

    static Double fetchPrice(String code, String url) {
        System.out.println("query price from " + url + "...");
        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException ignored) {
        }
        return 5 + Math.random() * 20;
    }
}
