package com.example.tryspark;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;

import java.util.logging.Logger;

public class RDDFromExternalDataset {

    private static String appName = "RDD Try Application";
    private static String master = "local[16]";

    private static Logger logger = Logger.getGlobal();

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = sc.textFile("./README.md");

        JavaRDD<Integer> lineLengths = lines.map(s -> s.length());
        int totalLength = lineLengths.reduce((a, b) -> a + b);

        logger.info(String.valueOf(totalLength));
    }
}
