package com.example.tryspark;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RDDFromParallelizedCollection {

    private static String appName = "RDD Try Application";
    private static String master = "local[16]";

    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Long> data = new ArrayList<>();
        Random random = new Random();
        while (data.size() < 1000000L) {
            data.add(Long.valueOf(random.nextInt(999)));
        }
        System.out.println(data.size());
        JavaRDD<Long> distData = sc.parallelize(data, 32);
        long total = distData.reduce((a, b) -> a + b);

        System.out.println(total);

        Thread.sleep(3600*1000L);
    }
}
