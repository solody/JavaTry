package com.example.tryspark;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.storage.StorageLevel;

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
        // Cache it in memory, after the first time it is computed.
        lineLengths.persist(StorageLevel.MEMORY_ONLY());
        int totalLength = lineLengths.reduce((a, b) -> a + b);

        logger.info(String.valueOf(totalLength));

        // Use non-lambda syntax.
        JavaRDD<Integer> lineLengths2 = lines.map(new Function<String, Integer>() {
            public Integer call(String s) { return s.length(); }
        });
        int totalLength2 = lineLengths.reduce(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer a, Integer b) { return a + b; }
        });
        logger.info(String.valueOf(totalLength2));

    }
}
