package com.example.tryspark;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.logging.Logger;

public class DataFrameBasic {
    private static String appName = "DataFrameBasic";
    private static String master = "local[16]";

    private static Logger logger = Logger.getGlobal();
    public static void main(String[] args) throws AnalysisException {
        SparkSession spark = SparkSession
                .builder()
                .appName(appName)
                .master(master)
                .getOrCreate();

        Dataset<Row> df = spark.read().json("TrySpark/src/main/resources/people.json");

        df.show();
        df.printSchema();

        // Basic operations of dataframe.

        // Select only the "name" column
        df.select("name").show();

        // Select everybody, but increment the age by 1
        df.select(df.col("name"), df.col("age").plus(1)).show();

        // Select people older than 21
        df.filter(df.col("age").gt(21)).show();

        // Count people by age
        df.groupBy("age").count().show();

        // Register the DataFrame as a SQL temporary view
        df.createOrReplaceTempView("people");

        // Running sql on a dataframe.

        Dataset<Row> sqlDF = spark.sql("SELECT * FROM people");
        sqlDF.show();

        // Register the DataFrame as a global temporary view
        df.createGlobalTempView("people");
        // Global temporary view is tied to a system preserved database `global_temp`
        spark.sql("SELECT * FROM global_temp.people").show();
        // Global temporary view is cross-session
        spark.newSession().sql("SELECT * FROM global_temp.people").show();
    }
}
