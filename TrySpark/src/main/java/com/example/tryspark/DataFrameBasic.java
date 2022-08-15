package com.example.tryspark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.logging.Logger;

public class DataFrameBasic {
    private static String appName = "DataFrameBasic";
    private static String master = "local[16]";

    private static Logger logger = Logger.getGlobal();
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName(appName)
                .master(master)
                .getOrCreate();

        Dataset<Row> df = spark.read().json("TrySpark/src/main/resources/people.json");

        df.show();
        df.printSchema();
    }
}
