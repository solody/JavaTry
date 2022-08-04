package com.example.tryspark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.logging.Logger;

public class BroadcastVariable {

    private static String appName = "RDD Try Application BroadcastVariable";
    private static String master = "local[16]";

    private static Logger logger = Logger.getGlobal();

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);

        Broadcast<int[]> broadcastVar = sc.broadcast(new int[] {1, 2, 3});

        broadcastVar.value();
    }
}
