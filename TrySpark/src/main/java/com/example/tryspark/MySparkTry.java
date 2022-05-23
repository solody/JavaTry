package com.example.tryspark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class MySparkTry {
    public static void main(String[] args) {

        String logFile = "/opt/bitnami/spark/README.md"; // Should be some file on your system
        SparkConf conf = new SparkConf().setAppName("MySpark");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> distFile = sc.textFile(logFile);

        long numAs = distFile.filter(s -> s.contains("a")).count();
        long numBs = distFile.filter(s -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);
        sc.stop();
    }
}
