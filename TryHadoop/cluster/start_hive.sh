#!/usr/bin/env bash

echo "Waiting for hadoop cluster ..."
dockerize -wait tcp://name-node:9000 -wait http://name-node:50070

echo Hadoop cluster have waked up!

su - hive -c "$HADOOP_HOME/bin/hadoop fs -mkdir /tmp"
su - hive -c "$HADOOP_HOME/bin/hadoop fs -mkdir -p /user/hive/warehouse"
su - hive -c "$HADOOP_HOME/bin/hadoop fs -chmod g+w /tmp"
su - hive -c "$HADOOP_HOME/bin/hadoop fs -chmod g+w /user/hive/warehouse"

chown hive:hadoop /home/hive/meta && chmod 0755 /home/hive/meta

metastore_database="/home/hive/meta/metastore_db"
if [ -d "$metastore_database" ]
then
 echo "$metastore_database was created before."
else
 echo "$metastore_database is not found, to create it now."
 su - hive -c "$HIVE_HOME/bin/schematool -dbType derby -initSchema"
fi

su - hive -c "$HIVE_HOME/bin/hiveserver2"

$HADOOP_PREFIX/keep_running.sh