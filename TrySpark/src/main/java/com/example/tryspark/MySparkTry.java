package com.example.tryspark;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

import java.util.Arrays;
import java.util.List;

public class MySparkTry {
    public static void main(String[] args) throws InterruptedException {
        String appName = String.valueOf(MySparkTry.class);
        String master = "spark://localhost:7077";
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);

        while (true) {
            System.out.println("Running!!!");
            Thread.sleep(1000L);
        }
    }
}
