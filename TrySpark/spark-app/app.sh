#!/bin/bash

spark-submit \
  --class com.example.tryspark.MySparkTry \
  --master local[2] \
  --supervise \
  --conf spark.standalone.submit.waitAppCompletion=true \
  ./TrySpark-1.0-SNAPSHOT.jar \
  something-else