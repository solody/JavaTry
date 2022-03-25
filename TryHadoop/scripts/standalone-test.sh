#!/usr/bin/env bash

mkdir /scripts/input
cp /hadoop/etc/hadoop/*.xml /scripts/input
/hadoop/bin/hadoop jar /hadoop/share/hadoop/mapreduce/hadoop-mapreduce-examples-3.3.2.jar grep /scripts/input /scripts/output 'dfs[a-z.]+'
cat /scripts/output/*