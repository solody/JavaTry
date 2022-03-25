#!/usr/bin/env bash

service ssh start
/hadoop/bin/hdfs namenode -format
/hadoop/sbin/start-dfs.sh
echo Waitting for serving
while true; do
  sleep 1
done