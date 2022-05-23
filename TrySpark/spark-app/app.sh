#!/bin/bash

spark-submit \
  --class com.example.tryspark.MySparkTry \
  --master "${SPARK_MASTER_URL}" \
  --deploy-mode cluster \
  --supervise \
  --conf spark.standalone.submit.waitAppCompletion=true \
  ./TrySpark-1.0-SNAPSHOT.jar \
  something-else