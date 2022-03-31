#!/usr/bin/env bash

echo Waiting for hadoop cluster ...
dockerize -wait tcp://name-node:9000 -wait http://name-node:50070

echo Hadoop cluster have waked up!

su - hive -c "$HADOOP_HOME/bin/hadoop fs -mkdir /tmp"
su - hive -c "$HADOOP_HOME/bin/hadoop fs -mkdir -p /user/hive/warehouse"
su - hive -c "$HADOOP_HOME/bin/hadoop fs -chmod g+w /tmp"
su - hive -c "$HADOOP_HOME/bin/hadoop fs -chmod g+w /user/hive/warehouse"

su - hive -c "$HIVE_HOME/bin/schematool -dbType derby -initSchema"
su - hive -c "$HIVE_HOME/bin/hiveserver2"

$HADOOP_PREFIX/keep_running.sh