package com.example.tryspark;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class MySparkTry {
    public static void main(String[] args) {

        String logFile = "./README.md"; // Should be some file on your system

        SparkSession spark = SparkSession.builder().master("local[2]").appName("Simple Application").getOrCreate();

        Dataset<String> logData = spark.read().textFile(logFile).cache();
        long numAs = logData.filter((String s) -> s.contains("a")).count();
        long numBs = logData.filter((String s) -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        spark.stop();
    }
}
