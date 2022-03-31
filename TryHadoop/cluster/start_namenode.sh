#!/usr/bin/env bash

service ssh start

chown hdfs:hadoop /home/hdfs/dfs && chmod 0755 /home/hdfs/dfs

# Format the hdfs, if it's not formatted yet.
su - hdfs -c "$HADOOP_PREFIX/bin/hdfs namenode -format dsp -nonInteractive"

su - hdfs -c "$HADOOP_PREFIX/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script hdfs start namenode"

$HADOOP_PREFIX/keep_running.sh