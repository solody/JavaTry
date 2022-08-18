package com.example.tryspark;

import com.example.tryspark.entity.Person;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

public class CreatingDataset {
    private static String appName = "Creating Dataset";
    private static String master = "local[16]";

    private static Logger logger = Logger.getGlobal();
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .appName(appName)
                .master(master)
                .getOrCreate();

        // Create an instance of a Bean class
        Person person = new Person();
        person.setName("Andy");
        person.setAge(32);

        // Encoders are created for Java beans
        Encoder<Person> personEncoder = Encoders.bean(Person.class);
        Dataset<Person> javaBeanDS = spark.createDataset(
                Collections.singletonList(person),
                personEncoder
        );
        javaBeanDS.show();

        // Encoders for most common types are provided in class Encoders
        Encoder<Long> longEncoder = Encoders.LONG();
        Dataset<Long> primitiveDS = spark.createDataset(Arrays.asList(1L, 2L, 3L), longEncoder);
        Dataset<Long> transformedDS = primitiveDS.map(
                (MapFunction<Long, Long>) value -> value + 1L,
                longEncoder);
        System.out.println(transformedDS.collectAsList());

        // DataFrames can be converted to a Dataset by providing a class. Mapping based on name
        String path = "TrySpark/src/main/resources/people.json";
        Dataset<Person> peopleDS = spark.read().json(path).as(personEncoder);
        peopleDS.show();
    }
}
