package com.example.tryspark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.util.LongAccumulator;

import java.util.Arrays;
import java.util.logging.Logger;

public class AccumulatorVariable {
    private static String appName = "RDD Try Application AccumulatorVariable";
    private static String master = "local[16]";

    private static Logger logger = Logger.getGlobal();

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);

        LongAccumulator accum = sc.sc().longAccumulator();

        sc.parallelize(Arrays.asList(1, 2, 3, 4)).foreach(x -> accum.add(x));

        accum.value();
    }
}
